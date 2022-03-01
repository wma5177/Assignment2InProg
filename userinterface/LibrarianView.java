//New code
//I wanted to try this with the new javafx to see if it would work better and be more consistent

// specify the package
package userinterface;

// system imports
import java.text.NumberFormat;
import java.util.EventObject;
import java.util.Properties;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// project imports
import impresario.IModel;

/** The class containing the Teller View  for the ATM application */
//==============================================================
public class LibrarianView extends View
{

    // GUI stuff
    private TextField userid;
    private PasswordField password;
    private Button submitButton;

    // For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public LibrarianView( IModel librarian)
    {

        super(librarian, "Librarian");

        // create a container for showing the contents
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        //container.getChildren().add(createStatusLog("                          "));

        container.getChildren().add(container);

        populateFields();

        // STEP 0: Be sure you tell your model what keys you are interested in
        myModel.subscribe("LoginError", this);
    }

    // Create the label (Text) for the title of the screen
    //-------------------------------------------------------------
    private Node createTitle()
    {

        Text titleText = new Text("       Brockport Library Login          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);


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

        // data entry fields
        Label userName = new Label("User ID:");
        grid.add(userName, 0, 0);

        userid = new TextField();
        userid.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(userid, 1, 0);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 1);

        password = new PasswordField();
        password.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(password, 1, 1);

        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        HBox btnContainer = new HBox(10);
        btnContainer.setAlignment(Pos.BOTTOM_RIGHT);
        btnContainer.getChildren().add(submitButton);
        grid.add(btnContainer, 1, 3);

        return grid;
    }



    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        userid.setText("");
        password.setText("");
    }

    // This method processes events generated from our GUI components.
    // Make the ActionListeners delegate to this method
    //-------------------------------------------------------------


    /**
     * Process userid and pwd supplied when Submit button is hit.
     * Action is to pass this info on to the teller object
     */
    //----------------------------------------------------------
    private void processUserIDAndPassword(String useridString,
                                          String passwordString)
    {
        Properties props = new Properties();

        props.setProperty("bannerId", useridString);
        props.setProperty("password", passwordString);

        // clear fields for next time around
        userid.setText("");
        password.setText("");

        try {
            myModel.stateChangeRequest("Login", props);
        }catch (Exception x){
            databaseError();
        }
    }

    //---------------------------------------------------------

    public void updateState(String key, Object value)
    {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("LoginError") == true)
        {
            // display the passed text
            displayErrorMessage((String)value);
        }
    }
    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        String mes = message;
        if (mes != "") {
            databaseError();
        }else{
            System.out.println("");
        }
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }


    public void databaseError(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an issue finding your account!");

        String error = (String)myModel.getState("LoginError");
        alert.setContentText(error);

        alert.showAndWait();
    }

    public void databaseErrorEmpty(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an issue finding your account!");
        alert.setContentText("The User ID and Password fields cannot be empty.");

        alert.showAndWait();
    }

    @Override
    protected void processAction(EventObject evt) {
            // DEBUG: System.out.println("TellerView.actionPerformed()");
            clearErrorMessage();

            String useridEntered = userid.getText();
            String passwordEntered = password.getText();

            if ((useridEntered == null) || (useridEntered.length() == 0) ||
                    (passwordEntered == null) || (passwordEntered.length() == 0))
            {
                databaseErrorEmpty();
                userid.requestFocus();
            }
            else
            {
                processUserIDAndPassword(useridEntered, passwordEntered);
            }
        }

}


/* OLD CODE

// specify the package
package userinterface;

// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

// project imports
import impresario.IModel;
public class LibrarianView extends View {

    //GUI Stuff
    private JLabel libLabel;
    private JButton insertBook;
    private JButton insertPatron;
    private JButton searchBooks;
    private JButton searchPatrons;
    private JButton doneButton;

    //For showing error message
    private MessageView statusLog;

    //constructor for this class -- takes a model object

    public LibrarianView( IModel librarian) {
        super(librarian, "LibrarianView");

        //set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //create GUI components, then add them to this panel
        add(createTitle());
        add(createNavigationButtons());

        //NOT SURE IF WE NEED THIS SO IT IS COMMENTED OUT FOR THE TIME BEING

        //Error message area
        //add(createStatusLog("                "));


        //Step 0:  Tell your model which keys you are interested in
        myModel.subscribe("LoginError", this);
    }

    //override the paint method to ensure we can set the focus when made visible
    public void paint(Graphics g){
        super.paint(g);
        libLabel.requestFocus();
    }

    //create labels and fields
    private JPanel createTitle(){
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel libLabel = new JLabel("LIBRARY SYSTEM");
        Font myFont = new Font("Helvetica", Font.BOLD,20);
        libLabel.setFont(myFont);
        temp.add(libLabel);

        return temp;
    }



    //create main data entry fields
    private JPanel createNavigationButtons(){
        JPanel temp = new JPanel();
        //set the layout for this panel
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        //buttons
        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton insertBook = new JButton("Insert Book");
        insertBook.addActionListener(this);
        temp1.add(insertBook);

        JButton insertPatron = new JButton("Insert Patron");
        insertPatron.addActionListener(this);
        temp1.add(insertPatron);

        JButton searchBooks = new JButton("Search Books");
        searchBooks.addActionListener(this);
        temp1.add(searchBooks);

        JButton searchPatrons = new JButton("Search Patrons");
        searchPatrons.addActionListener(this);
        temp1.add(searchPatrons);

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(this);
        temp1.add(doneButton);

        return temp1;
    }

    //Process events generated from GUI Components
    public void processAction(EventObject e){
        if(e.getSource() == insertBook){
            myRegistry.updateSubscribers("Insert Book", null);
        }else if(e.getSource() == insertPatron){
            myRegistry.updateSubscribers("Insert Patron", null);
        }else if(e.getSource() == searchBooks){
            myRegistry.updateSubscribers("Search Books", null);
        }else if(e.getSource() == searchPatrons){
            myRegistry.updateSubscribers("Search Patrons", null);
        }else if(e.getSource() == doneButton){
            myRegistry.updateSubscribers("Done", null);
        }
    }




    public LibrarianView(IModel model, String classname) {
        super(model, classname);
    }

    @Override
    public void updateState(String key, Object value) {

    }

}

 */
