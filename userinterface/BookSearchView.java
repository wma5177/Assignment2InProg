package userinterface;
import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
public class BookSearchView extends View {

    protected TextField fName;
    protected TextField lName;

    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    public BookSearchView(IModel Librarian) {
        super(Librarian, "StudentSearchNameView");
        VBox container = new VBox(10.0D);
        container.setPadding(new Insets(15.0D, 5.0D, 5.0D, 5.0D));
        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        //figure this out later
        //container.getChildren().add(createStatusLog("             "));

        container.getChildren().add(container);

        populateFields();

    }

    public BookSearchView(IModel model, String classname) {
        super(model, classname);
    }

    private Node createTitle() {
        HBox var1 = new HBox();
        var1.setAlignment(Pos.CENTER);
        Text var2 = new Text(" Student Search ");
        var2.setFont(Font.font("Arial", FontWeight.BOLD, 20.0D));
        var2.setWrappingWidth(300.0D);
        var2.setTextAlignment(TextAlignment.CENTER);
        var2.setFill(Color.DARKGREEN);
        var1.getChildren().add(var2);
        return var1;
    }

    private VBox createFormContent() {

        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();


        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("First Name:");
        prompt.setWrappingWidth(80);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt,0,0);


        fName = new TextField();
        fName.setEditable(true);
        grid.add(fName,1, 0);

        Text prompt2 = new Text("Last Name:");
        prompt2.setWrappingWidth(80);
        prompt2.setTextAlignment(TextAlignment.CENTER);
        prompt2.setFill(Color.BLACK);
        grid.add(prompt2,0, 1 );


        lName = new TextField();
        lName.setEditable(true);
        grid.add(lName, 1,1);

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


    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    public void populateFields() {
        // this.fName.setText((String)this.myModel.getState("firstName"));

    }

    public void updateState(String var1, Object var2) {
        /*this.clearErrorMessage();
        if (var1.equals("ServiceCharge")) {
            String var3 = (String)var2;
            this.serviceCharge.setText(var3);
            this.displayMessage("Service Charge Imposed: $ " + var3);
        }
         */

    }

    public void displayErrorMessage(String var1) {
        /*
        this.statusLog.displayErrorMessage(var1);
         */
    }

    public void displayMessage(String var1) {
        /*
        this.statusLog.displayMessage(var1);
         */
    }

    public void clearErrorMessage() {
        /*
        this.statusLog.clearErrorMessage();
         */
    }


    @Override
    protected void processAction(EventObject evt) {
        clearErrorMessage();

        Properties p = new Properties();
        System.out.println(fName.getText() + " " + lName.getText());
        p.setProperty("firstName", fName.getText());
        p.setProperty("lastName", lName.getText());
        myModel.stateChangeRequest("SelectStudentView", p);

        fName.clear();
        lName.clear();
    }
}
