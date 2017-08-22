package cardscaner.cfcs.com.cardscanner.Database;

/**
 * Created by Admin on 22-08-2017.
 */

public class ContactTypeMasterTable
{
    public static final String tableName = "ContactTypeMasterTable";
    public static final String contactTypeID = "ContactTypeID";
    public static final String contactType = "ContactType";
    public static final String userId = "UserId";


    public static final String ContactTypeMasterTableData =
            "create table " + tableName +
                    " (" +
                    contactTypeID + " text, " +
                    userId + " text, " +
                    contactType + " text" +
                    ");";

}
