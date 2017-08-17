package cardscaner.cfcs.com.cardscanner.Database;

/**
 * Created by Admin on 12-08-2017.
 */

public class ZoneMasterTable
{
    public static final String tableName = "ZoneMaster";
    public static final String userId = "UserId";
    public static final String zoneID = "ZoneID";
    public static final String zoneName = "ZoneName";


    public static final String ZoneMasterTableData =
            "create table " + tableName +
                    " (" +
                    userId + " text, " +
                    zoneID + " text, " +
                    zoneName + " text" +
                    ");";
}
