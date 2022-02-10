package userinterface;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

//==============================================================================
public class AccountTableModel extends AbstractTableModel implements TableModel
{
	private Vector myState;

	public AccountTableModel(Vector accountData)
	{
		myState = accountData;
	}

	//--------------------------------------------------------------------------
	public int getColumnCount()
	{
		return 4;
	}

	//--------------------------------------------------------------------------
	public int getRowCount()
	{
		return myState.size();
	}

	//--------------------------------------------------------------------------
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Vector account = (Vector)myState.elementAt(rowIndex);
		return "    " + account.elementAt(columnIndex);
	}

	//--------------------------------------------------------------------------
	public String getColumnName(int columnIndex)
	{
		if(columnIndex == 0)
			return "Account Number";
		else
		if(columnIndex == 1)
			return "Type";
		else
		if(columnIndex == 2)
			return "Balance";
		else
		if(columnIndex == 3)
			return "Service Charge";
		else
			return "??";
	}
}
