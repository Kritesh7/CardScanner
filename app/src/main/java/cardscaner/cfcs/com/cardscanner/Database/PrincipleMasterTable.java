package cardscaner.cfcs.com.cardscanner.Database;

/**
 * Created by Admin on 12-08-2017.
 */

public class PrincipleMasterTable
{

    public static final String tableName = "PrincipleMasterTable";
    public static final String userId = "UserId";
    public static final String principleId = "PrincipleID";
    public static final String principle = "Principle";


    public static final String PrincipleMasterTableData =
            "create table " + tableName +
                    " (" +
                    userId + " text, " +
                    principleId + " text, " +
                    principle + " text" +
                    ");";
}
