package com.example.Atc;

import java.util.*;
public class ATC {
    Scanner sc = new Scanner(System.in);
    private HashMap<String, Flight> Flightlist ;
    private List<Platform> Platformlist;
    ATC() {
        Flightlist = new HashMap<>();
        Platformlist = new ArrayList<>();
        System.out.println("Welcome to ATC Controller ADMIN Panel");
        assign();
        Collections.sort(Platformlist);
        mainscreen();

    }

    void assign() {
        System.out.println("Please Fill the Flight/Platform Details:");
        System.out.println("1.Enter 1 for Entering Flight Details");
        System.out.println("2.Enter 2 for Entering Platform Details");
        System.out.println("3.Enter 3 for Finish");
        System.out.println();
        int choice;
        String name_id;
        double weight, time;
        boolean contine = true;

        while(true) {

            System.out.println("Enter the choice:");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter Flight Name, Weight, Time of Flight");
                    try {
                        name_id = sc.nextLine().toLowerCase();
                        weight = sc.nextDouble();
                        time = sc.nextDouble();
                        Flightlist.put(name_id, new Flight(name_id, weight, time));
                    } catch (InputMismatchException e) {
                        System.out.println("Input Exception Occured:"+ e.getMessage());
                        sc.next();
                    }

                    break;

                case 2:
                    System.out.println("Enter the Platform id and time");
                    try {
                        name_id = sc.nextLine().toLowerCase();
                        time = sc.nextDouble();
                        Platformlist.add(new Platform(name_id, time));
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Input Exception Occured:");
                        sc.next();
                }
                    break;

                case 3:
                    return;

                default:
                    break;

            }
        }
    }

    void mainscreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        int choice;
        while (true) {
            System.out.println("Hello,Welcome to automated ATC!!!");
            System.out.println("Press any one of the option:");
            System.out.println("1.Takeoff");
            System.out.println("2.Landing");
            System.out.println("3.Emergency Landing");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    try {
                        ArrayList arr = getInput();
                        System.out.println(arr);
                    } catch(InputMismatchException ex) {
                        System.out.println("Mismatch in Input");
                        sc.next();
                    } catch(NotAvailable ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 4:
                    return ;


            }
        }
    }

    ArrayList getInput() throws NotAvailable {

        System.out.println("Enter the name and weight of the flight...");
        String name = sc.nextLine().toLowerCase();
        double weight = sc.nextDouble();
        if(!Flightlist.containsKey(name)) {
            throw new NotAvailable("Flight is not availble in data");
        }
        ArrayList temp = new ArrayList();
        temp.add(name);
        temp.add(weight);
        return temp;

    }

    class NotAvailable extends Exception {

            NotAvailable(String s) {
                super(s);
            }
    }

    public static void main(String args[]) {

        ATC start = new ATC();

    }
}
