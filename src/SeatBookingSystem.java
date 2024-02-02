import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class SeatBookingSystem {
    static LocalDateTime localDateTime = LocalDateTime.now();
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    static String time = localDateTime.format(dateTimeFormatter);
    private static String[][] morningShift;  //? Declare seats as a class variable
    private static String[][] afternoonShift;  //? Declare seats as a class variable
    private static String[][] eveningShift;  //? Declare seats as a class variable
    private static String[] bookingHistory = new String[100];  //? Declare seats as a class variable
    private static String studentIdInput;
    private static int studentId = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        System.out.print("Enter the number of rows: ");

        String rows = scanner.nextLine();
        //only allow to input number
        while (!rows.matches("[0-9]+") || Integer.parseInt(rows) > 26) {
            System.out.println("Invalid input. Please try again.");
            System.out.print("Enter the number of rows: ");
            rows = scanner.nextLine();
        }
//A-1::AV A-1
        System.out.print("Enter the number of columns: ");
        String columns = scanner.nextLine();
        while (!columns.matches("[0-9]+") || Integer.parseInt(columns) > 26) {
            System.out.println("Invalid input. Please try again.");
            System.out.print("Enter the number of columns: ");
            columns = scanner.nextLine();
        }
        morningShift = createSeatsArray(Integer.parseInt(rows), Integer.parseInt(columns));  //? Initialize the class variable
        afternoonShift = createSeatsArray(Integer.parseInt(rows), Integer.parseInt(columns));  //? Initialize the class variable
        eveningShift = createSeatsArray(Integer.parseInt(rows), Integer.parseInt(columns));  //? Initialize the class variable
        String opt;
        welcome();
        do {
            option();
            System.out.print("Your Option:");
            opt = scanner.nextLine().toUpperCase();
            while (!opt.matches("[A-F]+") || opt.length() > 1) {
                System.out.println("Invalid input. Please try again");
                System.out.print("Your Option:");
                opt = scanner.nextLine().toUpperCase();
            }
            switch (opt) {
                case "A" -> {
                    opt = chooseBookingOption();
                    switch (opt) {
                        case "A" -> {
                            showHallA();
                        }
                        case "B" -> {
                            showHallB();
                        }
                        default -> {
                            showHallC();
                        }
                    }

                }
                case "B" -> {
                    checkHistory();
                }

                case "C" -> {
                    showTime();
                    //sleep 1 second
                }
                case "D" -> {
                    showHistory();
                }
                case "E" -> {
                    rebootSeat();
                }
                default -> {
                    goodbye();
                }
            }
        } while (!opt.equals("F"));
    }

    private static String chooseBookingOption() throws InterruptedException {
        String opt;
        System.out.println("Start Booking Seat");
        showTime();
        System.out.print("Your Option:");
        opt = scanner.nextLine().toUpperCase();
        String seatsToBookInput;
        String[] seatsToBook;
        //validate input opt only allow A,B,C and only allow to input only one character
        while (!opt.matches("[A-C]+") || opt.length() > 1) {
            System.out.println("Invalid input. Please try again");
            opt = scanner.nextLine().toUpperCase();
        }
        return opt;
    }

    private static void showHallC() throws InterruptedException {
        String[] seatsToBook;
        System.out.println("""
                +=================================================================================================================+
                |                                               ----- HALL C -----                                                |
                +=================================================================================================================+
                """);
        displaySeats(eveningShift);
        System.out.print("Enter seats to book (separated by commas): ");

        seatsToBook = getBookedSeat();
        Thread.sleep(1000);
        if (bookSeats("E", seatsToBook)) {
            System.out.println("Seats booked successfully!");
            displaySeats(eveningShift);
        }
    }

    private static void showHallB() throws InterruptedException {
        String[] seatsToBook;
        System.out.println("""
                +=================================================================================================================+
                |                                               ----- HALL B -----                                                |
                +=================================================================================================================+
                """);
        displaySeats(afternoonShift);
        System.out.print("Enter seats to book (separated by commas): ");
        seatsToBook = getBookedSeat();
        Thread.sleep(1000);
        if (bookSeats("A", seatsToBook)) {
            System.out.println("Seats booked successfully!");
            displaySeats(afternoonShift);
        }
    }

    private static void showHallA() throws InterruptedException {
        String[] seatsToBook;
        System.out.println("""
                +=================================================================================================================+
                |                                               ----- HALL A -----                                                |
                +=================================================================================================================+
                """);
        displaySeats(morningShift);
        bookingInfo();
        System.out.print("Enter seats to book (separated by commas): ");
        seatsToBook = getBookedSeat();
        Thread.sleep(1000);
        if (bookSeats("M", seatsToBook)) {
            System.out.println("Seats booked successfully!");
            //store student id with seat
            displaySeats(morningShift);
        }
    }

    private static String[] getBookedSeat() {
        String[] seatsToBook;
        String seatsToBookInput;
        seatsToBookInput = scanner.nextLine().toUpperCase();
        while (!seatsToBookInput.matches("([A-Z]-[0-9]+,?)+")) {
            System.out.println("Invalid input. Please try again");
            System.out.print("Enter seats to book (separated by commas): ");
            seatsToBookInput = scanner.nextLine().toUpperCase();
        }
        seatsToBook = seatsToBookInput.split(",");
        return seatsToBook;
    }

    private static void showHistory() throws InterruptedException {
        System.out.println("Booking History");
        int i = 0;
        if (bookingHistory[0] == null) {
            System.out.println("No booking history yet!");
        }
        for (String history : bookingHistory) {
            if (history != null) {
                System.out.println("+====================================================+");
//                System.out.println(history);
                System.out.println("#NO:" + ++i);
                String seat = history.substring(history.indexOf("["), history.indexOf("]") + 1);
                String hall = history.substring(history.indexOf("H"), history.indexOf("l") + 4);
                //select student id from string that have 2 space between one char and another char
                String studentId = history.substring(history.indexOf(" ", history.indexOf("l") + 4), history.lastIndexOf(" ") - 10);
//                String studentId = history.substring(history.indexOf(" ", history.indexOf("l") + 4), history.lastIndexOf(" ", history.indexOf(" ", )));
                String time = history.substring(history.lastIndexOf(" ") - 11);
                System.out.println("#SEATS:" + seat);
                System.out.println("#HALL\t" + "#STUDENT ID\t" + "#CREATED_AT");
                System.out.println(hall + "\t" + studentId + "\t\t" + time);
                System.out.println("+====================================================+");
            }
        }
        Thread.sleep(1000);
    }

    private static void goodbye() {
        System.out.println("""
                +=================================================================================================================+
                |                                       ----- Goodbye See you next time -----                                     |
                +=================================================================================================================+
                """);

        System.exit(0);
    }

    private static void rebootSeat() throws InterruptedException {
        System.out.println("Rebooting Hall");
        reboot();
        rebootHistory();
        Thread.sleep(1000);
        System.out.println("""
                +=================================================================================================================+
                |                                           ----- Reboot Successful -----                                         |
                +=================================================================================================================+
                """);
    }

    private static void rebootHistory() {
        Arrays.fill(bookingHistory, null);
    }

    private static void checkHistory() throws InterruptedException {
        System.out.println("This is for display hall checking");
        System.out.println("""
                +=================================================================================================================+
                |                                               ----- Morning Hall -----                                          |
                +=================================================================================================================+
                """);
        displaySeats(morningShift);
        System.out.println("""
                +=================================================================================================================+
                |                                               ----- Afternoon Hall -----                                        |
                +=================================================================================================================+
                """);

        displaySeats(afternoonShift);
        System.out.println("""
                +=================================================================================================================+
                |                                               ----- Evening Hall -----                                          |
                +=================================================================================================================+
                """);
        displaySeats(eveningShift);
        Thread.sleep(1000);
    }


    private static void bookingInfo() {
        System.out.println("""
                +=================================================================================================================+
                |                                        ----- BOOKING INTRODUCTION -----                                         |
                |                                                   Single: C-1                                                   |
                |                                    Multiple Choice (Separate by comma):C-1,C-2                                  |
                +=================================================================================================================+
                """);
    }

    private static void welcome() {
        System.out.println("""
                +=================================================================================================================+
                |██████╗  ██████╗  ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗     ███████╗██╗   ██╗███████╗████████╗███████╗███╗   ███╗ |
                |██╔══██╗██╔═══██╗██╔═══██╗██║ ██╔╝██║████╗  ██║██╔════╝     ██╔════╝ ╚██╗ ██╔╝██╔════╝╚══██╔══╝ ██╔════╝████╗ ████║ |
                |██████╔╝██║   ██║██║   ██║█████╔╝ ██║██╔██╗ ██║██║  ███╗    ███████╗ ╚████╔╝ ███████╗  ██║    █████╗  ██╔████╔██║ |
                |██╔══██╗██║   ██║██║   ██║██╔═██╗ ██║██║╚██╗██║██║   ██║    ╚════██║  ╚██╔╝  ╚════██║   ██║    ██╔══╝  ██║╚██╔╝██║ |
                |██████╔╝╚██████╔╝╚██████╔╝██║  ██╗██║██║ ╚████║╚██████╔╝    ███████║   ██║   ███████║  ██║    ███████╗██║ ╚═╝ ██║ |
                |╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝      ╚══════╝   ╚═╝    ╚══════╝   ╚═╝    ╚══════╝╚═╝     ╚═╝ |
                +================================================================================================================+
                """);
    }

    private static void showTime() throws InterruptedException {
        System.out.println("""
                +=================================================================================================================+
                |                                              ----- SHOW TIME -----                                              |
                |                                            A. Morning (10:00 - 12:30)                                           |                                                |
                |                                            B. Afternoon (3:00 - 5:30)                                           |                                                |
                |                                            C. Evening (7:00 - 8:30)                                             |                                                |
                +=================================================================================================================+
                """);
        Thread.sleep(1000);
    }


    public static void option() {
        System.out.println("""
                +=================================================================================================================+
                |                                         ----- Choose One Option -----                                           |
                |                                                 A. Booking Seat                                                 |
                |                                                 B. Hall                                                         |
                |                                                 C. Showtime                                                     |
                |                                                 D. Booking History                                              |
                |                                                 E. Rebooting Hall                                               |
                |                                                 F. Exit                                                         |
                +=================================================================================================================+
                """);
    }

    private static String[][] createSeatsArray(int rows, int columns) {
        String[][] seats = new String[rows][columns];

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                char rowChar = (char) ('A' + i);
                int seatNumber = j + 1;
                seats[i][j] = String.format("|%c-%d::AV|", rowChar, seatNumber);
            }
        }

        return seats;
    }

    private static void displaySeats(String[][] seats) {
        for (String[] row : seats) {
            for (String seat : row) {
                System.out.print(seat + "\t");
            }
            System.out.println();
        }
    }

    private static boolean bookSeats(String shift, String[] seatCodes) {
        boolean confirmationAsked = false;
        for (String seatCode : seatCodes) {
            String[] parts = seatCode.split("-");
            if (parts.length == 2) {
                char row = parts[0].charAt(0);
                int col = Integer.parseInt(parts[1]) - 1;
                if (isValidSeat(row, col)) {
                    if (shift.equals("M")) {
                        if (morningShift[row - 'A'][col].contains("BO")) {
                            System.out.println("Seat " + seatCode + " is not available.");
                            return false;
                        }
                        //input student id

                        //if already asked once, no need to ask again
                        if (!confirmationAsked) {
                            System.out.print("Enter Student ID: ");
                            studentIdInput = scanner.nextLine();
                            while (!studentIdInput.matches("\\d*")) {
                                System.out.println("Invalid input.");
                                System.out.print("Enter Student ID: ");
                                studentIdInput = scanner.nextLine();
                            }
                            System.out.println("Are you sure you want to book this seat? (Y/N)");
                            String confirm = scanner.nextLine().toUpperCase();
                            while (!confirm.matches("^[YNyn]$") || confirm.length() > 1) {
                                System.out.println("Invalid input. Please try again");
                                System.out.println("Are you sure you want to book this seat? (Y/N)");
                                confirm = scanner.nextLine().toUpperCase();
                            }
                            confirmationAsked = true;
                            bookingHistory[studentId] = Arrays.toString(seatCodes) + " Hall A " + studentIdInput + " " + time;

                            ++studentId;

                            if (!confirm.equals("Y")) {
                                System.out.println("Seat booking cancelled.");
                                return false;
                            }

                        }
                        morningShift[row - 'A'][col] = morningShift[row - 'A'][col].replace("AV", "BO");


                    } else if (shift.equals("A")) {
                        if (afternoonShift[row - 'A'][col].contains("BO")) {
                            System.out.println("Seat " + seatCode + " is not available.");
                            return false;
                        }
                        if (!confirmationAsked) {
                            System.out.print("Enter Student ID: ");
                            studentIdInput = scanner.nextLine();
                            while (!studentIdInput.matches("\\d*")) {
                                System.out.println("Invalid input.");
                                System.out.print("Enter Student ID: ");
                                studentIdInput = scanner.nextLine();
                            }
                            System.out.println("Are you sure you want to book this seat? (Y/N)");
                            String confirm = scanner.nextLine().toUpperCase();
                            while (!confirm.matches("^[YNyn]$") || confirm.length() > 1) {
                                System.out.println("Invalid input. Please try again");
                                System.out.println("Are you sure you want to book this seat? (Y/N)");
                                confirm = scanner.nextLine().toUpperCase();
                            }
                            confirmationAsked = true;
                            bookingHistory[studentId] = Arrays.toString(seatCodes) + " Hall B " + studentIdInput + " " + time;

                            ++studentId;

                            if (!confirm.equals("Y")) {
                                System.out.println("Seat booking cancelled.");
                                return false;
                            }

                        }
                        afternoonShift[row - 'A'][col] = afternoonShift[row - 'A'][col].replace("AV", "BO");
                    } else {
                        if (eveningShift[row - 'A'][col].contains("BO")) {
                            System.out.println("Seat " + seatCode + " is not available.");
                            return false;
                        }
                        //input student id
                        if (!confirmationAsked) {
                            System.out.print("Enter Student ID: ");
                            studentIdInput = scanner.nextLine();
                            while (!studentIdInput.matches("\\d*")) {
                                System.out.println("Invalid input.");
                                System.out.print("Enter Student ID: ");
                                studentIdInput = scanner.nextLine();
                            }
                            System.out.println("Are you sure you want to book this seat? (Y/N)");
                            String confirm = scanner.nextLine().toUpperCase();
                            while (!confirm.matches("^[YNyn]$") || confirm.length() > 1) {
                                System.out.println("Invalid input. Please try again");
                                System.out.println("Are you sure you want to book this seat? (Y/N)");
                                confirm = scanner.nextLine().toUpperCase();
                            }
                            confirmationAsked = true;
                            bookingHistory[studentId] = Arrays.toString(seatCodes) + " Hall C " + studentIdInput + " " + time;

                            ++studentId;

                            if (!confirm.equals("Y")) {
                                System.out.println("Seat booking cancelled.");
                                return false;
                            }

                        }
                        eveningShift[row - 'A'][col] = eveningShift[row - 'A'][col].replace("AV", "BO");
                    }
                } else {
                    System.out.println("Seat " + seatCode + " is not available or invalid.");
                    return false;
                }
            } else {
                System.out.println("Invalid seat code: " + seatCode);
                return false;
            }
        }
        return true;
    }

    private static boolean isValidSeat(char row, int col) {
        return row >= 'A' && row < 'A' + morningShift.length && col >= 0 && col < morningShift[0].length;
    }

    private static void reboot() {
        for (int i = 0; i < morningShift.length; i++) {
            for (int j = 0; j < morningShift[i].length; j++) {
                morningShift[i][j] = morningShift[i][j].replace("BO", "AV");
                afternoonShift[i][j] = afternoonShift[i][j].replace("BO", "AV");
                eveningShift[i][j] = eveningShift[i][j].replace("BO", "AV");
            }
        }
    }
}