package cardscaner.cfcs.com.cardscanner.Database;

/**
 * Created by Admin on 22-08-2017.
 */

public class ManagementTypeMasterTable
{
    public static final String tableName = "ManagementTypeMasterTable";
    public static final String managementTypeID = "ManagementTypeID";
    public static final String managementType = "ManagementType";
    public static final String userId = "UserId";


    public static final String ManagementTypeMasterTableData =
            "create table " + tableName +
                    " (" +
                    managementTypeID + " text, " +
                    userId + " text, " +
                    managementType + " text" +
                    ");";
}
