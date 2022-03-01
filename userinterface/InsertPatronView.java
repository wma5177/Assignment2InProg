// specify the package
package userinterface;

// system imports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EventObject;
import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class InsertPatronView extends View
{

    // GUI components
    protected TextField bannerId;
    protected TextField firstName;
    protected TextField lastName;
    protected TextField contactPhone;
    protected TextField email;
    protected ComboBox borrowerStatus;
    protected TextField dateOfLatestBorrowerStatus;
    protected TextField dateOfRegistration;
    protected TextField notes;
    protected ComboBox status;


    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertPatronView(IModel librarian)
    {
        super(librarian, "AddStudentBorrowerView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        //fix this later
        //container.getChildren().add(createStatusLog("             "));

        container.getChildren().add(container);

        populateFields();

        // myModel.subscribe("ServiceCharge", this);
        //myModel.subscribe("UpdateStatusMessage", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" New Student Borrower ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("Student Borrower INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);


        Text banID = new Text("Students Banner ID : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        banID.setFont(myFont);
        banID.setWrappingWidth(150);
        banID.setTextAlignment(TextAlignment.RIGHT);
        grid.add(banID, 0, 1);

        bannerId = new TextField();
        bannerId.setEditable(true);
        grid.add(bannerId, 1, 1);

        Text firsName = new Text("Students First Name : ");
        myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        firsName.setFont(myFont);
        firsName.setWrappingWidth(150);
        firsName.setTextAlignment(TextAlignment.RIGHT);
        grid.add(firsName, 0, 2);

        firstName = new TextField();
        firstName.setEditable(true);
        grid.add(firstName, 1, 2);

        Text lasName = new Text(" Students Last Name: ");
        myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        lasName.setFont(myFont);
        lasName.setTextAlignment(TextAlignment.RIGHT);
        grid.add(lasName,0,3);

        lastName = new TextField();
        lastName.setEditable(true);
        grid.add(lastName,1,3);


        Text contactPhon = new Text(" Students Contact Phone Number : ");
        contactPhon.setFont(myFont);
        contactPhon.setWrappingWidth(150);
        contactPhon.setTextAlignment(TextAlignment.RIGHT);
        grid.add(contactPhon, 0, 4);

        contactPhone = new TextField();
        contactPhone.setEditable(true);
        grid.add(contactPhone, 1, 4);

        Text emai = new Text(" Students Email Address : ");
        emai.setFont(myFont);
        emai.setWrappingWidth(150);
        emai.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emai, 0, 5);

        email = new TextField();
        email.setEditable(true);
        grid.add(email, 1, 5);

        Text borrowersta = new Text(" Students Borrower Status : ");
        borrowersta.setFont(myFont);
        borrowersta.setWrappingWidth(150);
        borrowersta.setTextAlignment(TextAlignment.RIGHT);
        grid.add(borrowersta, 0, 6);

        borrowerStatus = new ComboBox();
        borrowerStatus.getItems().addAll(
                "Good Standing",
                "Delinquent"
        );
        borrowerStatus.setValue("Good Standing");
        grid.add(borrowerStatus, 1, 6);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        Text dateOfLate = new Text(" Students Date Of Latest Borrowing Status : ");
        dateOfLate.setFont(myFont);
        dateOfLate.setWrappingWidth(150);
        dateOfLate.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dateOfLate, 0, 7);

        dateOfLatestBorrowerStatus = new TextField();
        dateOfLatestBorrowerStatus.setEditable(false);
        dateOfLatestBorrowerStatus.setText(dtf.format(now));
        grid.add(dateOfLatestBorrowerStatus, 1, 7);

        Text dateOfReg = new Text(" Students Date Of Registration : ");
        dateOfReg.setFont(myFont);
        dateOfReg.setWrappingWidth(150);
        dateOfReg.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dateOfReg, 0, 8);

        dateOfRegistration = new TextField();
        dateOfRegistration.setEditable(false);
        dateOfRegistration.setText(dtf.format(now));
        grid.add(dateOfRegistration, 1, 8);

        Text not = new Text(" Notes : ");
        not.setFont(myFont);
        not.setWrappingWidth(150);
        not.setTextAlignment(TextAlignment.RIGHT);
        grid.add(not, 0, 9);

        notes = new TextField();
        notes.setEditable(true);
        grid.add(notes, 1, 9);

        Text sta = new Text(" Students Borrower Status : ");
        sta.setFont(myFont);
        sta.setWrappingWidth(150);
        sta.setTextAlignment(TextAlignment.RIGHT);
        grid.add(sta, 0, 10);

        status = new ComboBox();
        status.getItems().addAll(
                "Active",
                "Inactive"
        );

        status.setValue("Active");
        grid.add(status, 1, 10);



        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });

        HBox buttonCont = new HBox(10);
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(submitButton);
        Label space = new Label("               ");
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(space);
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(cancelButton);
        vbox.getChildren().add(grid);
        vbox.getChildren().add(buttonCont);

        return vbox;
    }



    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
       /* accountNumber.setText((String)myModel.getState("AccountNumber"));
        acctType.setText((String)myModel.getState("Type"));
        balance.setText((String)myModel.getState("Balance"));
        serviceCharge.setText((String)myModel.getState("ServiceCharge"));
        */
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        if (key.equals("AddStudentBorrowerMessage") == true)
        {
            displayMessage((String)value);
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    public void databaseUpdated(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Database");
        alert.setHeaderText(null);
        alert.setHeaderText("Student Borrower Added To Database");

        alert.showAndWait();
    }

    public void databaseErrorBarcode(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an issue adding to the database!");
        alert.setContentText("Please make sure the barcode is correct.");

        alert.showAndWait();
    }


    @Override
    protected void processAction(EventObject e) {
        clearErrorMessage();

        String banid = bannerId.getText();
        String first = firstName.getText();
        String last = lastName.getText();
        String phone = contactPhone.getText();
        String eml = email.getText();
        String borrowerStat = (String)borrowerStatus.getValue();
        String dateOfLatest = dateOfLatestBorrowerStatus.getText();
        String dateOfReg = dateOfRegistration.getText();
        String note = notes.getText();
        String stat = (String)status.getValue();

        Properties p2 = new Properties();

        p2.setProperty("bannerId",banid);
        p2.setProperty("firstName", first);
        p2.setProperty("lastName", last);
        p2.setProperty("contactPhone", phone);
        p2.setProperty("email", eml);
        p2.setProperty("borrowerStatus", borrowerStat);
        p2.setProperty("dateOfLatestBorrowerStatus", dateOfLatest);
        p2.setProperty("dateOfRegistration", dateOfReg);
        p2.setProperty("notes", note);
        p2.setProperty("status", stat);

        if (banid.length() != 9){
            databaseErrorBarcode();
        }else {
            myModel.stateChangeRequest("AddStudent", p2);
        }

        bannerId.clear();
        firstName.clear();
        lastName.clear();
        contactPhone.clear();
        email.clear();
        notes.clear();
        borrowerStatus.setValue("Good Standing");
        status.setValue("Active");
    }
}


//---------------------------------------------------------------
//	Revision History:
//
