package cardscaner.cfcs.com.cardscanner.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 12-08-2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public BusinessVerticalMasterTable businessVerticalMasterTable = new BusinessVerticalMasterTable();
    public IndustrySegmentMasterTable industrySegmentMasterTable = new IndustrySegmentMasterTable();
    public IndustryTypeMasterTable industryTypeMasterTable = new IndustryTypeMasterTable();
    public NumberTypeMasterTable numberTypeMasterTable = new NumberTypeMasterTable();
    public PrincipleMasterTable principleMasterTable = new PrincipleMasterTable();
    public TitleMasterTable titleMasterTable = new TitleMasterTable();
    public ZoneMasterTable zoneMasterTable = new ZoneMasterTable();

    public DataBaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory cursorFactory, int dataBaseVersion)
    {
        super(context,databaseName,cursorFactory,dataBaseVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(businessVerticalMasterTable.BusinessVerticalMasterTableData);
        sqLiteDatabase.execSQL(industrySegmentMasterTable.IndustrySegmentMasterTableData);
        sqLiteDatabase.execSQL(industryTypeMasterTable.IndustryTypeMasterTableData);
        sqLiteDatabase.execSQL(numberTypeMasterTable.NumberTypeMasterTableData);
        sqLiteDatabase.execSQL(principleMasterTable.PrincipleMasterTableData);
        sqLiteDatabase.execSQL(titleMasterTable.TitleMasterTableData);
        sqLiteDatabase.execSQL(zoneMasterTable.ZoneMasterTableData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + businessVerticalMasterTable.tableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + industrySegmentMasterTable.tableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + industryTypeMasterTable.tableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + numberTypeMasterTable.tableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + principleMasterTable.tableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + titleMasterTable.tableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + zoneMasterTable.tableName);
    }
}
