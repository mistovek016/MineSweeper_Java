import java.util.Scanner;

public class Player {
    public static void main(String[] argus) {

        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Please enter your preference: easy (8x8), medium (15x15), or hard (20x20): ");
            String pref = sc.nextLine();
            int len, n_mines;
            switch (pref) {
                case "easy" -> {
                    len = 8;
                    n_mines = 15;
                }
                case "medium" -> {
                    len = 15;
                    n_mines = 30;
                }
                case "hard" -> {
                    len = 20;
                    n_mines = 100;
                }
                default -> {
                    System.out.println("Invalid choice!");
                    len = 5;
                    n_mines = 5;
                }
            }

            System.out.println("----------RULES----------");
            System.out.println("Coordinates of the cells must be separated by commas.");
            System.out.println("Ex.: 1,1 for the top-left cell, and 3,4 for the cell in the 3rd row and 4th row");
            System.out.println("It works just like matrix indexing - here's an example:");
            System.out.println("[1, 1] [1, 2] [1, 3] ...");
            System.out.println("[2, 1] [2, 2] ...");
            System.out.println("[3, 1] ...");
            System.out.println("...");
            System.out.println("To flag prefix the coordinates immediately with \'f\': f1,2");
            System.out.println("To UNflag prefix the coordinates immediately with \'uf\': uf1,2");
            System.out.println("To end the game, enter: end");
            System.out.println("\n----------START----------\n");
            System.out.println("-> Number of mines: " + n_mines);
            System.out.print("-> Co-ordinates: ");

            String coords = sc.nextLine();
            if (coords.contains("end"))
                System.exit(0);
            int firstNum = 0;
            for (int i = 0; i < coords.length(); i++) {
                if (Character.isDigit(coords.charAt(i))) {
                    firstNum = i;
                    break;
                }
            }
            int r = Integer.parseInt(coords.substring(firstNum, coords.indexOf(',')));
            int c = Integer.parseInt(coords.substring(coords.indexOf(',') + 1));
            r--;
            c--;
            MsGrid grid = new MsGrid(len, n_mines, r, c);

            while (true) {
                System.out.print("-> Co-ordinates: ");
                coords = sc.nextLine();
                if (coords.contains("end"))
                    System.exit(0);
                firstNum = 0;
                for (int i = 0; i < coords.length(); i++) {
                    if (Character.isDigit(coords.charAt(i))) {
                        firstNum = i;
                        break;
                    }
                }
                r = Integer.parseInt(coords.substring(firstNum, coords.indexOf(',')));
                c = Integer.parseInt(coords.substring(coords.indexOf(',') + 1));
                r--;
                c--;
                if (coords.charAt(0) == 'f' || (coords.charAt(0) == 'u' && coords.charAt(1) == 'f'))
                    grid.toggleFlag(r, c);
                else
                    grid.showCell(r, c);
                grid.checkWin();
            }
        }
    }
}