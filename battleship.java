package battleship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

class Player {
    final String name;

    Player(String name) {
        this.name = name;
    }
    final String[][] map = {
            {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
            {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"}
    };
    Ships carrier = new Ships(5, "Aircraft Carrier");
    Ships battleship = new Ships(4, "Battleship");
    Ships submarine = new Ships(3, "Submarine");
    Ships cruiser = new Ships(3, "Cruiser");
    Ships destroyer = new Ships(2, "Destroyer");
    Ships[] ships = {carrier, battleship, submarine, cruiser, destroyer};
    final List<String> occupied = new ArrayList<>();
    final void printMap() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < map.length; i++) {
            System.out.print((char) (65 + i) + " ");
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    final void printMapTurn(Player opponent) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < opponent.map.length; i++) {
            System.out.print((char) (65 + i) + " ");
            for (int j = 0; j < opponent.map[i].length; j++) {
                System.out.print("~ ");
            }
            System.out.println();
        }
        System.out.println("---------------------");
        this.printMap();
    }
}

class Ships {
    final int size;
    final String type;
    Ships(int size, String type) {
        this.size = size;
        this.type = type;
    }
    final List<String> body = new ArrayList<>();
    String start;
    String end;
}

public class Main {

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);

        Player player1 = new Player("player 1");
        Player player2 = new Player("player 2");
        setShips(player1, scanner);
        setShips(player2, scanner);
        System.out.println("The game starts!\n");
        while (true) {
            castShot(player1, player2, scanner);
            if (verifyWin(player1) == 1) {
                return;
            }
            castShot(player2, player1, scanner);
            if (verifyWin(player2) == 1) {
                return;
            }
        }

    }
    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setShips(Player player, Scanner scanner) {
        System.out.printf("%s, place your ships on the game field\n", player.name);
        System.out.println();
        player.printMap();
        for (int i = 0; i < player.ships.length; i++) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", player.ships[i].type, player.ships[i].size);
            System.out.println();
            if (validateCoordinates(player, player.ships[i], scanner.nextLine()) < 0) {
                i--;
            }
        }
        promptEnterKey();
    }
    private static void castShot(Player player, Player opponent, Scanner scanner) {
        player.printMapTurn(opponent);
        System.out.println("Take a shot!");
        System.out.printf("%s, it's your turn:\n", player.name);
        while (true) {
            if (verifyHit(player, opponent, scanner.next()) == 1) {
                return;
            }
        }
    }
    private static int verifyWin(Player player) {
        List<String> check = new ArrayList<>();
        for (String[] row : player.map) {
            Collections.addAll(check, row);
        }
        if (!(check.contains("O"))) {
            return 1;
        }
        return -1;
    }
    private static int verifyHit(Player player, Player opponent,  String coordinate) {
        coordinate = coordinate.toUpperCase();

        int row = coordinate.charAt(0) - 65;
        int col = Integer.parseInt(coordinate.substring(1)) - 1;
        if (row > 9 || row < 0 || col > 9 || col < 0 ) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            return -1;
        }
        if (!(opponent.map[row][col].equals("~")) && !(opponent.map[row][col].equals("M"))) {
            opponent.map[row][col] = "X";
            if (verifyWin(opponent) == 1) {
                System.out.printf("You sank the last ship. %s won. Congratulations!\n", player.name);
                return 1;
            }
            if (hasShipSunken(opponent, coordinate)) {
                System.out.println("You sank a ship!");
            } else {
                System.out.println("You hit a ship!\n");
            }
            promptEnterKey();
            return 1;
        } else {
            opponent.map[row][col] = "M";
            System.out.println("You missed!\n");
        }
        promptEnterKey();
        return 1;
    }
    private static boolean hasShipSunken(Player player, String coordinate) {
        for (Ships ship : player.ships) {
            ship.body.remove(coordinate);
        }
        for (Ships ship : player.ships) {
            if (ship.body.isEmpty()) {
                ship.body.add("empty");
                return true;
            }
        }
        return false;
    }
    private static int validateCoordinates(Player player, Ships ship, String inputCoordinates) {
        inputCoordinates = inputCoordinates.toUpperCase();
        String[] split = inputCoordinates.split(" ");

        String start = split[0];
        String end = split[1];

        char startRow = start.charAt(0);
        int startCol = Integer.parseInt(start.substring(1) + "");
        char endRow = end.charAt(0);
        int endCol = Integer.parseInt(end.substring(1) + "");

        if (startCol > endCol || startRow > endRow) {
            int tempCol = startCol;
            startCol = endCol;
            endCol = tempCol;

            char tempRow = startRow;
            startRow = endRow;
            endRow = tempRow;
        }

        if (startRow == endRow || startCol == endCol) {
            ship.start = start;
            ship.end = end;
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
            return -1;
        }

        if (startRow == endRow && Math.abs(startCol - endCol) + 1 == ship.size) {
            ship.start = start;
            ship.end = end;
        } else if (startCol == endCol && Math.abs((int) startRow - endRow) + 1 == ship.size) {
            ship.start = start;
            ship.end = end;
        } else {
            System.out.printf("Error! Wrong length of the %s Try again:\n", ship.type);
            return -1;
        }
        System.out.println();
        return updateMap(player, ship, startRow, startCol, endRow, endCol);

    }
    private static int updateMap(Player player, Ships ship, char startRow, int startCol, char endRow, int endCol) {
        if (startCol == endCol) {
            for (int i = 0; i < ship.size; i++) {
                if (player.occupied.contains("" + (char) (startRow + i) + startCol) || player.occupied.contains("" + endRow + endCol)) {
                    placementError(ship);
                    return -1;
                }
                ship.body.add("" + (char) (startRow + i) + startCol);
                player.occupied.add("" + (char) (startRow + i) + startCol);
                player.occupied.add("" + (char) (startRow + i) + (startCol + 1));
                player.occupied.add("" + (char) (startRow + i) + (startCol - 1));
                player.map[((startRow - 64) - 1) + i][startCol - 1] = "O";
            }
        } else {
            for (int i = 0; i < ship.size; i++) {
                if (player.occupied.contains("" + startRow + (startCol + i))) {
                    placementError(ship);
                    return -1;
                }
                ship.body.add("" + startRow + (startCol + i));
                player.occupied.add("" + startRow + (startCol + i));
                player.occupied.add("" + (char) (startRow + 1) + (startCol + i));
                player.occupied.add("" + (char) (startRow - 1) + (startCol + i));
                player.map[(startRow - 64) - 1][(startCol - 1) + i] = "O";
            }
        }
        player.printMap();
        return 1;
    }
    private static void placementError(Ships ship) {
        System.out.println("Error! You placed it too close to another one. Try again:");
        ship.start = "";
        ship.end = "";
    }
}
