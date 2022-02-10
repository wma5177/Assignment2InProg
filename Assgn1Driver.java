import model.*;

import java.util.Properties;
import java.util.Scanner;

public class Assgn1Driver {
    public static void main(String[] args) {
        boolean flag=false;
        Scanner myObj = new Scanner(System.in);

        System.out.println("What action would you like to take? ");
        while (!flag){
            displayMenu();
            String selection = myObj.nextLine();
            if(selection.equals("1")){
                System.out.println("Please enter Books Title: ");
                String title = myObj.nextLine();
                System.out.println("Please enter Books Author: ");
                String author = myObj.nextLine();
                System.out.println("Please enter Books publication year: ");
                String pubYear= myObj.nextLine();

                String status= "Active";
                //Setting properties
                Properties p = new Properties();
                p.setProperty("bookTitle", title);
                p.setProperty("author", author);
                p.setProperty("pubYear", pubYear);
                p.setProperty("status", status);
                //Creating book object and saving it to DB
                Book b = new Book(p);
                b.save();

            }else if(selection.equals("2")){
                System.out.println("Please enter Patrons Name: ");
                String name = myObj.nextLine();
                System.out.println("Please enter Patrons Street address: ");
                String address = myObj.nextLine();
                System.out.println("Please enter Patrons city");
                String city= myObj.nextLine();
                System.out.println("Please enter Patrons state code (two chars): ");
                String stateCode = myObj.nextLine();
                System.out.println("Please enter Patrons Zip: ");
                String zip = myObj.nextLine();
                System.out.println("Please enter Patrons Email: ");
                String email = myObj.nextLine();
                System.out.println("Please enter date of birth (yyyy-mm-dd)");
                String dateOfBirth= myObj.nextLine();

                String status = "Active";

                Properties p = new Properties();
                p.setProperty("name", name);
                p.setProperty("address", address);
                p.setProperty("city", city);
                p.setProperty("stateCode", stateCode);
                p.setProperty("zip", zip);
                p.setProperty("email", email);
                p.setProperty("dateOfBirth", dateOfBirth);
                p.setProperty("status", status);
                //Creating patron object and saving it to DB
                try {
                    Patron b = new Patron(p);
                    b.save();
                }catch(Exception E){
                    System.out.println(E);
                }


            }else if(selection.equals("3")){

                System.out.println("Please enter part of a book title: ");
                String subTitle = myObj.nextLine();
                try {
                    BookCollection bkCol = new BookCollection();
                    bkCol.findBooksWithTitleLike(subTitle);

                    bkCol.display();
                }catch(Exception E){
                    System.out.println(E);
                }
            }else if(selection.equals("4")){

                System.out.println("Please enter a year and we will return a list of books published before that year: ");
                String year = myObj.nextLine();

                BookCollection bkCol = new BookCollection();

                bkCol.findBooksOlderThanDate(year);

                bkCol.display();

            }else if(selection.equals("5")){

                System.out.println("Please enter a date and we will return a list of patrons born before that year: ");
                String date = myObj.nextLine();

                PatronCollection pCol = new PatronCollection();
                try {
                    pCol.findPatronsYoungerThan(date);
                }catch(Exception E){
                    System.out.println(E);
                }
                pCol.display();

            }else if(selection.equals("6")){

                System.out.println("Please enter a zip and we will return a list of patrons that live in that zip: ");
                String zip = myObj.nextLine();

                PatronCollection pCol = new PatronCollection();

                try {
                    pCol.findPatronsAtZipCode(zip);
                }catch (Exception E){
                    System.out.println(E);
                }

                pCol.display();

            }else if(selection.equals("7")){
                System.out.println("Goodbye");
                flag=true;
            }else{
                System.out.println("Invalid selection, Try again: ");
            }
        }






    }

    public static void displayMenu(){
        System.out.println("1: Insert new book");
        System.out.println("2: Insert new patron");
        System.out.println("3: Search for book by part of title");
        System.out.println("4: Search for books by year");
        System.out.println("5: Search for patron by younger than date");
        System.out.println("6: Search for patron by zip");
        System.out.println("7: Exit");
    }

}
