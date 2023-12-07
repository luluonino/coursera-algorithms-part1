public class HelloGoodbye {
    /*
     * HelloGoodbye.java takes two names as command-line arguments and prints
     * hello and goodbye messages as shown below (with the names for the hello
     * message in the same order as the command-line arguments and with the
     * names for the goodbye message in reverse order).
     */
    public static void main(final String[] args) {
        if (args.length == 2) {
            System.out.println("Hello " + args[0] + " and " + args[1] + ".");
            System.out.println("Goodbye " + args[1] + " and " + args[0] + ".");
        }
    }
}
