package BackEnd;

import FrontEnd.EndGame;
import FrontEnd.GameMenu;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/*
 * Field
 * Class describing a field's data.
 */
public class Field {

    /**
     * A two-dimensional array of Cell objects.
     */
    public static Cell[][] field;

    public static List<Integer> generatedFigures = new LinkedList<>();

    static String hostName = null;
    static String opponentName = null;
    static int hostFigures = 0;
    static int opponentFigures = 0;
    static int hostSeconds = 0;
    static int opponentSeconds = 0;
    static int serverPort = 0;
    static boolean singlePlayer = true;
    static boolean host = true;
    static Socket clientSocket;
    static BufferedReader br;

    static int step = 0;

    public static int nextFigure() {
        if (generatedFigures.size() == step) {
            generateNewFigure();
        }
        return generatedFigures.get(step++);
    }

    static void generateNewFigure() {
        Random random = new Random();
        int next = random.nextInt(31);
        generatedFigures.add(next);
    }

    /**
     * Initializes an empty field.
     */
    public static void initialize() {
        field = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[j][i] = new Cell();
            }
        }
    }

    public static void startGame(String name, int port, boolean singlePlayer, boolean host) {
        if (singlePlayer) {
            return;
        }
        serverPort = port;

        if (host) {
            Field.host = true;
            hostName = name;
            try {
                Thread thread = new ServerThread(serverPort);
                thread.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Field.host = false;
            try {
                InetAddress serverHost = InetAddress.getByName("localhost");
                serverPort = port;

                clientSocket = new Socket(serverHost, serverPort);
                PrintStream ps = new PrintStream(clientSocket.getOutputStream());
                ps.println(name);

                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                GameMenu.opponentNameLabel.setText("Opponent name: " + br.readLine());
                GameMenu.timer.start();
//                clientSocket.close();
            } catch (Exception ex) {
                EndGame.init(0, 0, "", true);
            }
        }
    }

    public static String getGameResult() {
        if (singlePlayer) {
            return "I've won... but at what cost?";
        }

        if (hostFigures > opponentFigures) {
            return hostName + " won with " + hostFigures + " turns.";
        }
        if (hostFigures < opponentFigures) {
            return opponentName + " won with " + opponentFigures + " turns.";
        }

        if (hostSeconds > opponentSeconds) {
            return hostName + " won in time with " + hostSeconds + " sec.";
        }
        if (hostSeconds < opponentSeconds) {
            return opponentName + " won in time with " + opponentSeconds + " sec.";
        }
        return "In war, whichever side may call itself the victor, " +
                "there are no winners, but all are losers";
    }

    /**
     * Adds figure to a field. Occupies needed cells.
     * @param x x coordinate to add a figure
     * @param y y coordinate to add a figure
     * @param figure a figure to add
     */
    public static void addFigure(int x, int y, Figure figure) {
        // Offset from the top-left corner where dragging started.
        x -= figure.getXOffSet();
        y -= figure.getYOffSet();

        var steps = figure.whereToGo;
        java.awt.Point xy;
        for (int i = 0; i < steps.length(); i++) {
            xy = makeStep(steps, i, x, y);
            x = xy.x;
            y = xy.y;
            field[y][x].setCellOccupied(true);
        }
    }

    /**
     * Check whether the figure can be added to the field at the provided position.
     * @param x x coordinate to add a figure
     * @param y y coordinate to add a figure
     * @param figure a figure to add
     * @return true if figure can be added and false otherwise
     */
    public static boolean checkFigure(int x, int y, Figure figure) {
        // Offset from the top-left corner where dragging started.
        x -= figure.getXOffSet();
        y -= figure.getYOffSet();
        var steps = figure.whereToGo;
        java.awt.Point xy;
        for (int i = 0; i < steps.length(); i++) {
            xy = makeStep(steps, i, x, y);
            x = xy.x;
            y = xy.y;
            try{
                if (field[y][x].isCellOccupied())
                    return false;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param steps String with directions to draw.
     * @param i char index
     * @param x x coordinate to add a figure
     * @param y y coordinate to add a figure
     * @return Point object with updated x and y coordinates
     */
    public static Point makeStep(String steps, int i, int x, int y) {
        switch (steps.charAt(i)) {
            case 's':
                break;
            case 'r':
                ++x;
                break;
            case 'l':
                --x;
                break;
            case 'd':
                ++y;
                break;
            case 'u':
                --y;
                break;
            case 'b':
                y += 2;
                break;
        }
        return new Point(x, y);
    }
}

class ServerThread extends Thread {
    int port;

    ServerThread(int port) {
        this.port = port;
    }

    public void run () {
        try {
            ServerSocket sumServer = new ServerSocket(port);

            while (true) {
                Socket dataSocket = sumServer.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
                if (Field.opponentName == null) {
                    GameMenu.opponentNameLabel.setText("Opponent name: " + br.readLine());
                    GameMenu.timer.start();

                    PrintStream ps = new PrintStream(dataSocket.getOutputStream());
                    ps.println(Field.hostName);
                    ps.flush();
                }
                dataSocket.close();
            }
        } catch (Exception ex) {
            EndGame.init(0, 0, "", true);
        }
    }
}