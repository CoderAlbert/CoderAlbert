package tictactoe;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static String game = "_________";
    private static final Character player1 = 'X';
    private static final Character player2 = 'O';
    private static final HashMap<String, Integer> coordinatesMap = new HashMap<>();

    public static void main(String[] args) {
        fillCoordinatesMap();
        printGrid();
        int moves = 0;

        while(!isGameFinished())
        {
            Character player = moves % 2 == 0? player1 : player2;
            makeMove(player);
            printGrid();

            if(isImpossible())
            {
                System.out.println("Impossible");
                return;
            }

            if(hasWinner())
            {
                return;
            }

            moves++;
        }

        System.out.println("Draw");

    }

    private static void fillCoordinatesMap()
    {
        coordinatesMap.put("1 1", 0);
        coordinatesMap.put("1 2", 1);
        coordinatesMap.put("1 3", 2);
        coordinatesMap.put("2 1", 3);
        coordinatesMap.put("2 2", 4);
        coordinatesMap.put("2 3", 5);
        coordinatesMap.put("3 1", 6);
        coordinatesMap.put("3 2", 7);
        coordinatesMap.put("3 3", 8);
    }

    private static void makeMove(Character player)
    {
        Scanner scanner = new Scanner(System.in);
        boolean areCoordinatesValid;
        do {
            System.out.println("Enter the coordinates: ");
            String coordinates = scanner.nextLine();
            areCoordinatesValid = areCoordinatesValid(coordinates);

            if (areCoordinatesValid) {
                addMove(coordinates, player);
            }

        } while (!areCoordinatesValid);
    }

    public static void printGrid()
    {
        int gameIdx = 0;
        System.out.println("---------");
        for(int column = 1; column <= 3; column++)
        {
            System.out.print("| ");
            for(int row = 1; row <= 3; row++)
            {
                System.out.print(game.charAt(gameIdx++) + " ");
            }

            System.out.print("|");
            System.out.println();
        }

        System.out.println("---------");
    }

    public static boolean hasWinner()
    {

        if((threeRow(player1) || threeColumn(player1) || threeDiagonal(player1)) &&
                (threeRow(player2) || threeColumn(player2) || threeDiagonal(player2)))
        {
            System.out.println("Impossible");
            return true;
        }

        if(threeRow(player1) || threeColumn(player1) || threeDiagonal(player1))
        {
            System.out.println(player1 + " wins");
            return true;
        }

        if(threeRow(player2) || threeColumn(player2) || threeDiagonal(player2))
        {
            System.out.println(player2 + " wins");
            return true;
        }

        return false;
    }

    public static boolean threeRow(Character player)
    {
        String winnerCondition = "" + player + player + player;

        for(int i = 0; i < 9; i += 3)
        {
            String row = "" + game.charAt(i) + game.charAt(i + 1) + game.charAt(i + 2);
            if(winnerCondition.equals(row)) return true;
        }

        return false;
    }

    public static boolean threeColumn(Character player)
    {
        return game.charAt(0) == player && game.charAt(3) == player && game.charAt(6) == player ||
                game.charAt(1) == player && game.charAt(4) == player && game.charAt(7) == player ||
                game.charAt(2) == player && game.charAt(5) == player && game.charAt(8) == player;
    }

    public static boolean threeDiagonal(Character player)
    {
        return game.charAt(0) == player && game.charAt(4) == player && game.charAt(8) == player ||
                game.charAt(2) == player && game.charAt(4) == player && game.charAt(6) == player;
    }

    public static boolean isGameFinished()
    {
        return !game.contains("_");
    }

    public static boolean isImpossible()
    {
        int counterPlayer1 = 0;
        int counterPlayer2 = 0;
        for(char move : game.toCharArray())
        {
            if(move == player1) counterPlayer1++;
            if(move == player2) counterPlayer2++;
        }


        return Math.abs(counterPlayer1 - counterPlayer2) >= 2;
    }

    public static void addMove(String coordinates, Character player)
    {
        StringBuilder newGame = new StringBuilder((game));
        int gameCoordinates = coordinatesMap.get(coordinates);
        newGame.setCharAt(gameCoordinates, player) ;
        game = newGame.toString();
    }

    public static boolean areCoordinatesValid(String coordinates)
    {
        if(Pattern.matches("[a-zA-Z]+", coordinates))
        {
            System.out.println("You should enter numbers!");
            return false;
        }


        if(coordinates.length() != 3
                || Integer.parseInt("" + coordinates.charAt(0)) > 3
                || Integer.parseInt("" + coordinates.charAt(2)) > 3)
        {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }

        if(isFieldOccupied(coordinates))
        {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }

        return true;
    }
    public static boolean isFieldOccupied(String coordinates)
    {
        int gameCoordinates = coordinatesMap.get(coordinates);

        return game.charAt(gameCoordinates) != '_';
    }
}