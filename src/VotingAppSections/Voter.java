package VotingAppSections;

import java.util.HashSet;
import java.util.Set;

public class Voter {

    private String name;
    private String ID;
    private String password;
    private boolean voted;

    private Set<String> votedPosition;

    public Voter(String name, String ID, String password) {
        validateUserInfo(name,password);
        this.name = name;
        this.ID = ID;
        this.password = password;
        voted = false;
        votedPosition = new HashSet<>();
    }

    public boolean login(String ID, String password) {
        boolean loginSuccessFully = this.ID.equals(ID) && this.password.equals(password);
        if(!loginSuccessFully) throw new IllegalArgumentException("Incorrect details");
        else return true;
    }

    public void castVote(String candidateID) {
        Candidate candidate = Registration.findCandidate(candidateID);
        candidate.increaseVote();
    }

    public void displayVoters(){
        System.out.printf("%-5s  %-20s", "ID", "Voters Name");
        System.out.printf("%n%-5s %-20s", ID, name);
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public boolean getVoted() {
        return voted;
    }

    public boolean hasVotedForPosition(String position){
        return this.votedPosition.contains(position);
    }

    public Set<String> getVotedPosition() {
        return votedPosition;
    }

    private static void validateUserInfo(String name, String password){
        String namePattern = "^[A-Za-z'-]+(?: [A-Za-z'-]+)*$";
        if (!name.matches(namePattern)) throw new IllegalArgumentException("Enter a valid name");
        if(name.isEmpty()) throw new NullPointerException("name cannot be empty");
        validatePassword(password);
    }

    private static void validatePassword(String password){
        String passwordPattern = "^.{4}$";
        if(!password.matches(passwordPattern)) throw new IllegalArgumentException("password can be number or letter but ensure it is 4 in number");

    }

//    public static void main(String[] args) {
//        Voter newVoter = new Voter ("Tijani Olayinka","021", "ADKJDA");
//        System.out.println(newVoter.getVoted());
//     newVoter.displayVoters();
//   }
}

