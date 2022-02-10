// tabs=4
//************************************************************
//	COPYRIGHT 2010 Sandeep Mitra and Students, The
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
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// project imports

//==============================================================
public class MessageView extends JPanel
{

	// GUI elements
	private JLabel statusLog;

	private final int statusLogHeight = 30;
	private final int statusLogWidth = 60;


	// Class constructor
	//----------------------------------------------------------
	public MessageView(String initialMessage)
	{
		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(createStatusLog(initialMessage));

	}


	// Overide the paint method to ensure we can set the focus when made visible
	//-------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);
	}



	// Create the composite ID text entry field
	//-------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage)
	{
		JPanel temp = new JPanel();		// default FlowLayout is fine
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));

		statusLog = new JLabel(initialMessage);
		statusLog.setMinimumSize(new Dimension(statusLogHeight, statusLogWidth));
		Font myFont = new Font("Helvetica", Font.BOLD, 16);
		statusLog.setFont(myFont);
		statusLog.setForeground(Color.blue);
  		temp.add(statusLog);

		return temp;
	}

	/**
	 * Display ordinary message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		// display the passed text in red
		statusLog.setForeground(Color.blue);
		statusLog.setText(message);
	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		// display the passed text in red
		statusLog.setForeground(Color.red);
		statusLog.setText(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.setText("                           ");
	}


}



