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
