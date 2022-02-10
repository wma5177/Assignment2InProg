package model;

import java.util.Properties;
import java.util.Vector;

public class PatronCollection extends EntityBase{
    private static final String myTableName="Patron";
    private Vector<Patron> patrons;

    public PatronCollection()
    {
        super(myTableName);
        patrons = new Vector<Patron>();
    }

    public void findPatronsOlderThan(String date) throws Exception{
        if(date == null){
            throw new Exception("UNEXPECTED ERROR: date is null");
        }

        String query = "SELECT * FROM "+ myTableName+ " WHERE (dateOfBirth < '"+date+"')";

        queryHelper(query);
    }
    public void findPatronsYoungerThan(String date)throws Exception{
        if(date == null){
            throw new Exception("UNEXPECTED ERROR: date is null");
        }

        String query = "SELECT * FROM "+ myTableName + " WHERE (dateOfBirth > '"+date+"')";

        queryHelper(query);
    }
    public void findPatronsAtZipCode(String zip)throws Exception{
        if(zip == null){
            throw new Exception("UNEXPECTED ERROR: zip is null");
        }

        String query = "SELECT * FROM "+ myTableName+ " WHERE (zip = "+zip+")";

        queryHelper(query);
    }
    public void findPatronsWithNameLike(String name)throws Exception{
        if(name == null){
            throw new Exception("UNEXPECTED ERROR: name is null");
        }

        String query = "SELECT * FROM "+ myTableName+ " WHERE (name LIKE '%"+name+"%')";

        queryHelper(query);
    }

    public void queryHelper(String query){
        patrons = new Vector();

        Vector allDataRetrieved = getSelectQueryResult(query);

        for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
        {
            Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);

            Patron patron = new Patron(nextPatronData);

            if (patrons != null)
            {
                addPatron(patron);
            }
        }



    }

    //----------------------------------------------------------------------------------
    private void addPatron(Patron a)
    {
        //users.add(u);
        int index = findIndexToAdd(a);
        patrons.insertElementAt(a,index); // To build up a collection sorted on some key
    }

    //----------------------------------------------------------------------------------
    private int findIndexToAdd(Patron a)
    {
        //users.add(u);
        int low=0;
        int high = patrons.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Patron midSession = (Patron)patrons.elementAt(middle);

            int result = Patron.compare(a,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }


    public Object getState(String key) {
        return null;
    }

    public void stateChangeRequest(String key, Object value) {
    }

    public void display() {
        if (patrons != null) {
            for (int cnt = 0; cnt < patrons.size(); cnt++) {
                Patron p = patrons.get(cnt);
                System.out.println("--------");
                System.out.println(p.toString());
            }
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