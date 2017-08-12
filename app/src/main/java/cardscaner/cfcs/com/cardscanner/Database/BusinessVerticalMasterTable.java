package cardscaner.cfcs.com.cardscanner.Database;

/**
 * Created by Admin on 12-08-2017.
 */

public class BusinessVerticalMasterTable
{
    public static final String tableName = "BusinessVerticalMasterTable";
    public static final String businessVerticalID = "BusinessVerticalID";
    public static final String businessVertical = "BusinessVertical";
    public static final String userId = "UserId";


    public static final String BusinessVerticalMasterTableData =
            "create table " + tableName +
                    " (" +
                    businessVerticalID + " text, " +
                    userId + " text, " +
                    businessVertical + " text" +
                    ");";
}
