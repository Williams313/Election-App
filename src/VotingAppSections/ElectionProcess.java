package VotingAppSections;

import java.util.ArrayList;
import java.util.Scanner;

public class ElectionProcess {
    private String  electionName;
    private static Voter loggedinVoter;
    private static Registration registration = new Registration();

    public ElectionProcess(String electionName) {
        this.electionName = electionName;
    }

    public static void main(String[] args) {
        showRegistrationMenu();
    }

    private static void showRegistrationMenu(){
        String registrationMenu = """
                        Election Menu
                ====================================
                   1 -> Voter Registration
                   2 -> Candidate Registration
                   3 -> Login
               =======================================
               """;
        display(registrationMenu);
        String RegistrationOption =  input("Enter preferred Option :");
        switch (RegistrationOption) {
            case "1":   voterRegistration();
                break;
            case "2":   candidateRegistration();
                break;
            case "3":   login(); displayMenu();
                break;
        }
    }

    private static void voterRegistration() {
        try {

            String votersName = input("Enter Voter Name :");
            String votersPassword = input("Enter Voter Password :");
            Voter newVoter = registration.registerVoter(votersName, votersPassword);
            display(String.format("Your ID is %s", newVoter.getID()));

        }catch (IllegalArgumentException error) {
            display(error.getMessage());
        }
        finally {
            showRegistrationMenu();
        }
    }

    private static void candidateRegistration() {
        try {

            String candidateName = input("Enter Candidate Name :");
            String position = input("Enter Position  :");
            Candidate newCandidate = registration.registerCandidate(candidateName, position);
            display(String.format("Your ID is %s", newCandidate.getID()));

        }catch (IllegalArgumentException error){
            display(error.getMessage());
            candidateRegistration();
        }
        finally {
            showRegistrationMenu();
        }
    }



    private static void login(){
        String voterID = input("Enter your ID :");
        String voterPassword = input("Enter your password : ");

        Voter voter = Registration.findVoter(voterID);
        try {
            boolean isSuccessful = voter.login(voterID, voterPassword);
            if (isSuccessful) {
                loggedinVoter = voter;
                display("==============================================");
                display(String.format("Welcome back %s", voter.getName().toUpperCase()));
                display("==============================================");
            }
        }catch (IllegalArgumentException err){
            display(err.getMessage());
        }
    }


    private static void displayMenu(){
        String electionMenu = """
                        Election Menu
                =====================================
                   1 -> Cast Vote for Candidate
                   2 -> Display Result
                   3 -> Exit/ Logout
               =======================================
               """;

        display(electionMenu);
        String UserOption =  input("Enter preferred Option :");
        switch (UserOption) {
            case "1": displayCandidateForSpecifiedPosition();break;
            case "2": displayResultForSpecifiedPosition();break;
            case "3": exitApp();break;
            default: display("Invalid Option");displayMenu();break;
        }

    }


    private static void displayCandidateForSpecifiedPosition() {
        displayPosition();
        ElectionProcess election;
        String option;
        String positionOption = input("Enter preferred Option");
        switch (positionOption) {

            case "1":   handleVotingForPosition("president");
                break;

            case "2":   handleVotingForPosition("vice-president");
                break;

            case "3":   handleVotingForPosition("governor");
                break;

            case "4":   handleVotingForPosition("senator");
                break;

            case "5":  displayMenu();break;

            default: display("Invalid Option");
                positionOption = input("Enter preferred Option"); break;

        }

    }

    private static void displayResultForSpecifiedPosition() {
        displayPosition();
        String positionOption = input("Enter preferred Option");

        switch (positionOption) {
            case "1":   manageResultOptions("president");
                break;

            case "2":   manageResultOptions("vice-president");
                break;

            case "3":   manageResultOptions("governor");
                break;

            case "4":   manageResultOptions("senator");
                break;

            case "5":   displayMenu();
                break;

            default:    display("Invalid option");
                positionOption = input("Enter preferred Option");break;

        }

    }

    private static void exitApp() {
        display("Thank you for your Vote");
        logout();
        System.exit(47);
    }

    private void displayResult(Registration registration) {
        ArrayList<Candidate> candidates =  registration.getRegisteredCandidates();
        boolean candidatesNotEmpty = candidates != null && !candidates.isEmpty();
        if(candidatesNotEmpty) {
            display(String.format("%-15s %5s", "Candidate Name", "Vote Count"));
            boolean candidatesFound = false;
            try {
                for (Candidate candidate : candidates) {
                    if (this.electionName.equals(candidate.getPosition())) {
                        display(String.format("%-15s %5s", candidate.getName(), candidate.getVoteCount()));
                        candidatesFound = true;
                    }

                }
                if(!candidatesFound){
                    display("There is no candidate for this category");
                    displayResultForSpecifiedPosition();
                }

            }catch (Exception err){
                display(err.getMessage());
            }
        }
        else{
            display("There is no registered candidate found");
        }
    }

    private void displayCandidate(Registration reg) {

        ArrayList<Candidate> candidates =  reg.getRegisteredCandidates();
        boolean candidatesNotEmpty = candidates != null && !candidates.isEmpty();


        if(candidatesNotEmpty) {
            display(String.format("%-5s  %-20s %-10s", "ID", "Candidate Name", "Position"));
            boolean candidatesFound = false;

            try {
                for (Candidate candidate : candidates) {
                    if (this.electionName.equals(candidate.getPosition())) {
                        candidate.displayCandidates();
                        candidatesFound = true;
                    }

                }
                if(!candidatesFound) {
                    display("There is no candidate for category");
                    displayCandidateForSpecifiedPosition();
                }

                System.out.print("\n\n");
            }catch(IllegalArgumentException err){
                display(err.getMessage());
            }
        }
        else{
            display("There is no registered candidate available.");
        }

    }

    private void castVote() {
        String candidateID = input("Enter Candidate ID :");

        try {

            if(loggedinVoter.hasVotedForPosition(this.electionName))
                throw new IllegalArgumentException(loggedinVoter.getName() + " has already voted for this category");

            loggedinVoter.castVote(candidateID);
            loggedinVoter.getVotedPosition().add(this.electionName);

            display(String.format("%s has voted successfully", loggedinVoter.getName()));

        }catch (IllegalArgumentException err){
            display(err.getMessage());
            if(err.getMessage().equals("Voter has already voted for this category")) displayCandidateForSpecifiedPosition();

        }
    }

    private static void display(String prompt){
        System.out.println(prompt);
    }

    private static String input(String prompt){
        display(prompt);
        Scanner receiver = new Scanner(System.in);
        return receiver.nextLine().toLowerCase();
    }

    private static void handleVotingForPosition(String position){
        ElectionProcess election = new ElectionProcess(position);
        election.displayCandidate(registration);
        String option = input("Enter 1 to cast Vote or 0 to Menu: ");

        if (option.equals("1")) {
            election.castVote();
            displayCandidateForSpecifiedPosition();
        }
        else if (option.equals("0")) displayMenu();
        else {
            display("Invalid option");
            handleVotingForPosition(position);
        }
    }

    private static void manageResultOptions(String position){
        ElectionProcess election = new ElectionProcess(position);
        election.displayResult(registration);
        String option =  input("Enter 1 to result menu or 0 to Main Menu: ");

        if (option.equals("1")) displayResultForSpecifiedPosition();
        else if (option.equals("0")) displayMenu();
        else {
            display("Invalid option");
            manageResultOptions(position);
        }

    }

    private static void displayPosition(){
        display("""
            
                      Positions
             ===========================
             1 -> President Position
             2 -> Vice President Position
             3 -> Governor Position
             4 -> Senator Position
             5 -> Main Menu
             """);

    }

    private static void logout(){
        if(loggedinVoter == null){
            display("You are not logged in");
        }
        else{
            loggedinVoter = null;
            display("==============================================");
            display("Logout successful");
            display("==============================================");
        }
    }


}

