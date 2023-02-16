import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    static boolean internet;

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        new GUI();
        try {
            internet = true;
            URL u = new URL("https://www.google.com");

            java.net.URLConnection conn = u.openConnection();
            conn.connect();

        } catch (Exception e) {
            internet = false;
            GUI.addText(" NBAStats Bot: No internet connection, please connect to the internet\n\n");
        }
    }
}
