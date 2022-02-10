// specify the package
package model;

// system imports
import javax.swing.JFrame;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the ImposeServiceChargeTransaction for the ATM application */
//==============================================================
public class ImposeServiceChargeTransaction extends Transaction
{
	private AccountCollection accounts;
	private Account selectedAccount;

	// GUI Components

	private String transactionErrorMessage = "";
	private String accountUpdateStatusMessage = "";

	/**
	 * Constructor for this class.
	 *
	 *
	 */
	//----------------------------------------------------------
	public ImposeServiceChargeTransaction(AccountHolder cust)
		throws Exception
	{
		super(cust);
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("AccountHolderIDEntered", "TransactionError");
		dependencies.setProperty("CancelImposeServiceCharge", "CancelTransaction");

		myRegistry.setDependencies(dependencies);
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("TransactionError") == true)
		{
			return transactionErrorMessage;
		}
		else
		if (key.equals("UpdateStatusMessage") == true)
		{
			return accountUpdateStatusMessage;
		}
		else
		if (key.equals("AccountList") == true)
		{
			return accounts;
		}
		else
		if (key.equals("SelectedAccount") == true)
		{
			return selectedAccount;
		}
		else
		if (selectedAccount != null)
		{
			Object val = selectedAccount.getState(key);
			if (val != null)
			{
				return val;
			}
		}
		else
		if (accounts != null)
		{
			Object val = accounts.getState(key);
			if (val != null)
			{
				return val;
			}
		}

		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if (key.equals("DoYourJob") == true)
		{
			createAndShowAccountHolderIDEntryView();
		}
		else
		if (key.equals("AccountHolderIDEntered") == true)
		{

			String acctHolderID = (String)value;
			try
			{
				AccountHolder customer = new AccountHolder(acctHolderID);

				try
				{
					accounts = new AccountCollection(customer);
					// accounts.subscribe("CancelAccountList", this);
					// accouts.stateChangeRequest("DisplayView", ""); (as shown on Impose Service Charge - 3)
					createAndShowAccountListView();
				}
				catch (Exception ex)
				{
					transactionErrorMessage = "Error getting account list";
				}
			}
			catch (Exception e)
			{
				System.out.println(e);
				transactionErrorMessage = "Invalid Account ID Entered";
			}
		}
		else
		if (key.equals("AccountSelected") == true)
		{
			String accountNumber = (String)value;
			selectedAccount = accounts.retrieve(accountNumber);
			selectedAccount.subscribe("AccountCancelled", this);
			selectedAccount.subscribe("UpdateStatusMessage", this);

			createAndShowAccountView();
		}
		else
		if (key.equals("ServiceCharge") == true)
		{
			selectedAccount.stateChangeRequest("ServiceCharge", value);
		}
		else
		if (key.equals("CancelAccountList") == true)
		{
			createAndShowAccountHolderIDEntryView();
		}
		else
		if (key.equals("AccountCancelled") == true)
		{
			createAndShowAccountListView();
		}
		else
		if (key.equals("UpdateStatusMessage") == true)
		{
			accountUpdateStatusMessage = (String)value;
		}

		myRegistry.updateSubscribers(key, this);
	}

	//------------------------------------------------------
	protected void createAndShowAccountHolderIDEntryView()
	{
		View localView = (View)myViews.get("AccountHolderIDEntryView");

		if (localView == null)
		{
			// create our initial view
			localView = ViewFactory.createView("AccountHolderIDEntryView", this);

			// NEEDED FOR IMPRESARIO
			localView.subscribe("AccountHolderIDEntered", this);
			localView.subscribe("CancelImposeServiceCharge", this);

			myViews.put("AccountHolderIDEntryView", localView);

			// make the view visible by installing it into the frame
			swapToView(localView);
		}
		else
		{
			// make the view visible by installing it into the frame
			swapToView(localView);
		}
	}

	//------------------------------------------------------
	protected void createAndShowAccountListView()
	{
		View localView = ViewFactory.createView("AccountCollectionView", this);

		// NEEDED FOR IMPRESARIO
		localView.subscribe("AccountSelected", this);
		localView.subscribe("CancelAccountList", this);

		// make the view visible by installing it into the frame
		swapToView(localView);
	}

	//------------------------------------------------------
	protected void createAndShowAccountView()
	{
		// create our initial view
		View localView = ViewFactory.createView("AccountView", this);

		// NEEDED FOR IMPRESARIO
		localView.subscribe("ServiceCharge", this);
		localView.subscribe("AccountCancelled", this);

		myViews.put("AccountView", localView);

		// make the view visible by installing it into the frame
		swapToView(localView);
	}

	//------------------------------------------------------
	protected View createView() // not needed for this class
	{
		return null;
	}
}

