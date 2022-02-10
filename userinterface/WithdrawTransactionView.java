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

/** The class containing the Withdraw Transaction View  for the ATM application */
//==============================================================
public class WithdrawTransactionView extends View
{

	// GUI components
	private JComboBox accountNumbers;
	private JTextField amount;
	private JButton submitButton;
	private JButton cancelButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public WithdrawTransactionView(IModel trans)
	{
		super(trans, "WithdrawTransactionView");

		// set the layout for this panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create our GUI components, add them to this panel
		add(createTitle());
		add(createDataEntryFields());
		add(createNavigationButtons());

		// Error message area
		add(createStatusLog("                          "));

		populateFields();

		myModel.subscribe("TransactionError", this);
	}

	// Overide the paint method to ensure we can set the focus when made visible
	//-------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);
		// amount.requestFocus(); (causes the drop-downs to be non-selectable)
	}

	// Create the labels and fields
	//-------------------------------------------------------------
	private JPanel createTitle()
	{
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel lbl = new JLabel(" Withdraw Transaction ");
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
		JPanel temp1 = new JPanel();
		temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel accountLabel = new JLabel("Withdrawal account : ");
		temp1.add(accountLabel);

		accountNumbers = new JComboBox();
		accountNumbers.setMinimumSize(new Dimension(100, 20));
		temp1.add(accountNumbers);

		temp.add(temp1);

		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel amountLabel = new JLabel("Amount : ");
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
		Vector allAccountIDs = (Vector)myModel.getState("AccountNumberList");

		if (allAccountIDs != null)
		{
			for (int cnt = 0; cnt < allAccountIDs.size(); cnt++)
			{
				String nextAccountID = (String)allAccountIDs.elementAt(cnt);
				accountNumbers.addItem(nextAccountID);
			}

			if (accountNumbers.getItemCount() > 0)
			{
				accountNumbers.setSelectedIndex(0);
			}
		}

		amount.setText("");
	}

	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void processAction(EventObject evt)
	{
		// DEBUG: System.out.println("WithdrawTransactionView.actionPerformed()");

		clearErrorMessage();

		if (evt.getSource() == cancelButton)
		{
			// cancel the withdraw
			processCancel();
		}
		else
		{
			// do the withdraw

			String selectedAccountNumber = (String)accountNumbers.getSelectedItem();

			String amountEntered = amount.getText();

			if ((amountEntered == null) || (amountEntered.length() == 0))
			{
				displayErrorMessage("Please enter an amount to withdraw");
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
						processAccountnumberAndAmount(selectedAccountNumber, amountEntered);
					}
				}
				catch (Exception ex)
				{
					displayErrorMessage("Invalid amount: Please re-enter");
				}

			}
		}

	}

	/**
	 * Process account number and amount selected by user.
	 * Action is to pass this info on to the transaction object
	 */
	//----------------------------------------------------------
	private void processAccountnumberAndAmount(String accountNumber,
		String amount)
	{
		Properties props = new Properties();
		props.setProperty("AccountNumber", accountNumber);
		props.setProperty("Amount", amount);
		myRegistry.updateSubscribers("DoWithdraw", props);
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
		myRegistry.updateSubscribers("CancelWithdraw", null);
	}


	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		if (key.equals("TransactionError") == true)
		{
			String val = (String)value;
			displayErrorMessage(val);
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
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}


}
