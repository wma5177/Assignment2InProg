package model;

import exception.InvalidPrimaryKeyException;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


public class Patron extends EntityBase {
    private static final String myTableName = "Patron";

    protected Properties dependencies;

    private String updateStatusMessage = "";

    //Constructor for if we know the id number which is a private key so there should only be one unique id of that number
    public Patron(String id) throws InvalidPrimaryKeyException
    {
        super(myTableName);
        String query = "SELECT * FROM " + myTableName + " WHERE (patronID = " + id + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null){
            int size = allDataRetrieved.size();

            // There should be EXACTLY one patron. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple patrons matching id : "
                        + id + " found.");
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
        // If no patron found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No patron matching id : "
                    + id + " found.");
        }
    }

    //Constructor for if we do not know the specific id number to look up
    public Patron(Properties props)
    {
        super(myTableName);

        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while(allKeys.hasMoreElements()==true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if(nextValue!=null)
            {
                persistentState.setProperty(nextKey,nextValue);
            }
        }
    }

    public void save(){
        updateStateInDatabase();
    }

    public void updateStateInDatabase(){
        try
        {
            if(persistentState.getProperty("patronID") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("patronID",
                        persistentState.getProperty("patronID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Patron data for Patron Id : " + persistentState.getProperty("patronID") + " updated successfully in database!";
            }
            else
            {
                Integer patronId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("patronID", "" + patronId.intValue());
                updateStatusMessage = "Patron data for new patron : " +  persistentState.getProperty("AccountNumber")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing patron data in database!";
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Patron Name: " + persistentState.getProperty("name") + "; Address: " +
                persistentState.getProperty("address")  + "; City: " +
                persistentState.getProperty("city") + "; Zip: "+
                persistentState.getProperty("zip")+ "; Email: "+ persistentState.getProperty("email")
                +"; DateOfBirth: "+ persistentState.getProperty("dateOfBirth");
    }

    // compare
    public void display(){
        System.out.println(toString());
    }


    public static int compare(Patron a, Patron b)
    {
        String aNum = (String)a.getState("patronID");
        String bNum = (String)b.getState("patronID");

        return aNum.compareTo(bNum);
    }

    public Object getState(String key) {
        return persistentState.getProperty(key);
    }

    public void stateChangeRequest(String key, Object value) {
        if (value != null) {
            persistentState.setProperty(key, (String)value);
        }
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