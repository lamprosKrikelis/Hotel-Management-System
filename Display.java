package letsgo;

import java.io.*;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Display {

    public Rooms r = new Rooms();
    public String city;
    public String date_in;
    public String date_out;
    public long date_between;
    public int total;
    public ArrayList<String> arrFile;

// I must to work into local dir  without DBMS .
    public ArrayList<String> getFile(PrintWriter dout, BufferedReader in) throws IOException {
        File file = new File("C:\\Users\\ritod\\Desktop\\letsgo\\hotel.txt");
        Scanner reader = new Scanner(file);

        arrFile = new ArrayList<>();

        dout.println("Choose between Rome and Skg ");
        city = in.readLine().toUpperCase();
        System.out.println("Requested City: " + city);
        boolean flag = false;
        while (reader.hasNextLine()) {
            String line = reader.nextLine();

            if (city.equals(line)) {
                for (int i = 0; i < 6; i++) {
                    arrFile.add(reader.nextLine());
                }
                flag = true;
            }
        }
        reader.close();
        if (flag) {
            arrFile.add(0, "The hotels for " + city + " are: ");
        } else {
            arrFile.add("Your city is not in our data");
        }
        return arrFile;
    }

    public void Reservetion(PrintWriter dout, BufferedReader in) throws IOException {
        ArrayList<Period> AllDates = new ArrayList<>();
        DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        dout.println("Enter date in (dd/MM/yyyy): ");
        date_in = in.readLine().toUpperCase();
        dout.println("Enter date out (dd/MM/yyyy): ");
        date_out = in.readLine();

        LocalDate begin = LocalDate.parse(date_in, dayformatter);
        LocalDate end = LocalDate.parse(date_out, dayformatter);
        AllDates.add(new Period(begin, end));
        date_between = ChronoUnit.DAYS.between(begin, end);

        // here Im trying to check if the in_outDate  exist into the ArrayList or not but I dont know how ...
        if (!AllDates.contains(begin) && !AllDates.contains(end)) {
            System.out.println("Dates between: " + date_between);
            if (date_between > 0) {
                String d = begin.getMonth().toString();
                if (d.equals("JANUARY")) {
                    r.setExtraM(10);
                    dout.println("Message: Due to the fact that is month has much popullarity it will be an extra fee "
                            + "of " + r.getExtraM());
                    Room(in, dout);
                    total = total + r.getExtraM();
                    dout.println("Success: your total fee is: " + total);
                } else if (d.equals("AUGUST") || d.equals("JULY")) {
                    r.setExtraM(20);
                    dout.println("Message: Due to the fact that is month has much popularity it will be an extra fee "
                            + "of " + r.getExtraM());
                    Room(in, dout);
                    total = total + r.getExtraM();
                    dout.println("Success: your total fee is: " + total);

                } else {
                    Room(in, dout);
                    dout.println("Success: your total fee is: " + total);
                }
            } else if (date_between == 0) {
                date_between = 1;
                Room(in, dout);
                dout.println("Success: your total fee is: " + total);
            } else {
                dout.println("Error: Pls type a correct month :/ ");
                Reservetion(dout, in);
            }
        } else {
            dout.println("Error: Sorry someone else reserve pls try  a new check in and out ");
            Reservetion(dout, in);
        }

    }

    public int Room(BufferedReader in, PrintWriter dout) throws IOException {

        String[] rooms = new String[4];
        rooms[0] = "For one bed Room press 1";
        rooms[1] = "For two bed Room press 2";
        rooms[2] = "For three bed Room press 3";
        rooms[3] = "For four bed Room press 4";

        dout.println(Arrays.deepToString(rooms));
        int chroom = Integer.parseInt(in.readLine());
        if (date_between > 0) {
            if (chroom == 1) {
                r.setPrice(30);
                total = (int) (r.getPrice() * date_between);

            } else if (chroom == 2) {
                r.setPrice(40);
                total = (int) (r.getPrice() * date_between);

            } else if (chroom == 3) {
                r.setPrice(50);
                total = (int) (r.getPrice() * date_between);

            } else if (chroom == 4) {
                r.setPrice(60);
                total = (int) (r.getPrice() * date_between);

            } else {
                dout.println("Pls type the correct number of bedroom you want to reserve: ");
                return Room(in, dout);
            }
        }
        dout.println("Do you want to reserve a bed room "
                + chroom + " with a view for only 15 dollars  ? if type `yes` or `no` ");
        String chview = in.readLine().toLowerCase();
        int view = 15;
        if (chview.equals("yes")) {
            total = total + view;
            return total;
        } else if (chview.equals("no")) {
            return total;
        } else {
            dout.println("pls type yes or no ");
            return Room(in, dout);
        }
    }

}
