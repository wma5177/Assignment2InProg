
// specify the package
package userinterface;

// system imports
import java.util.EventObject;
import java.util.Properties;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// project imports
import impresario.IModel;
import model.Librarian;

// The class containing the Teller View  for the ATM application
//==============================================================
public class LibrarianView extends View
{
    // GUI stuff
    private Button newBookButton;
    private Button newPatronButton;
    private Button searchPatronsButton;
    private Button searchBooksButton;
    private Button doneButton;

    // For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public LibrarianView(IModel lib)
    {
        super(lib, "LibrarianView");

        // create a container for showing the contents
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog("                          "));

        container.getChildren().add(container);

        // STEP 0: Be sure you tell your model what keys you are interested in
        myModel.subscribe("TransactionError", this);
    }

    // Create the label (Text) for the title of the screen
    //-------------------------------------------------------------
    private Node createTitle()
    {

        Text titleText = new Text("        LIBRARY SYSTEM        ");
        titleText.setFont(Font.font("Garamond", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BLACK);

        return titleText;
    }

    // Create the main form contents
    //-------------------------------------------------------------
    private GridPane createFormContents()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        };

        newBookButton = new Button("Insert New Book");
        newBookButton.setOnAction(buttonHandler);
        grid.add(newBookButton, 0, 0);

        newPatronButton = new Button("Insert New Patron");
        newPatronButton.setOnAction(buttonHandler);
        grid.add(newPatronButton, 0, 1);

        searchBooksButton = new Button("Search Books");
        searchBooksButton.setOnAction(buttonHandler);
        grid.add(searchBooksButton, 0, 2);

        doneButton = new Button("Done");
        doneButton.setOnAction(buttonHandler);
        grid.add(doneButton, 0, 4);

        return grid;
    }



    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    // This method processes events generated from our GUI components.
    // Make the ActionListeners delegate to this method
    //-------------------------------------------------------------


    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("LoginError") == true)
        {
            // display the passed text
            displayErrorMessage((String)value);
        }

    }



    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }



    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    @Override
    protected void processAction(EventObject evt) {
        Object eventSource = evt.getSource();

        if (eventSource.equals(newBookButton))
        {
            myModel.stateChangeRequest("InsertNewBook", "");
        }
        else if (eventSource.equals(newPatronButton))
        {
            myModel.stateChangeRequest("InsertNewPatron", "");
        }
        else if (eventSource.equals(searchBooksButton))
        {
            myModel.stateChangeRequest("SearchBooks", "");
        }
        else if (eventSource.equals(doneButton))
        {
            myModel.stateChangeRequest("Exit", "");
        }
    }
}