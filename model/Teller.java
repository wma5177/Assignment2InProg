// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.MainFrame;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

/** The class containing the Teller  for the ATM application */
//==============================================================
public class Teller implements IView, IModel, ISlideShow
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
	// For Impresario
	private Properties dependencies;
	private ModelRegistry myRegistry;

	private AccountHolder myAccountHolder;

	// GUI Components
	private Hashtable myViews;
	private JFrame		myFrame;

	private String loginErrorMessage = "";
	private String transactionErrorMessage = "";

	// constructor for this class
	//----------------------------------------------------------
	public Teller()
	{
		myFrame = MainFrame.getInstance();
		myViews = new Hashtable();

		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("Teller");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Teller",
				"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		setDependencies();

		// Set up the initial view
		createAndShowTellerView();
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("Login", "LoginError");
		dependencies.setProperty("Deposit", "TransactionError");
		dependencies.setProperty("Withdraw", "TransactionError");
		dependencies.setProperty("Transfer", "TransactionError");
		dependencies.setProperty("BalanceInquiry", "TransactionError");
		dependencies.setProperty("ImposeServiceCharge", "TransactionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("LoginError") == true)
		{
			return loginErrorMessage;
		}
		else
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("Name") == true)
		{
			if (myAccountHolder != null)
			{
				return myAccountHolder.getState("Name");
			}
			else
				return "Undefined";
		}
		else
			return "";
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// STEP 4: Write the sCR method component for the key you
		// just set up dependencies for
		// DEBUG System.out.println("Teller.sCR: key = " + key);

		if (key.equals("Login") == true)
		{
			if (value != null)
			{
				loginErrorMessage = "";

				boolean flag = loginAccountHolder((Properties)value);
				if (flag == true)
				{
					createAndShowTransactionChoiceView();
				}
			}
		}
		else
		if (key.equals("CancelTransaction") == true)
		{
			createAndShowTransactionChoiceView();
		}
		else
		if ((key.equals("Deposit") == true) || (key.equals("Withdraw") == true) ||
			(key.equals("Transfer") == true) || (key.equals("BalanceInquiry") == true) ||
			(key.equals("ImposeServiceCharge") == true))
		{
			String transType = key;

			if (myAccountHolder != null)
			{
				doTransaction(transType);
			}
			else
			{
				transactionErrorMessage = "Transaction impossible: Customer not identified";
			}

		}
		else
		if (key.equals("Logout") == true)
		{
			myAccountHolder = null;

			createAndShowTellerView();
		}

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// DEBUG System.out.println("Teller.updateState: key: " + key);

		stateChangeRequest(key, value);
	}

	/**
	 * Login AccountHolder corresponding to user name and password.
	 */
	//----------------------------------------------------------
	public boolean loginAccountHolder(Properties props)
	{
		try
		{
			myAccountHolder = new AccountHolder(props);
			return true;
		}
		catch (InvalidPrimaryKeyException ex)
		{
				loginErrorMessage = "ERROR: " + ex.getMessage();
				return false;
		}
		catch (PasswordMismatchException exec)
		{

				loginErrorMessage = "ERROR: " + exec.getMessage();
				return false;
		}
	}


	/**
	 * Create a Transaction depending on the Transaction type (deposit,
	 * withdraw, transfer, etc.). Use the AccountHolder holder data to do the
	 * create.
	 */
	//----------------------------------------------------------
	public void doTransaction(String transactionType)
	{
		try
		{
			Transaction trans = TransactionFactory.createTransaction(
				transactionType, myAccountHolder);

			trans.subscribe("CancelTransaction", this);
			trans.stateChangeRequest("DoYourJob", "");
		}
		catch (Exception ex)
		{
			transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
			new Event(Event.getLeafLevelClassName(this), "createTransaction",
					"Transaction Creation Failure: Unrecognized transaction " + ex.toString(),
					Event.ERROR);
		}
	}

	//----------------------------------------------------------
	private void createAndShowTransactionChoiceView()
	{
		View localView = (View)myViews.get("TransactionChoiceView");

		if (localView == null)
		{
				// create our initial view
				localView = ViewFactory.createView("TransactionChoiceView", this); // USE VIEW FACTORY

				// NEEDED FOR IMPRESARIO
				localView.subscribe("Logout", this);
				localView.subscribe("Deposit", this);
				localView.subscribe("Withdraw", this);
				localView.subscribe("Transfer", this);
				localView.subscribe("BalanceInquiry", this);
				localView.subscribe("ImposeServiceCharge", this);

				myViews.put("TransactionChoiceView", localView);

				// make the view visible by installing it into the frame
				swapToView(localView);
		}
		else
		{
			swapToView(localView);
		}
	}

	//------------------------------------------------------------
	private void createAndShowTellerView()
	{
		View localView = (View)myViews.get("TellerView");

		if (localView == null)
		{
				// create our initial view
				localView = ViewFactory.createView("TellerView", this); // USE VIEW FACTORY

				// NEEDED FOR IMPRESARIO
				localView.subscribe("Login", this);

				myViews.put("TellerView", localView);

				// make the view visible by installing it into the frame
				myFrame.getContentPane().add(localView); // just the main panel in this case
				myFrame.pack();
		}
		else
		{
			swapToView(localView);
		}
	}


	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}

	//----------------------------------------------------------------------------
	protected void swapToPanelView(JPanel otherView)
	{
		JPanel currentView = (JPanel)myFrame.getContentPane().getComponent(0);
		// and remove it
		myFrame.getContentPane().remove(currentView);
		// add our view
		myFrame.getContentPane().add(otherView);
		//pack the frame and show it
		myFrame.pack();
		//Place in center
		WindowPosition.placeCenter(myFrame);
	}

	//-----------------------------------------------------------------------------
	public void swapToView(IView otherView)
	{

		if (otherView == null)
		{
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		if (otherView instanceof JPanel)
		{
			swapToPanelView((JPanel)otherView);
		}//end of SwapToView
		else
		{
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Non-displayable view object sent ", Event.ERROR);
		}

	}

}

