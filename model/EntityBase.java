// tabs=4
//************************************************************
//	COPYRIGHT 2009 Sandeep Mitra and Michael Steves, The
//    College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package model;

// system imports
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;

// project imports
import database.Persistable;
import impresario.ModelRegistry;
import impresario.IModel;
import impresario.IView;
import impresario.ISlideShow;
import event.Event;
import userinterface.MainFrame;
import userinterface.WindowPosition;


/** The superclass for all Fast Trax Model Entities that are also
 *  Persistable */
//==============================================================
public abstract class EntityBase extends Persistable
	implements IModel, ISlideShow
{
	protected ModelRegistry myRegistry;	// registry for entities interested in our events
	private int referenceCount;		// the number of others using us
	protected boolean dirty;		// true if the data has changed
	protected Properties persistentState;	// the field names and values from the database
	private String myTableName;				// the name of our database table

	protected Hashtable myViews;
	protected JFrame myFrame;

	protected Properties mySchema;

	// forward declarations
	public abstract Object getState(String key);
	public abstract void stateChangeRequest(String key, Object value);
	protected abstract void initializeSchema(String tableName);

	// constructor for this class
	//----------------------------------------------------------
	protected EntityBase(String tablename)
	{
		myFrame = MainFrame.getInstance();
		myViews = new Hashtable();

		// save our table name for later
		myTableName = tablename;

		// extract the schema from the database, calls methods in subclasses
		initializeSchema(myTableName);

		// create a place to hold our state from the database
		persistentState = new Properties();

		// create a registry for subscribers
		myRegistry = new ModelRegistry("EntityBase." + tablename);	// for now

		// initialize the reference count
		referenceCount = 0;
		// indicate the data in persistentState matches the database contents
		dirty = false;
    }

	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("EntityBase[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("EntityBase.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}


    //-----------------------------------------------------------------------------------
    // package level permission, only ObjectFactory should modify
    void incrementReferenceCount()
    {
		referenceCount++;
    }

    //-----------------------------------------------------------------------------------
    // package level permission, only ObjectFactory should modify
    void decrementReferenceCount()
    {
		referenceCount--;
    }

    //-----------------------------------------------------------------------------------
    // package level permission, only ObjectFactory should modify
    int getReferenceCount()
    {
		return referenceCount;
    }

	//-----------------------------------------------------------------------------------
    // package level permission, only ObjectFactory and others in same package should invoke
    void releaseAggregates()
    {
    }

	//----------------------------------------------------------
	protected String convertToDefaultDateFormat(Date theDate)
	{

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

		String valToReturn = formatter.format(theDate);

		return valToReturn;

	}

	//----------------------------------------------------------
	protected String convertDateStringToDefaultDateFormat(String dateStr)
	{

		Date theDate = validateDateString(dateStr);

		if (theDate == null)
		{
			return null;
		}
		else
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

			String valToReturn = formatter.format(theDate);

			return valToReturn;
		}
	}

	//----------------------------------------------------------
	protected Date validateDateString(String str)
	{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

			Date theDate = null;

			try
			{
				theDate = formatter.parse(str);
				return theDate;
			}
			catch (ParseException ex)
			{
				SimpleDateFormat formatter2 =
					new SimpleDateFormat("yyyy-MM-dd");

				try
				{
					theDate = formatter2.parse(str);
					return theDate;
				}
				catch (ParseException ex2)
				{
					SimpleDateFormat formatter3 =
						new SimpleDateFormat("yyyy/MMdd");

					try
					{
						theDate = formatter3.parse(str);
						return theDate;
					}
					catch (ParseException ex3)
					{
						SimpleDateFormat formatter4 =
							new SimpleDateFormat("yyyyMM/dd");

						try
						{
							theDate = formatter4.parse(str);
							return theDate;
						}
						catch (ParseException ex4)
						{
							return null;
						}
					}
				}
			}
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
			JPanel currentView = (JPanel)myFrame.getContentPane().getComponent(0);
			// and remove it
			myFrame.getContentPane().remove(currentView);
			// add our view
			myFrame.getContentPane().add((JPanel)otherView);
			//pack the frame and show it
			myFrame.pack();
			//Place in center
			WindowPosition.placeCenter(myFrame);
		}//end of SwapToView
		else
		{
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Non-displayable view object sent ", Event.ERROR);
		}
	}

}

