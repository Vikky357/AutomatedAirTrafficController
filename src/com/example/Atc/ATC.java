package com.example.Atc;

import java.io.IOException;
import java.util.*;
public class ATC  {
    Scanner sc = new Scanner(System.in);
     HashMap<String, Flight> Flightlist ;
     List<Platform> Platformlist;
    ATC() {
        Flightlist = new HashMap<>();
        Platformlist = new ArrayList<>();
        System.out.println("Welcome to ATC Controller ADMIN Panel");
        dynamicassign();
        //assign();
        Collections.sort(Platformlist);
        for(Platform platforms:Platformlist) {
            System.out.println(platforms);
        }
        mainscreen();

    }
    void dynamicassign() {
        Flightlist.put("atr", new Flight("atr", 12.00,30.00));
        Flightlist.put("airbus", new Flight("airbus", 20.00,40.00));
        Flightlist.put("boeing", new Flight("boeing", 40.00,50.00));
        Flightlist.put("cargo", new Flight("cargo", 100.00,60.00));
        Platformlist.add(new Platform("r1",40.00));
        Platformlist.add(new Platform("r2",60.00));
        Platformlist.add(new Platform("r3",80.00));
        Platformlist.add(new Platform("r4",90.00));

    }
    void assign() {
        System.out.println("Please Fill the Flight/Platform Details:");
        System.out.println("1.Enter 1 f     or Entering Flight Details");
        System.out.println("2.Enter 2 for Entering Platform Details");
        System.out.println("3.Enter 3 for Finish");
        System.out.println();
        int choice;
        String name_id;
        double weight, time;

        while(true) {

            System.out.println("Enter the choice:");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println("Input Exception Occured..");
                sc.next();
                continue;
            }
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

        int choice;
        while (true) {
            System.out.println("Hello,Welcome to automated ATC!!!");
            System.out.println("Press any one of the option:");
            System.out.println("1.Takeoff");
            System.out.println("2.Landing");
            System.out.println("3.Emergency Landing");
            System.out.println("4.Exit");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch(InputMismatchException e) {
                System.out.println("Input Error Occured");
                sc.next();
                continue;
            }
            switch (choice) {
                case 1: case 2: case 3:
                    Flightassinged(choice);
                    break;
                case 4:
                    if(Thread.activeCount() > 2)
                        System.out.println("Please wait we will close once the threads finished running...");
                    return ;

            }
        }
    }

    void Flightassinged(int choice) {
        try {
            ArrayList arr = getInput();
            double actualTime = calculatepercent((double)arr.get(1), Flightlist.get((String)arr.get(0)) ) + 10;
            if(choice != 3)
                choice = 0;
            else
                choice = 1;
            TakeoffLanding objref = new TakeoffLanding(actualTime,(String) arr.get(0),(short)choice);

        } catch(InputMismatchException ex) {
            System.out.println("Mismatch in Input");
            sc.next();
        } catch(NotAvailable ex) {
            System.out.println(ex.getMessage());
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

    double calculatepercent(double weight, Flight obj) {
        double percentage = (weight/obj.getFWeight()) * 100;
        double time = obj.getFTime();
        if(percentage > 75.00d) {
            return time;
        }
        else if (percentage < 50.00d) {
            return time - ((20/100.00d)* obj.getFTime());
        }

        return time - ((10/100.00d)* obj.getFTime());


    }

    synchronized boolean isTakeoff(double time, String FName ) {

        for(Platform platforms : Platformlist) {
            if(platforms.getPTime() >= time && platforms.getisFree()) {

                platforms.setisFree(false);
                System.out.println("Platform id:"+ platforms.getPid()+ " "+ "is assigned for "+ time+ "seconds to"+ FName);
                platforms.setInputFlighttime(time);
                Thread t = new Thread(platforms, "Assigned Thread");
                t.start();
                return true;
            }
        }
        return false;
    }



    static class NotAvailable extends Exception {

            NotAvailable(String s) {
                super(s);
            }
    }


    class TakeoffLanding {
            String name;
            double time;
            short option;
            TakeoffLanding(double time, String name,Short option) {
                this.name = name;
                this.time = time;
                this.option = option;
                Timer t = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        while(!isTakeoff(time, name)) {
                            continue;
                        }
                        t.cancel();
                    }
                };
                if(option != 1)
                    t.scheduleAtFixedRate(task,0,3000);
                else
                    t.scheduleAtFixedRate(task,0,1000);
            }
        }




    public static void main(String[] args) {

        new ATC();

    }
}
