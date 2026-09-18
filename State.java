public class State {

    private boolean hidden, mine, flagged;
    private int neighbours;

    State(boolean hidden, boolean mine, boolean flagged, int neighbours) {
        this.hidden = hidden;
        this.mine = mine;
        this.flagged = flagged;
        this.neighbours = neighbours;
    }

    public boolean isHidden() { return hidden; }
    public boolean isMine() { return mine; }
    public boolean isFlagged() { return flagged; }
    public int getNeighbours() { return neighbours; }

    public void setHidden(boolean hidden) { this.hidden = hidden; }
    public void setMine(boolean mine) { this.mine = mine; }
    public void setFlagged(boolean flagged) { this.flagged = flagged; }
    public void setNeighbours(int neighbours) { this.neighbours = neighbours; }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_MAGENTA = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public String neighbours_ansi;

    public String getSymbol () {
        neighbours_ansi = switch (neighbours) {
            case 1 -> ANSI_GREEN;
            case 2 -> ANSI_YELLOW;
            case 3 -> ANSI_BLUE;
            case 4 -> ANSI_MAGENTA;
            case 5 -> ANSI_CYAN;
            default -> ANSI_WHITE;
        };
        String sym;
        if (hidden) {
            if (flagged) sym = "[" + ANSI_RED + "f" + ANSI_RESET + "]";
            else sym = "[ ]";
        }
        else if (mine) sym = "[" + ANSI_RED_BACKGROUND + "m" + ANSI_RESET + "]";
        else sym = neighbours == 0 ? ANSI_WHITE_BACKGROUND + "   " + ANSI_RESET : "[" + neighbours_ansi + neighbours + ANSI_RESET + "]";
        return sym;
    }
}