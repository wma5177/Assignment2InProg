package classes.model;

import java.util.Properties;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;
import model.EntityBase;
import impresario.IView;
import exception.InvalidPrimaryKeyException;

public class Patron extends EntityBase implements IView {
    private static final String myTableName = "Patron";
    protected Properties dependencies;
    private String updateStatusMessage = "";

    public Patron (Properties input1) {
        super("Patron");
        this.setDependencies();
        this.persistentState = new Properties();
        Enumeration input2 = input1.propertyNames();

        while(input2.hasMoreElements()){
            String input3 = (String)input2.nextElement();
            String input4 = input1.getProperty(input3);
            if(input4 != null){
                this.persistentState.setProperty(input3, input4);
            }
            String input5 = input1.getProperty(input4);
        }
        //year-month-date 0000-00-00
    }

    public Patron (String patronId) throws InvalidPrimaryKeyException {
        super("Book");
        setDependencies();
        String selection = "SELECT * FROM Patron WHERE (patronId = " + patronId + ")";
        Vector getResult = getSelectQueryResult(selection);
        if(getResult != null) {
            int compareResult = getResult.size();
            if(compareResult != 1){
                throw new InvalidPrimaryKeyException("Multiple accounts matching patronId: " + patronId + " found.");
            } else {
                Properties props = (Properties)getResult.elementAt(0);
                persistentState = new Properties();
                Enumeration propNames = props.propertyNames();

                while(propNames.hasMoreElements()){
                    String nextElement = (String)propNames.nextElement();
                    String nextElementProp = props.getProperty(nextElement);
                    if(nextElementProp != null){
                        persistentState.setProperty(nextElement, nextElementProp);
                    }

                }
            }
        }

    }
//I remember him telling us to comment this out to get to compile, not sure when we uncomment.
    /**
     protected void createAndShowView(){
     View bookView = (View)this.myViews.get("BookView");
     if (bookView == null) {
     bookView = ViewFactory.createView("BookView", this);
     this.myViews.put("BookView", var1);
     this.swapToView(var1);
     } else {
     this.swapToView(var1);
     }
     }
     **/

    public String toString(){
        return "Patron Info: ID: " + persistentState.getProperty("patronId") + " Name: " + persistentState.getProperty("name") +
                " DOB: " + persistentState.getProperty("dateOfBirth");
    }

    private void setDependencies() {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }

    @Override
    public Object getState(String key) {
        return persistentState.getProperty(key);
    }

    @Override
    public void stateChangeRequest(String key, Object value) {

    }

    public void update() {
        updateStateInDatabase();
    }


    /*
    //Same for this, not sure if this is correct since I don't know the exact stage yet...
    public void stateChangeRequest(String givenState, Object setState){
        if (givenState.equals("DisplayView")){
            this.createAndShowView();
        } else if (givenState.equals("Update")){
            this.updateStateInDatabase();
        } else if (givenState.equals("ServiceCharge")) {
            persistentState.setProperty(givenState, (String)setState);
            updateStateInDatabase();
        } else {
            persistentState.setProperty(givenState, (String)setState);
        }

        /**
         Not sure why'd we'd need this method call
         but I copied it over, we may need to replace it with a different method.

         myRegistry.updateSubscribers(givenState, this); */


    private void updateStateInDatabase(){
        try{
            if(persistentState.getProperty("patronId") != null){
                Properties prop = new Properties();
                prop.setProperty("patronId", persistentState.getProperty("patronId"));
                updatePersistentState(mySchema, persistentState, prop);
                updateStatusMessage = "Patron information for patronId: " + persistentState.getProperty("patronId") + " updated successfully in database.";
            }
            else{
                Integer patronId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("patronId", "" + patronId);
                updateStatusMessage = "Patron information for bookId: " + persistentState.getProperty("patronId") + " created successfully in database.";

            }
        } catch (SQLException exp){
            updateStatusMessage = "Error in installing patron data in database.";
        }
    }

    protected void initializeSchema(String input){
        if (mySchema == null){
            mySchema = getSchemaInfo(input);
        }
    }

    @Override
    public void updateState(String key, Object value) {

    }
}
