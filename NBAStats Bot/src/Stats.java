import java.io.IOException;
import org.json.*;

public class Stats {
    int games;
    String minutes;
    double points;
    double rebounds;
    double assists;
    double blocks;
    double steals;
    static double fieldGoal;
    static double threePoint;
    static double turnovers;
    static int temporary;

    public Stats(int newGames, String newMinutes, double newPoints, double newRebounds, double newAssists,
            double newBlocks, double newSteals, double newFieldGoal, double newThreePoint, double newTurnovers) {
        games = newGames;
        minutes = newMinutes;
        points = newPoints;
        rebounds = newRebounds;
        assists = newAssists;
        blocks = newBlocks;
        steals = newSteals;
        fieldGoal = newFieldGoal;
        threePoint = newThreePoint;
        turnovers = newTurnovers;
    }

    public static Stats parseStats(String responseBody) {
        JSONObject statsData = new JSONObject(responseBody).getJSONArray("data").getJSONObject(0);
        Stats stats;

        stats = new Stats(
                statsData.getInt("games_played"),
                statsData.getString("min"),
                statsData.getDouble("pts"),
                statsData.getDouble("reb"),
                statsData.getDouble("ast"),
                statsData.getDouble("stl"),
                statsData.getDouble("blk"),
                statsData.getDouble("fg_pct"),
                statsData.getDouble("fg3_pct"),
                statsData.getDouble("turnover"));
        threePoint = threePoint * 100;
        fieldGoal = fieldGoal * 100;
        return stats;

    }

    public static Stats parseStatsException(String responseBody, String pID) throws IOException, InterruptedException {
        int year = API.year + 1;

        Stats stats;
        String uri;
        String r = "";
        for (int i = year; i > 1978; i--) {
            uri = "https://www.balldontlie.io/api/v1/season_averages?season=" + i + "&player_ids[]="
                    + pID;
            if (r.length() > 50) {
                temporary = i + 1;
                break;
            } else {
                r = API.getJSON(uri);
            }
        }
        year = API.year + 1;
        JSONObject statsData = new JSONObject(r).getJSONArray("data").getJSONObject(0);
        stats = new Stats(
                statsData.getInt("games_played"),
                statsData.getString("min"),
                statsData.getDouble("pts"),
                statsData.getDouble("reb"),
                statsData.getDouble("ast"),
                statsData.getDouble("stl"),
                statsData.getDouble("blk"),
                statsData.getDouble("fg_pct"),
                statsData.getDouble("fg3_pct"),
                statsData.getDouble("turnover"));
        threePoint = threePoint * 100;
        fieldGoal = fieldGoal * 100;

        return stats;

    }

    @Override
    public String toString() {
        return " " + temporary + " Season Stats"
                + "\n" + " Games Played: " + games
                + "\n" + " Minutes: " + minutes + "\n"
                + " Points: " + points + "\n"
                + " Rebounds: " + rebounds + "\n"
                + " Assists: " + assists + "\n"
                + " Steals: " + steals + "\n"
                + " Blocks: " + blocks + "\n"
                + " Field Goal: " + (int) fieldGoal + "%\n"
                + " 3 Point: " + (int) threePoint + "%\n"
                + " Turnovers: " + turnovers + "\n\n";
    }

}
