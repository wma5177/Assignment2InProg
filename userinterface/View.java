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
package userinterface;

// system imports
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.EventObject;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

// project imports
import common.StringList;
import impresario.IView;
import impresario.IModel;
import impresario.IControl;
import impresario.ControlRegistry;

//==============================================================
public abstract class View extends JPanel
	implements IView, IControl, ActionListener, FocusListener
{
	// private data
	protected IModel myModel;
	protected ControlRegistry myRegistry;
	
	// forward declaration
	protected abstract void processAction(EventObject evt);
	
	// GUI components
	
		
	// Class constructor
	//----------------------------------------------------------
	public View(IModel model, String classname)
	{
		myModel = model;
		
		myRegistry = new ControlRegistry(classname);
	}


	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void actionPerformed(ActionEvent evt) 
	{
		// DEBUG: System.out.println("View.actionPerformed(): " + evt.toString());

		processAction(evt);
	}
	
	// Same as hitting return in a field, fire postStateChange
	//----------------------------------------------------------
	public void focusLost(FocusEvent evt)
	{
		// DEBUG: System.out.println("CustomerView.focusLost()");
		// ignore temporary events
		if(evt.isTemporary() == true)
			return;	
			
		processAction(evt);
	}
	
	
	// ignore gaining focus, we don't care
	//----------------------------------------------------------
	public void focusGained(FocusEvent evt)
	{
		// placeholder
	}
	
	//----------------------------------------------------------
	public void setRegistry(ControlRegistry registry)
	{
		myRegistry = registry;
	}
	
	// Allow models to register for state updates
	//----------------------------------------------------------
	public void subscribe(String key,  IModel subscriber)
	{
		myRegistry.subscribe(key, subscriber);
	}
		
		
	// Allow models to unregister for state updates
	//----------------------------------------------------------
	public void unSubscribe(String key, IModel subscriber)
	{
		myRegistry.unSubscribe(key, subscriber);
	}

	//----------------------------------------------------------
	protected String mapMonthToString(int month)
	{
		if (month == Calendar.JANUARY)
		{
			return "January";
		}
		else
		if (month == Calendar.FEBRUARY)
		{
			return "February";
		}
		else
		if (month == Calendar.MARCH)
		{
			return "March";
		}
		else
		if (month == Calendar.APRIL)
		{
			return "April";
		}
		else
		if (month == Calendar.MAY)
		{
			return "May";
		}
		else
		if (month == Calendar.JUNE)
		{
			return "June";
		}
		else
		if (month == Calendar.JULY)
		{
			return "July";
		}
		else
		if (month == Calendar.AUGUST)
		{
			return "August";
		}
		else
		if (month == Calendar.SEPTEMBER)
		{
			return "September";
		}
		else
		if (month == Calendar.OCTOBER)
		{
			return "October";
		}
		else
		if (month == Calendar.NOVEMBER)
		{
			return "November";
		}
		else
		if (month == Calendar.DECEMBER)
		{
			return "December";
		}
		
		return "";
	}

	//----------------------------------------------------------
	protected int mapMonthNameToIndex(String monthName)
	{
		if (monthName.equals("January") == true)
		{
			return Calendar.JANUARY;
		}
		else
		if (monthName.equals("February") == true)
		{
			return Calendar.FEBRUARY;
		}
		else
		if (monthName.equals("March") == true)
		{
			return Calendar.MARCH;
		}
		else
		if (monthName.equals("April") == true)
		{
			return Calendar.APRIL;
		}
		else
		if (monthName.equals("May") == true)
		{
			return Calendar.MAY;
		}
		else
		if (monthName.equals("June") == true)
		{
			return Calendar.JUNE;
		}
		else
		if (monthName.equals("July") == true)
		{
			return Calendar.JULY;
		}
		else
		if (monthName.equals("August") == true)
		{
			return Calendar.AUGUST;
		}
		else
		if (monthName.equals("September") == true)
		{
			return Calendar.SEPTEMBER;
		}
		else
		if (monthName.equals("October") == true)
		{
			return Calendar.OCTOBER;
		}
		else
		if (monthName.equals("November") == true)
		{
			return Calendar.NOVEMBER;
		}
		else
		if (monthName.equals("December") == true)
		{
			return Calendar.DECEMBER;
		}
		
		return -1;
	}
	
	
	//----------------------------------------------------
   	protected boolean checkProperLetters(String value)
   	{
   		for (int cnt = 0; cnt < value.length(); cnt++)
   		{
   			char ch = value.charAt(cnt);
   			
   			if ((ch >= 'A') && (ch <= 'Z') || (ch >= 'a') && (ch <= 'z'))
   			{
   			}
   			else
   			if ((ch == '-') || (ch == ',') || (ch == '.') || (ch == ' '))
   			{
   			}
   			else
   			{
   				return false;
   			}
   		}
   		
   		return true;
   	}
   	
   	//----------------------------------------------------
   	protected boolean checkProperPhoneNumber(String value)
   	{
   		if ((value == null) || (value.length() < 7))
   		{
   			return false;
   		}
   		
   		for (int cnt = 0; cnt < value.length(); cnt++)
   		{
   			char ch = value.charAt(cnt);
   			
   			if ((ch >= '0') && (ch <= '9'))
   			{
   			}
   			else
   			if ((ch == '-') || (ch == '(') || (ch == ')') || (ch == ' '))
   			{
   			}
   			else
   			{
   				return false;
   			}
   		}
   		
   		return true;
   	}
   	
   	//----------------------------------------------------------
	protected String convertToDefaultDateFormat(Date theDate)
	{
	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	
		String valToReturn = formatter.format(theDate);	
		
		return valToReturn;
		
	}
}

