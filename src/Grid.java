import java.util.Scanner;

public class Grid {
    int length = 7;
    int nearby = 0;
    String block = "|     ";
    String blockMine = "|  X  ";
    String blockSafe = "|  " + nearby + "  ";
    String border = "+-----";
    String borderEnd = "+";
    String[][] grid;
    String[][] secretGrid;

    public void genGrid(int length) {
        this.length = length;
        grid = new String[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                grid[i][j] = block;
            }
        }
    }

    public void genSecretGrid(int length) {
        this.length = length;
        secretGrid = new String[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (Math.random() < 0.18) {
                    secretGrid[i][j] = blockMine;
                } else {
                    secretGrid[i][j] = block;
                }
            }
        }
    }

    public void printGrid() {
        for (int i = 0; i < length; i++) {

            for (int j = 0; j < length; j++) {
                System.out.print(border);
            }
            System.out.println(borderEnd);

            for (int j = 0; j < length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println("|");
        }

        for (int i = 0; i < length; i++) {
            System.out.print(border);
        }
        System.out.println(borderEnd);
    }

    public void revealGrid() {
        for (int i = 0; i < length; i++) {

            for (int j = 0; j < length; j++) {
                System.out.print(border);
            }
            System.out.println(borderEnd);

            for (int j = 0; j < length; j++) {
                System.out.print(secretGrid[i][j]);
            }
            System.out.println("|");
        }

        for (int i = 0; i < length; i++) {
            System.out.print(border);
        }
        System.out.println(borderEnd);
    }

    public void makeGuess() {
        Scanner scan = new Scanner(System.in);
        boolean guessingOpen = true;
        while (guessingOpen == true) {
            int guessX = 0;
            int guessY = 0;
            boolean waitingForValidX = true;
            while (waitingForValidX == true) {
                System.out.println("Please enter X coordinate (1-7):");

                if (!scan.hasNextInt()) {
                    System.out.println("ERROR: Must input a number");
                    scan.next();
                    continue;
                }
                guessX = scan.nextInt() - 1;
                if (guessX < 0 || guessX > length) {
                    System.out.println("ERROR: Invalid X coordinate");

                } else if (guessX >= 0 && guessX < length) {
                    waitingForValidX = false;

                }

            }

            boolean waitingForValidY = true;
            while (waitingForValidY == true) {
                System.out.println("Please enter Y coordinate (1-7):");

                if (!scan.hasNextInt()) {
                    System.out.println("ERROR: Must input a number");
                    scan.next();
                    continue;
                }
                guessY = scan.nextInt() - 1;
                if (guessY < 0 || guessY > length) {
                    System.out.println("ERROR: Invalid X coordinate");

                } else if (guessY >= 0 && guessY < length) {
                    waitingForValidY = false;

                }

            }

            if (secretGrid[guessY][guessX] == blockMine) {
                System.out.println("~~~~~~~~~~~BOOM!~~~~~~~~~~~");
                System.out.println("~~~~~~~~~GAME OVER~~~~~~~~~");
                Scanner newGame = new Scanner(System.in);
                revealGrid();
                System.out.println("New Game? y/n:");
                char resp = newGame.next().charAt(0);
                if (resp == 'y') {
                    System.out.println("~~~~~~NEW GAME STARTING~~~~~~");
                    startGame();
                } else if (resp == 'n') {
                    System.out.println("bye :)");
                    break;
                }
                newGame.close();
                guessingOpen = false;

            } else {
                System.out.println("Safe!");
                checkNearby(guessY, guessX);
                grid[guessY][guessX] = "|  " + nearby + "  ";
                printGrid();
            }

        }
        scan.close();
    }

    public void checkNearby(int guessY, int guessX) {
        nearby = 0;
        if (guessY > 0 && secretGrid[guessY - 1][guessX] == blockMine) {
            nearby++;
        }
        if (guessY > 0 && guessX > 0 && secretGrid[guessY - 1][guessX - 1] == blockMine) {
            nearby++;
        }
        if (guessY > 0 && guessX < length - 1 && secretGrid[guessY - 1][guessX + 1] == blockMine) {
            nearby++;
        }
        if (guessX > 0 && secretGrid[guessY][guessX - 1] == blockMine) {
            nearby++;
        }
        if (guessX < length - 1 && secretGrid[guessY][guessX + 1] == blockMine) {
            nearby++;
        }
        if (guessY < length - 1 && guessX > 0 && secretGrid[guessY + 1][guessX - 1] == blockMine) {
            nearby++;
        }
        if (guessY < length - 1 && secretGrid[guessY + 1][guessX] == blockMine) {
            nearby++;
        }
        if (guessY < length - 1 && guessX < length - 1 && secretGrid[guessY + 1][guessX + 1] == blockMine) {
            nearby++;
        }
    }

    public void startGame() {
        Grid g = new Grid();
        g.genGrid(7);
        g.printGrid();
        g.genSecretGrid(7);
        g.makeGuess();

    }

}
