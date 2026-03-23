package PenDesign;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);

        System.out.println("Enter Pen Type (ball / gel): ");
        String type = scnr.nextLine().toLowerCase();

        System.out.print("Enter ink color: ");
        String color = scnr.nextLine();

        System.out.println("Enter the text you want to write: ");
        String text = scnr.next();

        System.out.print("Enter ink level: ");
        int ink = scnr.nextInt();

        Refill refill = new Refill(color, ink);

        Pen pen;

        if (type.equals("ball")) {
            pen = new BallPen(refill);
        } else {
            pen = new GelPen(refill);
        }

        pen.start();
        pen.write(text);
        pen.close();

        scnr.close();
    }
}