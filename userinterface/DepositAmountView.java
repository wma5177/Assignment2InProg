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
import java.util.EventObject;
import java.util.Properties;
import java.util.Vector;

// project imports
import impresario.IModel;

/** The class containing the Deposit Amount View  for the ATM application */
//==============================================================
public class DepositAmountView extends View
{

	// Model

	// GUI components
	private JTextField amount;

	private JButton submitButton;
	private JButton cancelButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public DepositAmountView(IModel trans)
	{
		super(trans, "DepositAmountView");

		// set the layout for this panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create our GUI components, add them to this panel
		add(createTitle());
		add(createDataEntryFields());
		add(createNavigationButtons());

		// Error message area
		add(createStatusLog("                          "));

		populateFields();
	}

	// Overide the paint method to ensure we can set the focus when made visible
	//-------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);
		amount.requestFocus();
	}

	// Create the labels and fields
	//-------------------------------------------------------------
	private JPanel createTitle()
	{
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel lbl = new JLabel(" Deposit Amount Entry ");
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		temp.add(lbl);

		return temp;
	}

	// Create the main data entry fields
	//-------------------------------------------------------------
	private JPanel createDataEntryFields()
	{
		JPanel temp = new JPanel();
		// set the layout for this panel
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

		// data entry fields
		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel amountLabel = new JLabel("Amount To Deposit: ");
		temp2.add(amountLabel);

		amount = new JTextField(20);
		amount.addActionListener(this);
		temp2.add(amount);

		temp.add(temp2);


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
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		temp.add(submitButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		temp.add(cancelButton);

		return temp;
	}

	// Create the status log field
	//-------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		amount.setText("");
	}

	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void processAction(EventObject evt)
	{
		// DEBUG: System.out.println("DepositAmountView.actionPerformed()");

		clearErrorMessage();

		String amountEntered = amount.getText();

		if ((amountEntered == null) || (amountEntered.length() == 0))
		{
			displayErrorMessage("Please enter an amount to deposit");
		}
		else
		{
			try
			{
				double amountVal = Double.parseDouble(amountEntered);
				if (amountVal <= 0)
				{
					displayErrorMessage("Invalid amount: Please re-enter");
				}
				else
				{
					processAmount(amountEntered);
				}
			}
			catch (Exception ex)
			{
				displayErrorMessage("Invalid amount: Please re-enter");
			}

		}
	}

	/**
	 * Process amount entered by user.
	 * Action is to pass this info on to the transaction object.
	 */
	//----------------------------------------------------------
	private void processAmount(String amount)
	{
		Properties props = new Properties();
		props.setProperty("Amount", amount);
		myRegistry.updateSubscribers("Amount", props);
	}

	/**
	 * Process the Cancel button.
	 * The ultimate result of this action is that the transaction will tell the teller to
	 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
	 * It simply tells its model (controller) that the deposit transaction was canceled, and leaves it
	 * to the model to decide to tell the teller to do the switch back.
	 */
	//----------------------------------------------------------
	private void processCancel()
	{
		myRegistry.updateSubscribers("CancelDeposit", null);
	}

	/**
	 * Required by interface, but has no role here
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{

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
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}

}
