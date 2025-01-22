package VotingAppSections;

import java.util.ArrayList;

public class VoteRecord {
    private static final ArrayList<VoteRecord> voteRecords = new ArrayList<VoteRecord>();


    private String voterID;
    private String candidateID;
    private String electionName;

    public VoteRecord(String voterID, String candidateID, String electionName) {
        this.voterID = voterID;
        this.candidateID = candidateID;
        this.electionName = electionName;
    }

    public void keepRecord(String voterID, String candidateID, String electionName) {
        VoteRecord newRecord = new VoteRecord(voterID, candidateID, electionName);
        voteRecords.add(newRecord);
    }

}