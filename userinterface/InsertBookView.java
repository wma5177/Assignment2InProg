package userinterface;

import impresario.IModel;
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

import java.util.EventObject;
import java.util.Properties;

public class InsertBookView<pubilc> extends View{
    // GUI components
    protected TextField barcode;
    protected TextField title;
    protected TextField author1;
    protected TextField author2;
    protected TextField author3;
    protected TextField author4;
    protected TextField publisher;
    protected TextField yearOfPublication;
    protected TextField isbn;
    protected TextField suggestedPrice;
    protected TextField notes;

    protected ComboBox discipline;
    protected ComboBox quality;
    protected ComboBox status;

    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public InsertBookView(IModel book)
    {
        super(book, "AddBookView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

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

        Text titleText = new Text(" Add New Book ");
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

        Text prompt = new Text("BOOK INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text bcode = new Text(" Book's Barcode : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        bcode.setFont(myFont);
        bcode.setWrappingWidth(150);
        bcode.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bcode, 0, 1);

        barcode = new TextField();
        barcode.setEditable(true);
        grid.add(barcode, 1, 1);

        Text tit = new Text(" Title : ");
        tit.setFont(myFont);
        tit.setWrappingWidth(150);
        tit.setTextAlignment(TextAlignment.RIGHT);
        grid.add(tit, 0, 2);

        title = new TextField();
        title.setEditable(true);
        grid.add(title, 1, 2);


        Text auth1 = new Text(" Author 1 : ");
        auth1.setFont(myFont);
        auth1.setWrappingWidth(150);
        auth1.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth1, 0, 4);

        author1 = new TextField();
        author1.setEditable(true);
        grid.add(author1, 1, 4);

        Text auth2 = new Text(" Author 2 : ");
        auth2.setFont(myFont);
        auth2.setWrappingWidth(150);
        auth2.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth2, 0, 5);

        author2 = new TextField();
        author2.setEditable(true);
        grid.add(author2, 1, 5);

        Text auth3 = new Text(" Author 3 : ");
        auth3.setFont(myFont);
        auth3.setWrappingWidth(150);
        auth3.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth3, 0, 6);

        author3 = new TextField();
        author3.setEditable(true);
        grid.add(author3, 1, 6);

        Text auth4 = new Text(" Author 4 : ");
        auth4.setFont(myFont);
        auth4.setWrappingWidth(150);
        auth4.setTextAlignment(TextAlignment.RIGHT);
        grid.add(auth4, 0, 7);

        author4 = new TextField();
        author4.setEditable(true);
        grid.add(author4, 1, 7);

        Text pub = new Text(" Publisher : ");
        pub.setFont(myFont);
        pub.setWrappingWidth(150);
        pub.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pub, 0, 8);

        publisher = new TextField();
        publisher.setEditable(true);
        grid.add(publisher, 1, 8);

        Text yOf = new Text(" Year of Publication : ");
        yOf.setFont(myFont);
        yOf.setWrappingWidth(150);
        yOf.setTextAlignment(TextAlignment.RIGHT);
        grid.add(yOf, 0, 9);

        yearOfPublication = new TextField();
        yearOfPublication.setEditable(true);
        grid.add(yearOfPublication, 1, 9);

        Text iS = new Text(" ISBN : ");
        iS.setFont(myFont);
        iS.setWrappingWidth(150);
        iS.setTextAlignment(TextAlignment.RIGHT);
        grid.add(iS, 0, 10);

        isbn = new TextField();
        isbn.setEditable(true);
        grid.add(isbn, 1, 10);

        Text con = new Text(" Quality : ");
        con.setFont(myFont);
        con.setWrappingWidth(150);
        con.setTextAlignment(TextAlignment.RIGHT);
        grid.add(con, 0, 11);

        quality = new ComboBox();
        quality.getItems().addAll(
                "Good",
                "Damaged"
        );

        quality.setValue("Good");
        grid.add(quality, 1, 11);

        Text sug = new Text(" Suggested Price : ");
        sug.setFont(myFont);
        sug.setWrappingWidth(150);
        sug.setTextAlignment(TextAlignment.RIGHT);
        grid.add(sug, 0, 12);

        suggestedPrice = new TextField();
        suggestedPrice.setText("0.00");
        suggestedPrice.setEditable(true);
        grid.add(suggestedPrice, 1, 12);

        Text not = new Text(" Notes : ");
        not.setFont(myFont);
        not.setWrappingWidth(150);
        not.setTextAlignment(TextAlignment.RIGHT);
        grid.add(not, 0, 13);

        notes = new TextField();
        notes.setEditable(true);
        grid.add(notes, 1, 13);

        Text bStatus = new Text(" Book's Status : ");
        bStatus.setFont(myFont);
        bStatus.setWrappingWidth(150);
        bStatus.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bStatus, 0, 14);

        status = new ComboBox();
        status.getItems().addAll(
                "Active",
                "Inactive"
        );

        status.setValue("Active");
        grid.add(status, 1, 14);

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
        // consider using GridPane.setHgap(10); instead of label space
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

    /*
    private void processAction(ActionEvent e) {

        clearErrorMessage();

        String bar = barcode.getText();
        String titl = title.getText();
        String disi = " ";
        String au1 = author1.getText();
        String au2 = author2.getText();
        String au3 = author3.getText();
        String au4 = author4.getText();
        String publi = publisher.getText();
        String yeaO = yearOfPublication.getText();
        String isb = isbn.getText();
        String condi = (String) quality.getValue();
        String sugPric = suggestedPrice.getText();
        String no = notes.getText();
        String sta = (String) status.getValue();

        Properties p2 = new Properties();

        p2.setProperty("barcode", bar);
        p2.setProperty("title", titl);
        p2.setProperty("discipline", disi);
        p2.setProperty("author1", au1);
        p2.setProperty("author2", au2);
        p2.setProperty("author3", au3);
        p2.setProperty("author4", au4);
        p2.setProperty("publisher", publi);
        p2.setProperty("yearOfPublication", yeaO);
        p2.setProperty("isbn", isb);
        p2.setProperty("quality", condi);
        p2.setProperty("suggestedPrice", sugPric);
        p2.setProperty("notes", no);
        p2.setProperty("status", sta);

        if (yeaO == null || yeaO == "" || yeaO.length() == 0 || yeaO.length() > 4 ||
                bar.length() != 6){
            databaseErrorYear();
        }else {
            myModel.stateChangeRequest("InsertBook", p2);
        }

        barcode.clear();
        title.clear();
        author1.clear();
        author2.clear();
        author3.clear();
        author4.clear();
        publisher.clear();
        yearOfPublication.clear();
        isbn.clear();
        suggestedPrice.clear();
        notes.clear();

        quality.setValue("Good");
        status.setValue("Active");
        suggestedPrice.setText("0.00");

    }

     */


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

        if (key.equals("PopulateAddBookMessage") == true)
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
        alert.setHeaderText("Book Added to Database");

        alert.showAndWait();
    }

    public void databaseErrorYear(){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database");
        alert.setHeaderText("Ooops, there was an issue adding to the database!");
        alert.setContentText("Cannot add to database. Check year/barcode.");

        alert.showAndWait();
    }

    @Override
    protected void processAction(EventObject evt) {
        clearErrorMessage();

        String bar = barcode.getText();
        String titl = title.getText();
        String disi = " ";
        String au1 = author1.getText();
        String au2 = author2.getText();
        String au3 = author3.getText();
        String au4 = author4.getText();
        String publi = publisher.getText();
        String yeaO = yearOfPublication.getText();
        String isb = isbn.getText();
        String condi = (String) quality.getValue();
        String sugPric = suggestedPrice.getText();
        String no = notes.getText();
        String sta = (String) status.getValue();

        Properties p2 = new Properties();

        p2.setProperty("barcode", bar);
        p2.setProperty("title", titl);
        p2.setProperty("discipline", disi);
        p2.setProperty("author1", au1);
        p2.setProperty("author2", au2);
        p2.setProperty("author3", au3);
        p2.setProperty("author4", au4);
        p2.setProperty("publisher", publi);
        p2.setProperty("yearOfPublication", yeaO);
        p2.setProperty("isbn", isb);
        p2.setProperty("quality", condi);
        p2.setProperty("suggestedPrice", sugPric);
        p2.setProperty("notes", no);
        p2.setProperty("status", sta);

        if (yeaO == null || yeaO == "" || yeaO.length() == 0 || yeaO.length() > 4 ||
                bar.length() != 6){
            databaseErrorYear();
        }else {
            myModel.stateChangeRequest("InsertBook", p2);
        }

        barcode.clear();
        title.clear();
        author1.clear();
        author2.clear();
        author3.clear();
        author4.clear();
        publisher.clear();
        yearOfPublication.clear();
        isbn.clear();
        suggestedPrice.clear();
        notes.clear();

        quality.setValue("Good");
        status.setValue("Active");
        suggestedPrice.setText("0.00");

    }
}

//---------------------------------------------------------------
//	Revision History:
//



/*
package userinterface;

import java.awt.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.EventObject;

import impresario.IModel;

public class InsertBookView<ComboBox> extends View {
    protected JTextField bookBarcode;
    protected JTextField bookTitle;
    protected JTextField bookAuthor;
    protected JTextField bookPublisher;
    protected JTextField bookYearPublished;
    protected JTextField isbn;
    protected JTextField suggestedPrice;

    protected ComboBox status;

    protected JButton cancelButton;
    protected JButton submitButton;

    //for error messaging
    protected MessageView statusLog;


    public InsertBookView(IModel model, String classname) {
        super(model, classname);

        //set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //create GUI components, adding them to the panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        //Error message area
        add(createStatusLog("                  "));

        //populateDataFields();

    }

    public InsertBookView(IModel model, String classname) {
        super(model, classname);
    }

    private JPanel createTitle() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(Box.createRigidArea(new Dimension(250, 10)));

        JPanel temp_1 = new JPanel();
        temp_1.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl_1 = new JLabel("     Library Project      ");
        Font myFont_1 = new Font("Helvetica", Font.BOLD, 18);
        lbl_1.setFont(myFont_1);
        temp_1.add(lbl_1);

        container.add(temp_1);

        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        String title = "BOOK INFORMATION";
        JLabel lbl = new JLabel(title);
        Font myFont = new Font("Helvetica", Font.BOLD, 15);
        lbl.setFont(myFont);
        temp.add(lbl);

        container.add(temp);
        return container;
    }
    protected JPanel createDataEntryFields(){
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        temp.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel temp0 = new JPanel();
        temp0.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel bookAuthorLabel = new JLabel("  Author Name: ");
        Font myFont = new Font("Helvetica", Font.BOLD, 12);
        bookAuthorLabel.setFont(myFont);
        temp0.add(bookAuthorLabel);

        bookAuthor = new JTextField(20);
        bookAuthor = setEditable(false);
        temp0.add(bookAuthor);

        temp.add(temp0);

        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel bookTitleLabel = new JLabel("  Book Title:     ");
        temp1.add(bookTitleLabel);

        bookTitle = new JTextField(20);
        bookTitle.setEditable(false);
        temp1.add(bookTitle);

        temp.add(temp1);

        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel bookYearPublishedLabel = new JLabel("  Book Year Published:     ");
        temp2.add(bookYearPublishedLabel);

        bookYearPublished = new JTextField(20);
        bookYearPublished.setEditable(false);
        temp2.add(bookYearPublished);

        temp.add(temp2);

        return(temp);
    }

    protected JPanel createNavigationButtons(){
        JPanel temp = new JPanel();

        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
        temp.add(Box.createRigidArea(new Dimension(0,10)));

        JPanel temp1 = new JPanel();
        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
        f1.setVgap(1);
        f1.setHgap(25);
        temp1.setLayout(f1);

        //buttons
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        temp1.add(cancelButton);

        temp.add(temp1);

        return(temp);

    }

    protected JPanel createStatusLog(String initialMessage){

        statusLog = new MessageView(initialMessage);
        return statusLog;
    }

    public void populateFields(){

    }

    @Override
    public void updateState(String key, Object value) {
        clearErrorMessage();

        if (key.equals("Transaction") == true) {
            displayErrorMessage((String)value);
        }
    }

    public void displayErrorMessage(String message){
        statusLog.displayErrorMessage(message);
    }

    public void clearErrorMessage(){
        statusLog.clearErrorMessage();
    }

    @Override
    protected void processAction(EventObject evt) {

    }
}

 */