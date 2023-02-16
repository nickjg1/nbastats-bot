import java.net.*;
import java.net.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import org.json.*;

public class API {

    static int year = Calendar.getInstance().get(Calendar.YEAR);

    public static String getJSON(String uri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
        HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
        return res.body();
    }

    public void findPlayer() throws IOException, InterruptedException, URISyntaxException {
        ArrayList<Player> players = new ArrayList<Player>();
        JSONArray res = new JSONObject(
                getJSON("https://www.balldontlie.io/api/v1/players/?search=" + GUI.getPlayerLast() + "&per_page=100"))
                        .getJSONArray("data");
        for (int i = 0; i < res.length(); i++) {
            players.add(Player.parsePlayer(res.getJSONObject(i).toString()));
        }
        String firstName = GUI.getPlayerFirst();
        boolean found = false;
        for (Player p : players) {

            if (p.firstName.toLowerCase().replaceAll(" ", "").equals(firstName.toLowerCase().replaceAll(" ", ""))) {

                if (getJSON("https://www.balldontlie.io/api/v1/season_averages?player_ids[]=" + p.id).length() < 20) {
                    String pID = String.valueOf(p.id);
                    GUI.botMessage(p.toString() + Stats
                            .parseStatsException(
                                    getJSON("https://www.balldontlie.io/api/v1/season_averages?season=" + year
                                            + "&player_ids[]=" + p.id),
                                    pID)
                            .toString());
                    found = true;

                } else {
                    Stats.temporary = year - 1;
                    GUI.botMessage(p.toString() + Stats
                            .parseStats(
                                    getJSON("https://www.balldontlie.io/api/v1/season_averages?player_ids[]=" + p.id))
                            .toString());
                    found = true;
                }
            }

        }
        if (!found) {
            GUI.addText("\n NBAStats Bot: Player not found\n\n");
        }
    }

    public void findTeam() throws JSONException, IOException, InterruptedException, URISyntaxException {
        ArrayList<Team> teams = new ArrayList<Team>();
        JSONArray res = new JSONObject(getJSON("https://www.balldontlie.io/api/v1/teams/"))
                .getJSONArray("data");

        String teamName = GUI.getTeam();
        for (int i = 0; i < res.length(); i++) {
            teams.add(Team.parseTeam(res.getJSONObject(i).toString()));
        }
        boolean found = false;
        for (Team t : teams) {
            if (t.city.toLowerCase().replaceAll(" ", "").equals(teamName.toLowerCase().replaceAll(" ", "")) ||
                    t.name.toLowerCase().replaceAll(" ", "").equals(teamName.toLowerCase().replaceAll(" ", ""))) {

                GUI.botMessage(t.toString());
                found = true;
            }
        }
        if (!found) {
            GUI.addText("\n NBAStats Bot: Team not found\n\n");
        }

    }
}
