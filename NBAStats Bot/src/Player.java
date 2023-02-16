import org.json.*;

public class Player {
    int id;
    String firstName;
    String lastName;
    String position;
    int heightFeet;
    int heightInches;
    int weightPounds;
    Team team;

    public Player(int newId, String newFirstName, String newLastName, String newPosition, int newHeightFeet,
            int newHeightInches, int newWeightPounds, Team newTeam) {
        id = newId;
        firstName = newFirstName;
        lastName = newLastName;
        position = newPosition;
        team = newTeam;
        heightFeet = newHeightFeet;
        heightInches = newHeightInches;
        weightPounds = newWeightPounds;
    }

    public static Player parsePlayer(String responseBody) {
        JSONObject playerData = new JSONObject(responseBody);
        JSONObject teamData = playerData.getJSONObject("team");
        Team team;
        Player player;
        int heightF = -1;
        int heightI = -1;
        int weightP = -1;
        if (!playerData.get("height_feet").getClass().getName().equals("org.json.JSONObject$Null")) {
            heightF = playerData.getInt("height_feet");
        }
        if (!playerData.get("height_inches").getClass().getName().equals("org.json.JSONObject$Null")) {
            heightI = playerData.getInt("height_inches");
        }
        if (!playerData.get("weight_pounds").getClass().getName().equals("org.json.JSONObject$Null")) {
            weightP = playerData.getInt("weight_pounds");
        }

        team = new Team(
                teamData.getInt("id"),
                teamData.getString("abbreviation"),
                teamData.getString("city"),
                teamData.getString("conference"),
                teamData.getString("division"),
                teamData.getString("full_name"),
                teamData.getString("name"));

        player = new Player(

                playerData.getInt("id"),
                playerData.getString("first_name"),
                playerData.getString("last_name"),
                playerData.getString("position"),
                heightF,
                heightI,
                weightP,
                team);
        return player;
    }

    @Override
    public String toString() {
        String heightFeetOut = String.valueOf(heightFeet) + "'";
        String heightInchesOut = String.valueOf(heightInches);
        String weightPoundsOut = String.valueOf(weightPounds) + "lbs";
        if (position.equals("")) {
            position = "N/A";
        }
        if (heightFeet == -1) {
            heightFeetOut = "N/A";
        }
        if (heightInches == -1) {
            heightInchesOut = "";
        }
        if (weightPounds == -1) {
            weightPoundsOut = "N/A";
        }

        return " Name: " + firstName + " "
                + lastName + "\n"
                + " Position: " + position + "\n"
                + " Height: " + heightFeetOut
                + heightInchesOut + "\n"
                + " Weight: " + weightPoundsOut + "\n\n" + team + "\n";

    }

}
