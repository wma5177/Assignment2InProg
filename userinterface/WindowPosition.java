// tabs=4
//************************************************************
//	COPYRIGHT ArchSynergy, Ltd. 2003 - ALL RIGHTS RESERVED
//
// This file is the product of ArchSynergy, Ltd. and is 
// provided for unrestricted use provided that this legend 
// is included on all tape media and as part of the software 
// program in whole or part. Users may copy or modify this 
// file without charge, but are not authorized to license or 
// distribute it to anyone else except as part of a product or 
// program developed by the user.
//*************************************************************

// Specify the package
package userinterface;

// system imports
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Component;
import java.awt.Toolkit;

// project imports

/** 
* Provides a means of placing top-level components ONLY at
* specified locations on the full screen 
*/
//==============================================================
public class WindowPosition 
{
	public static Point center = getCenter();

	/**
	* Returns the coordinates of the center of the screen
	*
	* @return Point object indicating the coordinates of the center of the screen
	*
	*/
	//--------------------------------------------------------------------------
	public static Point getCenter() 
	{
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		// DEBUG System.out.println("Screen width obtained as "+d.width+" pixels and height as "+d.height+" pixels");
		return new Point(d.width/2, d.height/2);
	}

	/**
	* Used to place a component so that the center of the component is at the center of the screen
	*
	* @param	c	Component to place at the center of the screen
	*
	*/
	//--------------------------------------------------------------------------
	public static void placeCenter(Component c) 
	{
		if (c != null) 
		{
			Dimension d = c.getSize();
			// DEBUG System.out.println("Component's top left corner at x = "+(center.x-d.width/2)+" and y = "+(center.y-d.height/2));
			Point loc = new Point(center.x - d.width/2, center.y - d.height/2);
			c.setLocation(loc);
		}
	}
  
	/**
	* Used to place a component so that the top left corner of the component is at the top left corner of te screen
	*
	* @param	c	Component to place at the top left corner of the screen
	*
	*/
	//--------------------------------------------------------------------------
	public static void placeTopLeft(Component c) 
	{
		if (c != null) 
		{
			Dimension d = c.getSize();
			Point loc = new Point(0, 0);
			c.setLocation(loc);
		}
	}

	/**
	* Used to place a component so that the top right corner of the component is at the top right corner of the screen
	*
	* @param	c	Component to place at the top right corner of the screen
	*
	*/
	//--------------------------------------------------------------------------
	public static void placeTopRight(Component c) 
	{
		if (c != null) 
		{
			Dimension d = c.getSize();
			Point loc = new Point(2*center.x - d.width, 0);
			c.setLocation(loc);
		}
	}

	/**
	* Used to place a component so that the bottom left corner of the component is at the bottom left corner of te screen
	*
	* @param	c	Component to place at the bottom left corner of the screen
	*
	*/
	//--------------------------------------------------------------------------
	public static void placeBottomLeft(Component c) 
	{
		if (c != null) 
		{
			Dimension d = c.getSize();
			Point loc = new Point(0, 2*center.y - d.height);
			c.setLocation(loc);
		}
	}

	/**
	* Used to place a component so that the bottom right corner of the component is at the bottom right corner of the screen
	*
	* @param	c	Component to place at the bottom right corner of the screen
	*
	*/
	//--------------------------------------------------------------------------
	public static void placeBottomRight(Component c) 
	{
		if (c != null) 
		{
			Dimension d = c.getSize();
			Point loc = new Point(2*center.x - d.width, 2*center.y - d.height);
			c.setLocation(loc);
		}
	}
}


//---------------------------------------------------------------
//	Revision History:
//
//	$Log: WindowPosition.java,v $
//	Revision 1.1  2004/06/21 04:27:12  smitra
//	First check-in
//	
//	Revision 1.1  2003/09/14 23:25:39  tomb
//	Brought over from EasyVideo test GUI.
//	
//	Revision 1.1  2003/08/08 05:26:19  smitra
//	First check-in
//	

