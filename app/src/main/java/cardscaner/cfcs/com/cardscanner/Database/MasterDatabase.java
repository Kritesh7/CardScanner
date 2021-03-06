package cardscaner.cfcs.com.cardscanner.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Admin on 12-08-2017.
 */

public class MasterDatabase
{
    public static final int databaseVersion = 1;
    public static final String databaseName = "CARD_SCANNER_DATABASE";
    public SQLiteDatabase sqLiteDatabase;
    public Context context;
    public BusinessVerticalMasterTable businessVerticalMasterTable = new BusinessVerticalMasterTable();
    public IndustrySegmentMasterTable industrySegmentMasterTable = new IndustrySegmentMasterTable();
    public IndustryTypeMasterTable industryTypeMasterTable = new IndustryTypeMasterTable();
    public NumberTypeMasterTable numberTypeMasterTable = new NumberTypeMasterTable();
    public PrincipleMasterTable principleMasterTable = new PrincipleMasterTable();
    public TitleMasterTable titleMasterTable = new TitleMasterTable();
    public ZoneMasterTable zoneMasterTable = new ZoneMasterTable();
    public DataBaseHelper dataBaseHelper;
    public ManagementTypeMasterTable managementTypeMasterTable = new ManagementTypeMasterTable();
    public ContactTypeMasterTable contactTypeMasterTable = new ContactTypeMasterTable();

    public MasterDatabase(Context context)
    {
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context,databaseName,null,databaseVersion);
    }

    //get and set BusinessVerticalMasterTableData
    public void setBusinessVerticalMasterTableData(String businessVerticalID, String userId,String businessVertical)
    {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(businessVerticalMasterTable.businessVerticalID,businessVerticalID);
        contentValues.put(businessVerticalMasterTable.userId,userId);
        contentValues.put(businessVerticalMasterTable.businessVertical,businessVertical);

        sqLiteDatabase.insertWithOnConflict(businessVerticalMasterTable.tableName,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getBusinessVerticalMasterTableData(String userId)
    {
        sqLiteDatabase=dataBaseHelper.getReadableDatabase();
        Cursor cursor=null;
        cursor = sqLiteDatabase.rawQuery("SELECT " + businessVerticalMasterTable.businessVerticalID + ", " + businessVerticalMasterTable.businessVertical +
                " FROM " + businessVerticalMasterTable.tableName + " WHERE " + businessVerticalMasterTable.userId + "=?", new String[]
                {userId});
        return cursor;
    }
    public int getBusinessVerticalMasterTableDataCunt(String userId)
    {

        String countQuery = "SELECT  * FROM " + businessVerticalMasterTable.tableName + " WHERE " + businessVerticalMasterTable.userId + "=" + userId;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //get and set NumberTypeMasterTable
    public void setNumberTypeMasterTable(String userId,String numberType)
    {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(numberTypeMasterTable.userId,userId);
        contentValues.put(numberTypeMasterTable.numberType,numberType);

        sqLiteDatabase.insertWithOnConflict(numberTypeMasterTable.tableName,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getNumberTypeMasterTable(String userId)
    {
        sqLiteDatabase=dataBaseHelper.getReadableDatabase();
        Cursor cursor=null;
        cursor = sqLiteDatabase.rawQuery("SELECT " + numberTypeMasterTable.numberType +
                " FROM " + numberTypeMasterTable.tableName + " WHERE " + numberTypeMasterTable.userId + "=?", new String[]
                {userId});
        return cursor;
    }
    public int getNumberTypeMasterTableCunt(String userId)
    {

        String countQuery = "SELECT  * FROM " + numberTypeMasterTable.tableName + " WHERE " + numberTypeMasterTable.userId + "=" + userId;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //get and set PrincipleMasterTable
    public void setPrincipleMasterTable(String userId,String principleId, String principle)
    {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(principleMasterTable.userId,userId);
        contentValues.put(principleMasterTable.principleId,principleId);
        contentValues.put(principleMasterTable.principle,principle);

        sqLiteDatabase.insertWithOnConflict(principleMasterTable.tableName,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getPrincipleMasterTable(String userId)
    {
        sqLiteDatabase=dataBaseHelper.getReadableDatabase();
        Cursor cursor=null;
        cursor = sqLiteDatabase.rawQuery("SELECT " + principleMasterTable.principle + ", " + principleMasterTable.principleId +
                " FROM " + principleMasterTable.tableName + " WHERE " + principleMasterTable.userId + "=?", new String[]
                {userId});
        return cursor;
    }
    public int getPrincipleMasterTableCunt(String userId)
    {

        String countQuery = "SELECT  * FROM " + principleMasterTable.tableName + " WHERE " + principleMasterTable.userId + "=" + userId;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //get and set IndustryTypeMaster
    public void setIndustryTypeMasterTable(String userId,String industryTypeID, String industryType)
    {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(industryTypeMasterTable.userId,userId);
        contentValues.put(industryTypeMasterTable.industryTypeID,industryTypeID);
        contentValues.put(industryTypeMasterTable.industryType,industryType);

        sqLiteDatabase.insertWithOnConflict(industryTypeMasterTable.tableName,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getIndustryTypeMasterTable(String userId)
    {
        sqLiteDatabase=dataBaseHelper.getReadableDatabase();
        Cursor cursor=null;
        cursor = sqLiteDatabase.rawQuery("SELECT " + industryTypeMasterTable.industryTypeID + ", " + industryTypeMasterTable.industryType +
                " FROM " + industryTypeMasterTable.tableName + " WHERE " + industryTypeMasterTable.userId + "=?", new String[]
                {userId});
        return cursor;
    }
    public int getIndustryTypeMasterTableCunt(String userId)
    {

        String countQuery = "SELECT  * FROM " + industryTypeMasterTable.tableName + " WHERE " + industryTypeMasterTable.userId + "=" + userId;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //get and set IndustrySegmentMasterTable
    public void setIndustrySegmentMasterTable(String userId,String industrySegmentID, String industrySegment)
    {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(industrySegmentMasterTable.userId,userId);
        contentValues.put(industrySegmentMasterTable.industrySegmentID,industrySegmentID);
        contentValues.put(industrySegmentMasterTable.industrySegment,industrySegment);

        sqLiteDatabase.insertWithOnConflict(industrySegmentMasterTable.tableName,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getIndustrySegmentMasterTable(String userId)
    {
        sqLiteDatabase=dataBaseHelper.getReadableDatabase();
        Cursor cursor=null;
        cursor = sqLiteDatabase.rawQuery("SELECT " + industrySegmentMasterTable.industrySegmentID + ", " + industrySegmentMasterTable.industrySegment +
                " FROM " + industrySegmentMasterTable.tableName + " WHERE " + industrySegmentMasterTable.userId + "=?", new String[]
                {userId});
        return cursor;
    }
    public int getIndustrySegmentMasterTableCunt(String userId)
    {

        String countQuery = "SELECT  * FROM " + industrySegmentMasterTable.tableName + " WHERE " + industrySegmentMasterTable.userId + "=" + userId;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //zonemaster Table
    //get and set IndustrySegmentMasterTable
    public void setZoneMasterTable(String userId,String zoneID, String zoneName)
    {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ZoneMasterTable.userId,userId);
        contentValues.put(ZoneMasterTable.zoneID,zoneID);
        contentValues.put(ZoneMasterTable.zoneName,zoneName);

        sqLiteDatabase.insertWithOnConflict(ZoneMasterTable.tableName,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getZoneMasterTable(String userId)
    {
        sqLiteDatabase=dataBaseHelper.getReadableDatabase();
        Cursor cursor=null;
        cursor = sqLiteDatabase.rawQuery("SELECT " + ZoneMasterTable.zoneID + ", " + ZoneMasterTable.zoneName +
                " FROM " + ZoneMasterTable.tableName + " WHERE " + ZoneMasterTable.userId + "=?", new String[]
                {userId});
        return cursor;
    }
    public int getZoneMasterTableCunt(String userId)
    {

        String countQuery = "SELECT  * FROM " + ZoneMasterTable.tableName + " WHERE " + ZoneMasterTable.userId + "=" + userId;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //get and set ManagmentType
    public void setManageMentTypeTable(String userId,String managementTypeId, String managementType)
    {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ManagementTypeMasterTable.userId,userId);
        contentValues.put(ManagementTypeMasterTable.managementTypeID,managementTypeId);
        contentValues.put(ManagementTypeMasterTable.managementType,managementType);

        sqLiteDatabase.insertWithOnConflict(ManagementTypeMasterTable.tableName,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getManagementTypeTable(String userId)
    {
        sqLiteDatabase=dataBaseHelper.getReadableDatabase();
        Cursor cursor=null;
        cursor = sqLiteDatabase.rawQuery("SELECT " + ManagementTypeMasterTable.managementTypeID + ", " + ManagementTypeMasterTable.managementType +
                " FROM " + ManagementTypeMasterTable.tableName + " WHERE " + ManagementTypeMasterTable.userId + "=?", new String[]
                {userId});
        return cursor;
    }
   /* public int getZoneMasterTableCunt(String userId)
    {

        String countQuery = "SELECT  * FROM " + ZoneMasterTable.tableName + " WHERE " + ZoneMasterTable.userId + "=" + userId;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }*/

    //get and set ContactType
    public void setContactTypeTable(String userId,String contactTypeId, String contactType)
    {
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ContactTypeMasterTable.userId,userId);
        contentValues.put(ContactTypeMasterTable.contactTypeID,contactTypeId);
        contentValues.put(ContactTypeMasterTable.contactType,contactType);

        sqLiteDatabase.insertWithOnConflict(ContactTypeMasterTable.tableName,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getContactTypeMasterTable(String userId)
    {
        sqLiteDatabase=dataBaseHelper.getReadableDatabase();
        Cursor cursor=null;
        cursor = sqLiteDatabase.rawQuery("SELECT " + ContactTypeMasterTable.contactTypeID + ", " + ContactTypeMasterTable.contactType +
                " FROM " + ContactTypeMasterTable.tableName + " WHERE " + ContactTypeMasterTable.userId + "=?", new String[]
                {userId});
        return cursor;
    }

}
