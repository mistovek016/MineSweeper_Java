import java.util.*;

public class MsGrid {
    private final boolean[][] mineMap;
    private final State[][] mineShowMap;
    private final int n_mines, len;

    MsGrid(int len, int n_mines, int fr, int fc) {
        mineMap = new boolean[len][len];
        mineShowMap = new State[len][len];
        this.n_mines = n_mines;
        this.len = len;

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                mineMap[i][j] = false;
            }
        }

        makeMines(fr, fc);

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                mineShowMap[i][j] = new State(true, mineMap[i][j], false, -1);
            }
        }
        computeNeighbours();
        expose_zero_neighbours(fr, fc, 0, 0);
        removeDuplicates(bounds);
        removeDuplicates1(getNonZeroBounds(cleanBounds));
        showBounds();
        printMap();
    }

    private void computeNeighbours() {
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++)
                mineShowMap[i][j].setNeighbours(n_neighbours(i, j));
        }
    }

    private void makeMines(int exr, int exc) {
        double norm;
        int i = 0, mine, r, c;

        while (i < n_mines) {
            mine = (int) (Math.random() * (len * len));

            r = mine / len;
            c = mine % len;
            norm = Math.sqrt(Math.pow(exr - r, 2) + Math.pow(exc - c, 2));

            if (r == exr && c == exc)
                continue;
            if (mineMap[r][c])
                continue;
            if (norm <= 2.5 * 0.2 * len)
                continue;
            mineMap[r][c] = true;
            i++;
        }
    }

    public void printMine() {
        for (boolean[] b : mineMap) {
            for (boolean b1 : b)
                System.out.print((b1 ? "@" : ".") + "  ");
            System.out.println();
        }
    }

    private int n_neighbours(int r, int c) {
        int startR = r == 0 ? r : r - 1;
        int endR = r == len - 1 ? r : r + 1;
        int startC = c == 0 ? c : c - 1;
        int endC = c == len - 1 ? c : c + 1;
        int count = 0;
        for (int i = startR; i <= endR; i++) {
            for (int j = startC; j <= endC; j++) {
                if (mineMap[i][j])
                    count++;
            }
        }
        return count;
    }

    public void toggleFlag(int r, int c) {
        mineShowMap[r][c].setFlagged(!mineShowMap[r][c].isFlagged());
        printMap();
    }

    public void showCell(int r, int c) {
        if (mineShowMap[r][c].isMine()) {
            mineShowMap[r][c].setHidden(false);
            printMap();
            System.out.println("Game Over!");
            System.exit(0);
        }
        if (mineShowMap[r][c].isHidden()) {
            mineShowMap[r][c].setHidden(false);
            printMap();
        }
    }

    public void printMap() {
        int len = 1;
        System.out.print("    ");
        for (int i = 1; i <= mineShowMap[0].length; i++)
            System.out.print(" " + (i < 10 ? i : i - 10) + (i == 9 ? "+" : " "));
        System.out.println();
        for (State[] s : mineShowMap) {
            System.out.print(len++ + "  " + (len > 10 ? "" : " "));
            for (State s1 : s)
                System.out.print(s1.getSymbol());
            System.out.println();
        }
        System.out.println();
    }

    private final ArrayList<Cell> bounds = new ArrayList<>();

    private void expose_zero_neighbours(int r, int c, int v, int h) {
        // int count = iters;
        if (r < 0 || r >= len || c < 0 || c >= len)
            return;
        // double norm = Math.sqrt(Math.pow(fr - r, 2) + Math.pow(fc - c, 2));
        // if (norm > 4) return;
        if (mineShowMap[r][c].isHidden()) {
            if (mineShowMap[r][c].getNeighbours() != 0) {
                // bounds.add(new Cell(r-v, c-h));
                return;
            } else {
                mineShowMap[r][c].setHidden(false);
                bounds.add(new Cell(r, c));
            }
        } else
            return;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == -v && j == -h)
                    continue;
                // count++;
                // if (count > 25) return;
                expose_zero_neighbours(r + i, c + j, i, j);
            }
        }
    }

    private final Set<Cell> cleanBounds = new LinkedHashSet<>();

    private void removeDuplicates(ArrayList<Cell> maxBounds) {
        cleanBounds.clear();
        cleanBounds.addAll(maxBounds);
    }

    private ArrayList<Cell> getNonZeroBounds(Set<Cell> cleanBounds) {
        int row, col, row1, col1;
        ArrayList<Cell> nonZeroBounds = new ArrayList<>();
        for (Cell c : cleanBounds) {
            row = c.getX();
            col = c.getY();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    row1 = row + i;
                    col1 = col + j;
                    if (row1 < 0 || row1 >= len || col1 < 0 || col1 >= len)
                        continue;
                    if (i == 0 && j == 0)
                        continue;
                    nonZeroBounds.add(new Cell(row1, col1));
                }
            }
        }
        return nonZeroBounds;
    }

    private final Set<Cell> finalNonZeroBounds = new LinkedHashSet<>();

    private void removeDuplicates1(ArrayList<Cell> nonZeroBounds) {
        finalNonZeroBounds.clear();
        finalNonZeroBounds.addAll(nonZeroBounds);
    }

    private void showBounds() {
        for (Cell c : finalNonZeroBounds)
            mineShowMap[c.getX()][c.getY()].setHidden(false);
    }

    public void checkWin() {
        int notFoundSafeCount = 0;
        for (State[] s : mineShowMap) {
            for (State s1 : s)
                if (/* s1.isHidden() && s1.isMine() || */!s1.isHidden() && !s1.isMine())
                    notFoundSafeCount++;
        }
        if (notFoundSafeCount == (int) Math.pow(len, 2) - n_mines) {
            System.out.println("Congratulations! You won!");
            System.exit(0);
        }
    }
}