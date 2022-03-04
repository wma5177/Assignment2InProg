
// specify the package

// system imports
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.FileOutputStream;
import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// project imports
import event.Event;
import event.EventLog;
import common.PropertyFile;

import model.Librarian;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;


/** The class containing the main program  for the ATM application */
//==============================================================
public class Assign2driver extends Application
{

    private Librarian librarian;		// the main behavior for the application

    /** Main frame of the application */
    private Stage mainStage;


    // start method for this class, the main application object
    //----------------------------------------------------------
    public void start(Stage primaryStage)
    {
        System.out.println("Library");

        // Create the top-level container (main frame) and add contents to it.
        MainStageContainer.setStage(primaryStage, "Library");
        mainStage = MainStageContainer.getInstance();

        // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
        // 'X' IN THE WINDOW), and show it.
        mainStage.setOnCloseRequest(event -> System.exit(0));

        try
        {
            librarian = new Librarian();
        }
        catch(Exception exc)
        {
            System.err.println("Could not create Librarian!");
            new Event(Event.getLeafLevelClassName(this), "Assign2driver.<init>", "Unable to create Librarian object", Event.ERROR);
            exc.printStackTrace();
        }


        WindowPosition.placeCenter(mainStage);

        mainStage.show();
    }


    /**
     * The "main" entry point for the application. Carries out actions to
     * set up the application
     */
    //----------------------------------------------------------
    public static void main(String[] args)
    {

        launch(args);
    }

}

/*
import event.Event;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Librarian;
//import userinterface.MainStageContainer;
import userinterface.WindowPosition;

public class Assgn2Driver extends Application {
    private Librarian myTeller;
    private Stage mainStage;

    public ATM() {
    }

    public void start(Stage var1) {
        System.out.println("ATM Version 3.00");
        System.out.println("Copyright 2004/2015 Sandeep Mitra and T M Rao");
        MainStageContainer.setStage(var1, "Brockport Bank ATM Version 3.00");
        this.mainStage = MainStageContainer.getInstance();
        this.mainStage.setOnCloseRequest(new 1(this));

        try {
            this.myTeller = new Teller();
        } catch (Exception var3) {
            System.err.println("ATM.ATM - could not create Teller!");
            new Event(Event.getLeafLevelClassName(this), "ATM.<init>", "Unable to create Teller object", 3);
            var3.printStackTrace();
        }

        WindowPosition.placeCenter(this.mainStage);
        this.mainStage.show();
    }

    public static void main(String[] var0) {
        launch(var0);
    }
}
 */