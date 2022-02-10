// specify the package
package userinterface;

// system imports
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.EventObject;
import java.util.Properties;
import java.util.Vector;

// project imports
import impresario.IModel;

/** The class containing the Withdraw Receipt  for the ATM application */
//==============================================================
public class WithdrawReceipt extends View
{

	private String				amountWithdrawnString;
	private	String				todaysDateAndTimeString;

	// GUI controls
	private JLabel accountNumber;
	private JLabel amountWithdrawn;
	private JLabel todaysDateAndTime;
	private JLabel currentBalance;

	private JButton okButton;
	// constructor for this class
	//----------------------------------------------------------
	public WithdrawReceipt(IModel trans)
	{
		super(trans, "WithdrawReceipt");

		Calendar todaysCalendar = Calendar.getInstance();	// creation date and time
    	Date todaysDateAndTime = todaysCalendar.getTime();

    	DateFormat theFormatter = DateFormat.getDateTimeInstance();
    	todaysDateAndTimeString = theFormatter.format(todaysDateAndTime);

    	// set the layout for this panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create our GUI components, add them to this panel
		add(createTitle());
		add(createDataDisplayFields());
		add(createNavigationButtons());

		populateFields();

	}

		// Overide the paint method to ensure we can set the focus when made visible
	//-------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);
	}

	// Create the labels and fields
	//-------------------------------------------------------------
	private JPanel createTitle()
	{
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel lbl = new JLabel(" Withdraw Receipt ");
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		temp.add(lbl);

		return temp;
	}

	// Create the main data display fields
	//-------------------------------------------------------------
	private JPanel createDataDisplayFields()
	{
		JPanel temp = new JPanel();
		// set the layout for this panel
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

		// data entry fields
		JPanel temp1 = new JPanel();
		temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel accountLabel = new JLabel("Account Number : ");
		temp1.add(accountLabel);

		accountNumber = new JLabel("                       ");
		temp1.add(accountNumber);

		temp.add(temp1);

		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel amountWithdrawnLabel = new JLabel("Amount Withdrawn : ");
		temp2.add(amountWithdrawnLabel);

		amountWithdrawn = new JLabel("                       ");
		temp2.add(amountWithdrawn);

		temp.add(temp2);

		JPanel temp3 = new JPanel();
		temp3.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel dateAndTimeLabel = new JLabel("Date/Time          : ");
		temp3.add(dateAndTimeLabel);

		todaysDateAndTime = new JLabel("                       ");
		temp3.add(todaysDateAndTime);

		temp.add(temp3);

		JPanel temp4 = new JPanel();
		temp4.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel currentBalanceLabel = new JLabel("Current Balance  : ");
		temp4.add(currentBalanceLabel);

		currentBalance = new JLabel("                       ");
		temp4.add(currentBalance);

		temp.add(temp4);

		return temp;
	}

	// Create the navigation buttons
	//-------------------------------------------------------------
	private JPanel createNavigationButtons()
	{
		JPanel temp = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(1);
		f1.setHgap(25);
		temp.setLayout(f1);

		// create the buttons, listen for events, add them to the panel
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		temp.add(okButton);

		return temp;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		String accountID = (String)((IModel)myModel.getState("Account")).getState("AccountNumber");
		accountNumber.setText(accountID);

		todaysDateAndTime.setText(todaysDateAndTimeString);

		String currentBalanceString = (String)((IModel)myModel.getState("Account")).getState("Balance");
		double currentBalanceVal = Double.parseDouble(currentBalanceString);
		double amountWithdrawnVal = Double.parseDouble((String)myModel.getState("WithdrawAmount"));

		DecimalFormat df2 = new DecimalFormat("0.00");
		currentBalance.setText("$ " + df2.format(currentBalanceVal));
		amountWithdrawn.setText("$ " + df2.format(amountWithdrawnVal));

	}

	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void processAction(EventObject evt)
	{
		// DEBUG: System.out.println("WithdrawReceipt.actionPerformed()");

		// dismiss the view
		processOK();

	}

	/**
	 * OK button hit. Action is to ask the transaction to tell the teller to
	 * to switch to the transaction choice view.
	 */
	//----------------------------------------------------------
	private void processOK()
	{
		myRegistry.updateSubscribers("OK", null);
	}

	/**
	 * Required by interface, but has no role here
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{

	}


}


