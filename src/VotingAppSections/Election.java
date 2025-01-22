package VotingAppSections;

public class Election {
    private int electionID;
    private String electionName;
    private String electionDescription;
    private String electionDate;
    private String electionTime;
    private String electionStatus;

    public Election(int electionID, String electionName, String electionDescription, String electionDate, String electionTime, String electionStatus) {
        this.electionID = electionID;
        this.electionName = electionName;
        this.electionDescription = electionDescription;
        this.electionDate = electionDate;
        this.electionTime = electionTime;
    }
}
