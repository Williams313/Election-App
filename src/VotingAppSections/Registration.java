package VotingAppSections;

import javax.swing.*;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Registration {
    private int numberOfVoters;
    private int numberOfCandidates;
    private static final ArrayList<Voter> registeredVoters = new ArrayList<Voter>();
    private static final ArrayList<Candidate> registeredCandidate = new ArrayList<>();


    public Voter registerVoter(String name, String password) {
        String ID = generateVoterID();
        Voter newVoter = new Voter(name, ID, password);
        registeredVoters.add(newVoter);
        return newVoter;
    }

    public Candidate registerCandidate(String name,String position) {
        String ID = generateCandidateID();
        Candidate newCandidate = new Candidate(name, ID, position);
        registeredCandidate.add(newCandidate);
        return newCandidate;
    }

    public static Voter findVoter(String ID) {
        if(ID.isEmpty()) throw new NullPointerException("ID cannot be empty");

        for (Voter voter : registeredVoters) {
            if(voter.getID().equals(ID)) return voter;
        }
        throw new IllegalArgumentException("Voter with " + ID + " not found");
    }

    public static Candidate findCandidate(String ID) {
        if(ID.isEmpty()) throw new NullPointerException("ID cannot be empty");

        for (Candidate candidate : registeredCandidate) {
            if(candidate.getID().equals(ID)) return candidate;
        }
        throw new IllegalArgumentException("Candidate with " + ID + " not found");
    }

    public void displayCandidate(String position){
        for (Candidate candidate : registeredCandidate) {
            if(candidate.getPosition().equals(position)){
                candidate.displayCandidates();
            }
        }
        System.out.print("\n\n\n");
    }

    public int getNumberOfVoters() {
        return numberOfVoters;
    }

    public int getNumberOfCandidates() {
        return numberOfCandidates;
    }

    public ArrayList<Candidate> getRegisteredCandidates() {
        return registeredCandidate;
    }
    private String generateVoterID(){
        numberOfVoters++;
        return "v" + Integer.toString(numberOfVoters + 10);
    }

    private String generateCandidateID(){
        numberOfCandidates++;
        return "c" + Integer.toString(numberOfCandidates + 10);
    }

}
