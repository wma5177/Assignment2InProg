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

/** The class containing the Transfer Receipt  for the ATM application */
//==============================================================
public class TransferReceipt extends View
{

	// Model
	private	String				todaysDateAndTimeString;

	// GUI controls
	private JLabel sourceAccountNumber;
	private JLabel destAccountNumber;
	private JLabel amountTransferred;
	private JLabel todaysDateAndTime;
	private JLabel sourceCurrentBalance;
	private JLabel destCurrentBalance;

	private JButton okButton;

	// constructor for this class
	//----------------------------------------------------------
	public TransferReceipt(IModel trans)
	{
		super(trans, "TransferReceipt");

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

		JLabel lbl = new JLabel(" Transfer Receipt ");
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

		JLabel sourceAccountLabel = new JLabel("FROM Account                         : ");
		temp1.add(sourceAccountLabel);

		sourceAccountNumber = new JLabel("                       ");
		temp1.add(sourceAccountNumber);

		temp.add(temp1);

		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel destAccountLabel = new JLabel("TO Account                           : ");
		temp2.add(destAccountLabel);

		destAccountNumber = new JLabel("                       ");
		temp2.add(destAccountNumber);

		temp.add(temp2);

		JPanel temp3 = new JPanel();
		temp3.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel amountLabel = new JLabel("Amount Transferred                 : ");
		temp3.add(amountLabel);

		amountTransferred = new JLabel("                       ");
		temp3.add(amountTransferred);

		temp.add(temp3);

		JPanel temp4 = new JPanel();
		temp4.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel dateAndTimeLabel = new JLabel("Date/Time                             : ");
		temp4.add(dateAndTimeLabel);

		todaysDateAndTime = new JLabel("                       ");
		temp4.add(todaysDateAndTime);

		temp.add(temp4);

		JPanel temp5 = new JPanel();
		temp5.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel sourceCurrentBalanceLabel = new JLabel("New Balance (FROM account) : ");
		temp5.add(sourceCurrentBalanceLabel);

		sourceCurrentBalance = new JLabel("                       ");
		temp5.add(sourceCurrentBalance);

		temp.add(temp5);

		JPanel temp6 = new JPanel();
		temp6.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel destCurrentBalanceLabel = new JLabel("New Balance (TO account)      : ");
		temp6.add(destCurrentBalanceLabel);

		destCurrentBalance = new JLabel("                       ");
		temp6.add(destCurrentBalance);

		temp.add(temp6);

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
		String sourceAccountID = (String)((IModel)myModel.getState("SourceAccount")).getState("AccountNumber");
		sourceAccountNumber.setText(sourceAccountID);

		String destAccountID = (String)((IModel)myModel.getState("DestAccount")).getState("AccountNumber");
		destAccountNumber.setText(destAccountID);

		todaysDateAndTime.setText(todaysDateAndTimeString);

		String sourceCurrentBalanceString = (String)((IModel)myModel.getState("SourceAccount")).getState("Balance");
		String destCurrentBalanceString = (String)((IModel)myModel.getState("DestAccount")).getState("Balance");

		double sourceCurrentBalanceVal = Double.parseDouble(sourceCurrentBalanceString);
		double destCurrentBalanceVal = Double.parseDouble(destCurrentBalanceString);
		double amountTransferredVal = Double.parseDouble((String)myModel.getState("TransferAmount"));

		DecimalFormat df2 = new DecimalFormat("0.00");
		sourceCurrentBalance.setText("$ " + df2.format(sourceCurrentBalanceVal));
		destCurrentBalance.setText("$ " + df2.format(destCurrentBalanceVal));
		amountTransferred.setText("$ " + df2.format(amountTransferredVal));

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

//---------------------------------------------------------------
//	Revision History:
//
//	$Log: TransferReceipt.java,v $
//	Revision 1.2  2004/06/22 19:07:58  smitra
//	Completed to include all functionality for v1.0
//
//	Revision 1.1  2004/06/18 20:34:38  smitra
//	First check in
//

