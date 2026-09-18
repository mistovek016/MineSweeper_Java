public class ColoredTextExample {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_MAGENTA = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // Background colors
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public static void main(String[] args) {
        System.out.println(ANSI_GREEN + "This text is green." + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "This text is yellow." + ANSI_RESET);
        System.out.println(ANSI_RED + "This text is red." + ANSI_RESET);

        // Example with background color
        System.out.println(ANSI_WHITE + ANSI_BLACK_BACKGROUND + "Blue text on a white background." + ANSI_RESET);
    }
}
// ... and so on for other background colors