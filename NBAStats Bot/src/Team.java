import org.json.JSONObject;

public class Team {
    int id;
    String abbreviation;
    String city;
    String conference;
    String division;
    String fullName;
    String name;

    public Team(int newId, String newAbbreviation, String newCity, String newConference, String newDivision,
            String newFullName, String newName) {
        id = newId;
        abbreviation = newAbbreviation;
        city = newCity;
        conference = newConference;
        division = newDivision;
        fullName = newFullName;
        name = newName;
    }

    public static Team parseTeam(String responseBody) {
        JSONObject teamData = new JSONObject(responseBody);
        Team team;
        team = new Team(
                teamData.getInt("id"),
                teamData.getString("abbreviation"),
                teamData.getString("city"),
                teamData.getString("conference"),
                teamData.getString("division"),
                teamData.getString("full_name"),
                teamData.getString("name"));
        return team;
    }

    @Override
    public String toString() {
        return " Team Information: \n" + " Abbreviation: " + abbreviation + "\n"
                + " Conference: " + conference + "\n"
                + " Division: " + division + "\n"
                + " Full Name: " + fullName + "\n";

    }
}