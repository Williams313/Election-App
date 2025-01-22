package VotingAppSections;

public class Candidate {
    private String ID;
    private String name;
    private String position;
    private int voteCount;

    private enum Position  {
        PRESIDENT,
        GOVERNOR,
        SENATOR,
        VICE_PRESIDENT
    }

    public Candidate(String name, String ID, String position) {
        validateUserInfo(name,position);
        this.ID = ID;
        this.name = name;
        this.position = position;
    }

    public String getID() {
        return ID;
    }

    public String getPosition() {
        return position;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void increaseVote() {
        voteCount++;
    }

    public void displayCandidates() {
        System.out.printf("%n%-5s %-20s %-10s", ID, name, position);
    }

    public String getName() {
        return name;
    }

    private static Position getPositionEnum(String position) {

        try {
            return Position.valueOf(position.toUpperCase());
        } catch (IllegalArgumentException err) {
            return null;
        }

    }

    private static void validateUserInfo(String name, String position){
        String namePattern = "^[A-Za-z'-]+(?: [A-Za-z'-]+)*$";
        if (!name.matches(namePattern)) throw new IllegalArgumentException("Enter a valid name");
        if(name.isEmpty()) throw new NullPointerException("name cannot be empty");
        validatePosition(position);

    }

    private static void validatePosition(String userInput) {
        Position position = getPositionEnum(userInput);
        if (position == null) throw new IllegalArgumentException("Invalid position");

    }

}





