import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI extends JFrame implements KeyListener, ActionListener {

    static String quote = "";
    API api = new API();
    private static JPanel panel = new JPanel();
    private static JTextArea dialog = new JTextArea(25, 55);
    private static JTextArea input = new JTextArea(2, 43);
    private JScrollPane scroll = new JScrollPane(
            dialog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private static JButton mode = new JButton("Dark Mode");
    private static boolean dark = false;
    private static Border borderB = BorderFactory.createLineBorder(Color.BLACK);
    private static Border borderW = BorderFactory.createLineBorder(Color.WHITE);
    private Font font = new Font("Futura", Font.PLAIN, 15);
    private static String data;
    private static BufferedWriter writer;
    private static BufferedReader br;

    public GUI() throws IOException, InterruptedException, URISyntaxException {
        super("NBAStats Bot");
        input.setBorder(BorderFactory.createCompoundBorder(borderB,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        dialog.setFont(font);
        input.setFont(font);
        setSize(725, 650); // 725 650
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dialog.setEditable(false);
        input.addKeyListener(this);
        mode.addActionListener(this);
        panel.add(scroll);
        panel.add(input);
        panel.setBackground(Color.WHITE);
        panel.add(mode);
        add(panel);
        setLocationRelativeTo(null);
        darkCheck();
        setVisible(true);
        addText(" NBAStats Bot:  Welcome to the NBAStats Bot! Here are the commands:\n\n To find a player, input: \"player <firstName> <lastName>\" \n\n To find a team, input: \"team <teamName>\"\n\n Example input: \"player lebron james\"\n\n All search keys must be separated by a space.\n\n To see the list of commands, input: \"help\"\n\n");
    }

    public static void botMessage(String message) throws IOException, InterruptedException, URISyntaxException {
        addText("\n NBAStats Bot: " + "\n\n" + message);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            switchMode();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            input.setEditable(false);
            quote = input.getText();
            quote = quote.trim();
            String[] coms = quote.split(" ");
            quote = "";
            for (int i = 0; i < coms.length; i++) {
                if (!coms[i].equals("")) {
                    quote += coms[i] + " ";
                }
            }
            input.setText("");
            GUI.addText("=================================================================\n\n");
            addText(" You: " + quote + "\n");
            if (Main.internet) {
                try {
                    String[] c = quote.split(" ", 0);
                    quote = quote.trim();
                    if (quote.toLowerCase().equals("help")) {
                        addText(" \n NBAStats Bot: Here are the commands:\n\n To find a player, input: \"player <firstName> <lastName>\" \n\n To find a team, input: \"team <teamName>\"\n\n Example input: \"player lebron james\"\n\n All search keys must be separated by a space.\n\n ");
                    } else if (c[0].toLowerCase().equals("team") || c[0].toLowerCase().equals("player")) {
                        if (getCommand().toLowerCase().equals("player")) {
                            if (getPlayerFirst().equals("nicholas") && getPlayerLast().equals("gyorgypal")) {
                                Team my = new Team(42069, "TOR", "Toronto", "Western", "Pacific",
                                        "Toronto Thorny Devils",
                                        "Thorny Devils");
                                Player me = new Player(
                                        42069, "Nicholas", "Gyorgypal", "G", 6, 9, 275, my);
                                Stats yes = new Stats(82, "48:00", 99, 50, 50, 30, 30, 99, 100, 0.0);
                                Stats.temporary = 2070;
                                botMessage(me.toString() + yes.toString());
                            } else if (getPlayerFirst().equals("shaheer") && getPlayerLast().equals("amir")) {
                                Team my = new Team(42069, "TOR", "Toronto", "Western", "Pacific",
                                        "Toronto Thorny Devils",
                                        "Thorny Devils");
                                Player me = new Player(
                                        69420, "Shaheer", "Amir", "G", 7, 3, 342, my);
                                Stats yes = new Stats(81, "47:16", 44.64, 15.35, 20.53, 5.52, 6.68, 55.37, 49.53, 0.01);
                                Stats.temporary = 2069;
                                botMessage(me.toString() + yes.toString());
                            } else if (getPlayerFirst().equals("mr") && getPlayerLast().equals("brunetti")) {
                                Team my = new Team(42069, "TOR", "Toronto", "Western", "Pacific",
                                        "Toronto Thorny Devils",
                                        "Thorny Devils");
                                Player me = new Player(
                                        999999, "Mr.", "Brunetti (G.O.A.T)", "G-C-F", 9, 9, 150, my);
                                Stats yes = new Stats(82, "48:00", 999, 999, 999, 999, 999, 101, 101, 0.0);
                                Stats.temporary = 2070;
                                botMessage(me.toString() + yes.toString());
                            } else if (c.length > 2) {
                                api.findPlayer();

                            } else {
                                addText("\n NBAStats Bot: Input Error\n\n");
                            }
                        }
                        if (GUI.getCommand().toLowerCase().equals("team")) {
                            if (c.length > 1) {
                                api.findTeam();
                            } else {
                                addText("\n NBAStats Bot: Input Error\n\n");
                            }
                        }
                    } else {
                        addText("\n NBAStats Bot: Input Error\n\n");
                    }
                } catch (IOException | InterruptedException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            } else {
                GUI.addText("\n NBAStats Bot: No internet connection, please connect to the internet\n\n");
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            input.setEditable(true);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void addText(String str) {
        dialog.setText(dialog.getText() + str);
    }

    public static String getCommand() {
        String[] c = quote.split(" ", 0);
        return c[0].replaceAll(" ", "");
    }

    public static String getPlayerFirst() {
        String[] c = quote.split(" ", 0);
        return c[1].replaceAll(" ", "");
    }

    public static String getPlayerLast() {
        String[] c = quote.split(" ", 0);
        return c[2].replaceAll(" ", "");
    }

    public static String getTeam() {
        String[] c = quote.split(" ", 0);
        if (c.length > 2) {
            String s = c[1] + c[2];
            return s;
        }
        return c[1].replaceAll(" ", "");
    }

    public static void darkCheck() throws IOException, FileNotFoundException {
        br = new BufferedReader(new FileReader("mode.txt"));
        data = br.readLine();
        br.close();
        if (data.equals("dark")) {
            switchMode();
        } else {
            dark = true;
            switchMode();
        }
    }

    public static void switchMode() throws IOException, FileNotFoundException {
        writer = new BufferedWriter(new FileWriter("mode.txt"));
        if (!dark) {
            input.setCaretColor(Color.LIGHT_GRAY);
            panel.setBackground(Color.DARK_GRAY.darker());
            dialog.setBackground(Color.DARK_GRAY.darker());
            dialog.setForeground(Color.LIGHT_GRAY);
            input.setBackground(Color.DARK_GRAY.darker());
            input.setForeground(Color.LIGHT_GRAY);
            input.setBorder(BorderFactory.createCompoundBorder(borderW,
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            mode.setText("Light Mode");
            writer.write("dark");
            writer.close();
            dark = true;

        } else {
            input.setCaretColor(Color.BLACK);
            panel.setBackground(Color.WHITE);
            dialog.setBackground(Color.WHITE);
            dialog.setForeground(Color.BLACK);
            input.setBackground(Color.WHITE);
            input.setForeground(Color.BLACK);
            input.setBorder(BorderFactory.createCompoundBorder(borderB,
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            mode.setText("Dark Mode");
            writer.write("light");
            writer.close();
            dark = false;
        }
    }
}
//create a basic gui with a text field and a text area

