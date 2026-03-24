package PenDesign;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);

        System.out.print("Enter Pen Type (ball / gel): ");
        String penType = scnr.nextLine();

        System.out.print("Enter Mechanism (click / cap): ");
        String mechanismType = scnr.nextLine();

        System.out.print("Enter ink color: ");
        String color = scnr.nextLine();

        System.out.print("Enter ink level: ");
        int ink = scnr.nextInt();
        scnr.nextLine();

        System.out.print("Enter text to write: ");
        String text = scnr.nextLine();

        Refill refill = new Refill(color, ink);

        Pen pen = PenFactory.createPen(penType, mechanismType, refill);

        pen.start();
        pen.write(text);
        pen.close();

        scnr.close();
    }
}