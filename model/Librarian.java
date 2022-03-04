

// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import exception.InvalidPrimaryKeyException;
import event.Event;
import userinterface.LibrarianView;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

/** Class containing Librarian for the Library Application */
//===============================================================
public class Librarian implements IModel, IView
{
    private Properties dependencies;
    private ModelRegistry myRegistry;

    private Hashtable<String, Scene> myViews;
    private Stage myStage;

    private String transactionErrorMessage = "";

    public Librarian()
    {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        myRegistry = new ModelRegistry("Librarian");
        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "Librarian",
                    "Could not instantiate Registry", Event.ERROR);
        }

        setDependencies();

        createAndShowLibrarianView();
    }

    /**
     * Method called from client to get the value of a particular field
     * held by the objects encapsulated by this object.
     *
     * @param	key	Name of database column (field) for which the client wants the value
     *
     * @return	Value associated with the field
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("TransactionError"))
        {
            return transactionErrorMessage;
        }

        return "";
    }

    /** */
    //-----------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();
        //dependencies.setProperty("InsertNewBook", "TransactionError");
        //dependencies.setProperty("InsertNewPatron", "TransactionError");
        //dependencies.setProperty("SearchBooks", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }



    //-----------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);
        if ((key.equals("InsertNewBook")) ||
                (key.equals("InsertNewPatron")) ||
                (key.equals("SearchBooks")) ||
                (key.equals("SearchPatrons"))){

            String transType = key;
            transType = transType.trim();
            doTransaction(transType);
        }
        else if (key.equals("CancelTransaction")){
            createAndShowLibrarianView();
    }
        else if (key.equals("Exit"))
        {
            System.exit(0);
        }


        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    public void doTransaction(String transactionType){
        try{
            Transaction trans = TransactionFactory.createTransaction(transactionType);

            trans.subscribe("CancelTransaction", this);
            trans.stateChangeRequest("DoYourJob", "");
        }
        catch (Exception e){
            transactionErrorMessage = "ERROR";
            new Event(Event.getLeafLevelClassName(this), "createTransaction", "Trans creation fail", Event.ERROR);
        }
    }

    public void createAndShowLibrarianView()
    {
        Scene currentScene = (Scene)myViews.get("LibrarianView");

        if (currentScene == null)
        {
            View newView = ViewFactory.createView("LibrarianView", this);
            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }

        myStage.setScene(currentScene);
        myStage.sizeToScene();

        WindowPosition.placeCenter(myStage);
    }


    public void createNewBook()
    {
        //Book book = new Book(this);

        //book.createAndShowBookView();
    }

    public void createNewPatron()
    {

    }

    public void searchBooks()
    {

    }

    public void transactionDone()
    {

    }

    public void subscribe(String key, IView subscriber)
    {
        myRegistry.subscribe(key, subscriber);
    }

    public void unSubscribe(String key, IView subscriber)
    {
        myRegistry.unSubscribe(key, subscriber);
    }
}

/*

package model;

import impresario.IModel;
import impresario.IView;

import java.util.Hashtable;

import exception.InvalidPrimaryKeyException;
import userinterface.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Librarian implements IModel {

    //GUI Components
    private Stage myStage;
    private Hashtable<String, Scene> myViews;


    //----------------------------------------------------------
    //Constructor
    //----------------------------------------------------------
    public Librarian() {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();


        createAndShowLibrarianView();
        //createAndShowBookView();	//Comment and uncomment to test the various views
    }

    //----------------------------------------------------------
    //Method called by constructor, creates/shows LibrarianView
    //----------------------------------------------------------
    private void createAndShowLibrarianView() {

        Scene currentScene = (Scene)myViews.get("LibrarianView");

        if (currentScene == null) {

            //Create our initial view

            View newView = new LibrarianView(this);

            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }
        swapToView(currentScene);
    }


    //----------------------------------------------------------
    //Creates/show BookSearchView
    //----------------------------------------------------------
    private void createAndShowBookSearchView() {

        Scene currentScene = (Scene)myViews.get("BookSearchView");

        if (currentScene == null) {
            View newView = new BookSearchView(this);
            currentScene = new Scene(newView);
            myViews.put("BookSearchView", currentScene);
        }
        swapToView(currentScene);
    }

    //----------------------------------------------------------
    //Create new book - Called when "Insert new book" is clicked in LibrarianView
    //----------------------------------------------------------
    public void createNewBook() {
        Book b = new Book(this);
        b.createAndShowBookView();
    }

    //----------------------------------------------------------
    //Return to LibrarianView from the BookSearchView
    //----------------------------------------------------------
    public void back() {
        Book b = new Book(this);
        createAndShowLibrarianView();
    }

    //----------------------------------------------------------
    //Create new patron - Called when "Insert new patron" is clicked in LibrarianView
    //----------------------------------------------------------
    public void createNewPatron() {
        Patron p = new Patron(this);
        p.createAndShowPatronView();
    }

    //----------------------------------------------------------
    //Search Books - Called when "Search Books" is clicked in LibrarianView
    //----------------------------------------------------------
    public void titleSearch() {
        createAndShowBookSearchView();
    }

    //----------------------------------------------------------
    //Search Books - Called when "Submit" is clicked in BookSearchView
    //----------------------------------------------------------
    public void searchBooks(String bookTitle) {
        BookCollection bc = new BookCollection(this);

        try {
            bc.findBooksWithTitleLike(bookTitle);
        } catch (InvalidPrimaryKeyException e) {
            e.printStackTrace();
        }

        bc.createAndShowBookCollectionView();
    }

    //----------------------------------------------------------
    //Called when user is finished - closes the program
    //----------------------------------------------------------
    public void closeProgram() {
        Platform.exit();
    }


    //----------------------------------------------------------
    //Changes our View - Called by previous method
    //----------------------------------------------------------
    public void swapToView(Scene newScene) {

        if (newScene == null) {
            System.out.println("Librarian.swapToView(): Missing view for display");
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();

        //Center our window
        WindowPosition.placeCenter(myStage);
    }

    //----------------------------------------------------------
    //Method Called by Book/Patron to return to LibrarianView
    //----------------------------------------------------------
    public void transactionDone() {
        createAndShowLibrarianView();
    }


    //----------------------------------------------------------
    //Abstract Methods inherited from IView - Leave Blank
    //----------------------------------------------------------
    public Object getState(String arg0) {
        return null;
    }
    public void stateChangeRequest(String arg0, Object arg1) {
    }
    public void subscribe(String arg0, IView arg1) {
    }
    public void unSubscribe(String arg0, IView arg1) {
    }


}

 */