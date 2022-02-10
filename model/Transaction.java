// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;

import impresario.*;

import userinterface.MainFrame;
import userinterface.View;
import userinterface.WindowPosition;

/** The class containing the Transaction for the ATM application */
//==============================================================
abstract public class Transaction implements IView, IModel, ISlideShow
{

	// For Impresario
	protected Properties dependencies;
	protected ModelRegistry myRegistry;

	protected JFrame myFrame;
	protected Hashtable myViews;

	protected AccountHolder myCust;

	protected Vector myAccountIDs;
	// GUI Components

	/**
	 * Constructor for this class.
	 *
	 * Transaction remembers all the account IDs for this customer.
	 * It uses AccountCatalog to create this list of account IDs.
	 *
	 */
	//----------------------------------------------------------
	protected Transaction(AccountHolder cust) throws Exception
	{

		myFrame = MainFrame.getInstance();
		myViews = new Hashtable();
		myCust = cust;

		myRegistry = new ModelRegistry("Transaction");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Transaction",
				"Could not instantiate Registry", Event.ERROR);
		}
		setDependencies();

	}

	//----------------------------------------------------------
	protected abstract void setDependencies();

	//---------------------------------------------------------
	protected abstract View createView();

	/**
	 * Template method
	 *
	 */
	//---------------------------------------------------------
	protected void doYourJob()
	{
		AccountCatalog catalog = null;

		try
		{
			catalog = new AccountCatalog(myCust);
			myAccountIDs = (Vector)catalog.getState("AccountNumberList");
			View v = createView();

			swapToView(v);

		}
		catch (Exception ex)
		{
				new Event(Event.getLeafLevelClassName(this), "Transaction",
					"Could not find any accounts for " + myCust.getState("ID"), Event.ERROR);
		}
	}

	// forward declarations
	//-----------------------------------------------------------
	public abstract Object getState(String key);

	//-----------------------------------------------------------
	public abstract void stateChangeRequest(String key, Object value);

	/** Called via the IView relationship
	 * Re-define in sub-class, if necessary
	 */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
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

	/**
	 * Create an account (based on account number passed to you from the view)
	 */
	//----------------------------------------------------------
	protected Account createAccount(String accountNumber) throws
		InvalidPrimaryKeyException
	{
		return new Account(accountNumber);
	}

	//----------------------------------------------------------
	public Vector getAccountList()
	{
		return myAccountIDs;
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

