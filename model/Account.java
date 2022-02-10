// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the Account for the ATM application */
//==============================================================
public class Account extends EntityBase implements IView
{
	private static final String myTableName = "Account";

	protected Properties dependencies;

	// GUI Components

	private String updateStatusMessage = "";

	// constructor for this class
	//----------------------------------------------------------
	public Account(String accountNumber)
		throws InvalidPrimaryKeyException
	{
		super(myTableName);

		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (AccountNumber = " + accountNumber + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// You must get one account at least
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			// There should be EXACTLY one account. More than that is an error
			if (size != 1)
			{
				throw new InvalidPrimaryKeyException("Multiple accounts matching id : "
					+ accountNumber + " found.");
			}
			else
			{
				// copy all the retrieved data into persistent state
				Properties retrievedAccountData = (Properties)allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedAccountData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedAccountData.getProperty(nextKey);

					if (nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}
		// If no account found for this user name, throw an exception
		else
		{
			throw new InvalidPrimaryKeyException("No account matching id : "
				+ accountNumber + " found.");
		}
	}

	//----------------------------------------------------------
	public Account(Properties props)
	{
		super(myTableName);

		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);

			if (nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("Update", "UpdateStatusMessage");
		dependencies.setProperty("ServiceCharge", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}

	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("UpdateStatusMessage") == true)
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{

		if (key.equals("DisplayView") == true)
		{
			createAndShowView();
		}
		else
		if (key.equals("Update") == true)
		{
			updateStateInDatabase();
		}
		else
		if (key.equals("ServiceCharge") == true)
		{
			persistentState.setProperty(key, (String)value);
			updateStateInDatabase();
		}
		else
		{
			persistentState.setProperty(key, (String)value);
		}

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	/**
	 * Verify ownership
	 */
	//----------------------------------------------------------
	public boolean verifyOwnership(AccountHolder cust)
	{
		if (cust == null)
		{
			return false;
		}
		else
		{
			String custid = (String)cust.getState("ID");
			String myOwnerid = (String)getState("OwnerId");

			return (custid.equals(myOwnerid));
		}
	}

	/**
	 * Credit balance (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
	 */
	//----------------------------------------------------------
	public void credit(String amount)
	{
		String myBalance = (String)getState("Balance");
		double myBal = Double.parseDouble(myBalance);

		double incrementAmount = Double.parseDouble(amount);
		myBal += incrementAmount;

		stateChangeRequest("Balance", ""+myBal);
	}

	/**
	 * Debit balance (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
	 */
	//----------------------------------------------------------
	public void debit(String amount)
	{
		String myBalance = (String)getState("Balance");
		double myBal = Double.parseDouble(myBalance);

		double incrementAmount = Double.parseDouble(amount);
		myBal -= incrementAmount;

		stateChangeRequest("Balance", ""+myBal);
	}

	/**
	 * Check balance -- returns true/false depending on whether
	 * there is enough balance to cover withdrawalAmount or not
	 * (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
	 *
	 */
	//----------------------------------------------------------
	public boolean checkBalance(String withdrawalAmount)
	{
		String myBalance = (String)getState("Balance");
		double myBal = Double.parseDouble(myBalance);

		double checkAmount = Double.parseDouble(withdrawalAmount);

		if (myBal >= checkAmount)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//-----------------------------------------------------------------------------------
	public static int compare(Account a, Account b)
	{
		String aNum = (String)a.getState("AccountNumber");
		String bNum = (String)b.getState("AccountNumber");

		return aNum.compareTo(bNum);
	}

	//-----------------------------------------------------------------------------------
	public void updateStateInDatabase() // should be private? Should this be invoked directly or via the 'sCR(...)' method always?
	{
		try
		{
			if (persistentState.getProperty("AccountNumber") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("AccountNumber",
				persistentState.getProperty("AccountNumber"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Account data for account number : " + persistentState.getProperty("AccountNumber") + " updated successfully in database!";
			}
			else
			{
				Integer accountNumber =
					insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("AccountNumber", "" + accountNumber.intValue());
				updateStatusMessage = "Account data for new account : " +  persistentState.getProperty("AccountNumber")
					+ "installed successfully in database!";
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing account data in database!";
		}
		//DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
	}

	//------------------------------------------------------
	protected void createAndShowView()
	{

		View localView = (View)myViews.get("AccountView");

		if (localView == null)
		{
				// create our initial view
				localView = ViewFactory.createView("AccountView", this);

				// NEEDED FOR IMPRESARIO
				localView.subscribe("ServiceCharge", this);
				localView.subscribe("AccountCancelled", this);

				myViews.put("AccountView", localView);

				// make the view visible by installing it into the frame
				swapToView(localView);
		}
		else
		{
			// make the view visible by installing it into the frame
			swapToView(localView);
		}
	}

	/**
	 * This method is needed solely to enable the Account information to be displayable in a table
	 *
	 */
	//--------------------------------------------------------------------------
	public Vector getEntryListView()
	{
		Vector v = new Vector();

		v.addElement(persistentState.getProperty("AccountNumber"));
		v.addElement(persistentState.getProperty("Type"));
		v.addElement(persistentState.getProperty("Balance"));
		v.addElement(persistentState.getProperty("ServiceCharge"));

		return v;
	}

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
}

