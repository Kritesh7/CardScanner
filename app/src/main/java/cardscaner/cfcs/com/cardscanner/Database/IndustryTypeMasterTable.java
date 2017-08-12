package cardscaner.cfcs.com.cardscanner.Database;

/**
 * Created by Admin on 12-08-2017.
 */

public class IndustryTypeMasterTable
{
    public static final String tableName = "IndustryTypeMaster";
    public static final String userId = "UserId";
    public static final String industryTypeID = "IndustryTypeID";
    public static final String industryType = "IndustryType";


    public static final String IndustryTypeMasterTableData =
            "create table " + tableName +
                    " (" +
                    userId + " text, " +
                    industryTypeID + " text, " +
                    industryType + " text" +
                    ");";
}
