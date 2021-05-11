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
            ArrayList input = null;
            switch (choice) {
                case 1: case 2:
                    input = Flightassigned();
                    if(input != null) {
                        Assigning obj = new Assigning((double) input.get(1), (String) input.get(0));
                        obj.TakeoffLanding();
                    }
                    break;
                case 3:
                    input = Flightassigned();
                    if(input != null) {
                        Assigning obj = new Assigning((double) input.get(1), (String) input.get(0));
                        Thread t = new Thread(obj);
                        t.setPriority(10);
                        t.start();
                    }
                    break;
                case 4:
                    if(Thread.activeCount() > 2)
                        System.out.println("Please wait we will close once the threads finished running...");
                    return ;

            }
        }
    }

    ArrayList Flightassigned() {

         try {
             ArrayList arr = null;
             arr = getInput();
             double actualTime = calculatepercent((double)arr.get(1), Flightlist.get((String)arr.get(0)) ) + 10;
             arr.set(1,actualTime);
             return arr;
         } catch(InputMismatchException ex) {
             System.out.println("Mismatch in Input");
             sc.next();
             return null;
         } catch(NotAvailable ex) {
             System.out.println(ex.getMessage());
             return null;
         }

    }



    ArrayList getInput() throws NotAvailable {

        System.out.println("Enter the name and weight of the flight...");
        String name = sc.nextLine().toLowerCase();
        double weight = sc.nextDouble();
        if(!Flightlist.containsKey(name)) {
            throw new NotAvailable("Flight is not availble in data");
        }
        if(weight > Flightlist.get(name).getFWeight())
            throw  new NotAvailable("Weight is more than the alloted weight..");
        ArrayList temp = new ArrayList();
        temp.add(name);
        temp.add(weight);
        return temp;

    }

    double calculatepercent(double weight, Flight obj) {
        double percentage = (weight/obj.getFWeight()) * 100.00d;
        double time = obj.getFTime();
        if(percentage > 75.00d) {
            return time;
        }
        else if (percentage < 50.00d) {
            return time - ((20/100.00d)* time);
        }

        return time - ((10/100.00d)* time);


    }





    static class NotAvailable extends Exception {

            NotAvailable(String s) {
                super(s);
            }
    }



        class Assigning implements Runnable {

            double time;
            String name;
            Assigning(double time, String name) {
                this.time = time;
                this.name = name;
            }

            void TakeoffLanding() {
                Timer t = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if(isTakeoff(time, name))
                            t.cancel();

                    }
                };
                t.scheduleAtFixedRate(task,0,3000);

            }

            @Override
            public void run() {
                while(!isTakeoff(time, name)) {
                    continue;
                }
            }
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






    public static void main(String[] args) {

        new ATC();

    }
}
