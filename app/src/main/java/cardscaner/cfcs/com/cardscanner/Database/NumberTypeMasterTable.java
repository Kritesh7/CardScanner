package cardscaner.cfcs.com.cardscanner.Database;

/**
 * Created by Admin on 12-08-2017.
 */

public class NumberTypeMasterTable
{

    public static final String tableName = "NumberTypeMaster";
    public static final String userId = "UserId";
    public static final String numberType = "NumberType";



    public static final String NumberTypeMasterTableData =
            "create table " + tableName +
                    " (" +
                    userId + " text, " +
                    numberType + " text" +
                    ");";
}
