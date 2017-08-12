package cardscaner.cfcs.com.cardscanner.Database;

/**
 * Created by Admin on 12-08-2017.
 */

public class TitleMasterTable
{
    public static final String tableName = "TitleMaster";
    public static final String titleID = "TitleID";
    public static final String titleName = "TitleName";


    public static final String TitleMasterTableData =
            "create table " + tableName +
                    " (" +
                    titleID + " text, " +
                    titleName + " text" +
                    ");";
}
