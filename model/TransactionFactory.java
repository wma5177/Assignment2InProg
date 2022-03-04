// specify the package
package model;

// system imports
import java.util.Vector;
import javax.swing.JFrame;

// project imports

/** The class containing the TransactionFactory for the ATM application */
//==============================================================
public class TransactionFactory
{

	/**
	 *
	 */
	//----------------------------------------------------------
	public static Transaction createTransaction(String transType)
		throws Exception
	{
		Transaction retValue = null;

	if (transType.equals("InsertNewBook")) {
		//retValue = new InsertbookTransaction();
	}
	else if(transType.equals("InsertNewPatron")) {
		//retValue = new InsertPatronTransaction();
	}
	else if(transType.equals("SearchBooks")){
		//retValue = new SearchBookTransaction();
	}
	else if(transType.equals("SearchPatrons")){
		//retValue = new SearchPatronTransaction();
	}

		return retValue;
	}
}
