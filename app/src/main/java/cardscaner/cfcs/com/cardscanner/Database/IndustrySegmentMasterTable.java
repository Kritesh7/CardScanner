package cardscaner.cfcs.com.cardscanner.Database;

/**
 * Created by Admin on 12-08-2017.
 */

public class IndustrySegmentMasterTable
{
    public static final String tableName = "IndustrySegmentMaster";
    public static final String userId = "UserId";
    public static final String industrySegmentID = "IndustrySegmentID";
    public static final String industrySegment = "IndustrySegment";


    public static final String IndustrySegmentMasterTableData =
            "create table " + tableName +
                    " (" +
                    userId + " text, " +
                    industrySegmentID + " text, " +
                    industrySegment + " text" +
                    ");";
}
