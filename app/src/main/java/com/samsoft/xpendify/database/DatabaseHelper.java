package com.samsoft.xpendify.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.samsoft.xpendify.model.AccountData;
import com.samsoft.xpendify.model.BillsData;
import com.samsoft.xpendify.model.BudgetData;
import com.samsoft.xpendify.model.CategoryData;
import com.samsoft.xpendify.model.PatternData;
import com.samsoft.xpendify.model.SummaryData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fred on 23-Aug-15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "XPENDIFY";

    private static final String KEY_PASSCODE_ID = "id";
    private static final String KEY_PASSCODE = "passcode";

    private static final String TABLE_PASSCODE = "passcode";
    private static final String TABLE_EXPENSE = "expense";
    private static final String TABLE_INCOME = "income";
    private static final String TABLE_BUDGET = "budget";
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_ACCOUNT = "account";
    private static final String TABLE_BILLS = "bills";

    private static final String KEY_CATEGORY_ID = "id";
    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_CATEGORY_TYPE = "category_type";
    private static final String KEY_CATEGORY_COLOR = "category_color";

    private static final String KEY_BUDGET_ID = "id";
    private static final String KEY_BUDGET_AMOUNT = "budget_amount";
    private static final String KEY_BUDGET_CATEGORY = "budget_category";
    private static final String KEY_BUDGET_DATE = "budget_date";

    private static final String KEY_EXPENSE_ID = "id";
    private static final String KEY_SPENT_AMOUNT = "spent_amount";
    private static final String KEY_EXPENSE_CATEGORY = "expense_category";
    private static final String KEY_EXPENSE_DATE = "expense_date";
    private static final String KEY_EXPENSE_NOTE = "expense_note";

    private static final String KEY_INCOME_ID = "id";
    private static final String KEY_COLLECTED_AMOUNT = "collected_amount";
    private static final String KEY_INCOME_CATEGORY = "income_category";
    private static final String KEY_INCOME_DATE = "income_date";
    private static final String KEY_INCOME_NOTE = "income_note";

    private static final String KEY_ACCOUNT_ID = "id";
    private static final String KEY_ACCOUNT_BALANCE = "balance";
    private static final String KEY_CUSTOMER_BANK = "customer_bank";
    private static final String KEY_ACCOUNT_NUMBER = "account_number";
    private static final String KEY_BANK_ACCOUNT_TYPE = "bank_account_type";

    private static final String KEY_BILLS_ID = "id";
    private static final String KEY_BILLS_CATEGORY = "bills_category";
    private static final String KEY_BILLS_AMOUNT = "bills_amount";
    private static final String KEY_BILLS_DUE_DATE = "due_date";
    private static final String KEY_BILLS_STATUS = "bills_status";
    private static final String KEY_BILLS_NOTE = "bills_note";

    private static final String CREATE_TABLE_PASSCODE = "CREATE TABLE " + TABLE_PASSCODE + "(" + KEY_PASSCODE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PASSCODE + " TEXT NOT NULL" + ")";
    private static final String CREATE_TABLE_BUDGET = "CREATE TABLE " + TABLE_BUDGET + "(" + KEY_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BUDGET_AMOUNT + " TEXT NOT NULL," + KEY_BUDGET_CATEGORY + " TEXT NOT NULL," + KEY_BUDGET_DATE + " TEXT NOT NULL" + ")";
    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE + "(" + KEY_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SPENT_AMOUNT + " TEXT NOT NULL," + KEY_EXPENSE_CATEGORY + " TEXT NOT NULL," + KEY_EXPENSE_DATE + " TEXT NOT NULL," + KEY_EXPENSE_NOTE + " TEXT" + ")";
    private static final String CREATE_TABLE_INCOME = "CREATE TABLE " + TABLE_INCOME + "(" + KEY_INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_COLLECTED_AMOUNT + " TEXT NOT NULL," + KEY_INCOME_CATEGORY + " TEXT NOT NULL," + KEY_INCOME_DATE + " TEXT NOT NULL," + KEY_INCOME_NOTE + " TEXT" + ")";
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "(" + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CATEGORY_NAME + " TEXT NOT NULL," + KEY_CATEGORY_TYPE + " TEXT NOT NULL," + KEY_CATEGORY_COLOR + " TEXT" + ")";
    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_ACCOUNT + "(" + KEY_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ACCOUNT_BALANCE + " TEXT NOT NULL," + KEY_CUSTOMER_BANK + " TEXT NOT NULL," + KEY_ACCOUNT_NUMBER + " TEXT NOT NULL," + KEY_BANK_ACCOUNT_TYPE + " TEXT NOT NULL" + ")";
    private static final String CREATE_TABLE_BILLS = "CREATE TABLE " + TABLE_BILLS + "(" + KEY_BILLS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BILLS_CATEGORY + " TEXT NOT NULL," + KEY_BILLS_AMOUNT + " TEXT NOT NULL," + KEY_BILLS_DUE_DATE + " TEXT NOT NULL," + KEY_BILLS_STATUS + " TEXT NOT NULL," + KEY_BILLS_NOTE + " TEXT NOT NULL" + ")";

    private static DatabaseHelper databaseHelperInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getDatabaseHelperInstance(Context context) {
        if (databaseHelperInstance == null) {
            databaseHelperInstance = new DatabaseHelper(context);
        }
        return databaseHelperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_PASSCODE);
        sqLiteDatabase.execSQL(CREATE_TABLE_INCOME);
        sqLiteDatabase.execSQL(CREATE_TABLE_BUDGET);
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT);
        sqLiteDatabase.execSQL(CREATE_TABLE_EXPENSE);
        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_BILLS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSCODE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        sqLiteDatabase.endTransaction();
        this.onCreate(sqLiteDatabase);
    }



    /*CUSTOM METHOD*/

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //LOCK_ACTIVITY
    public boolean getPassState() throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.query(DatabaseHelper.TABLE_PASSCODE, null, null, null, null, null, null);
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        sqLiteDatabase.close();
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return false;
    }

    public boolean vPasscode(String passcode) throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        Cursor mCursor = sqLiteDatabase.query(DatabaseHelper.TABLE_PASSCODE, null, " passcode =? ", new String[]{passcode}, null, null, null);
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        sqLiteDatabase.close();
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return false;
    }

    public void iPasscode(PatternData patternData) throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_PASSCODE, patternData.getPasscode());
        sqLiteDatabase.insert(DatabaseHelper.TABLE_PASSCODE, null, contentValues);
        sqLiteDatabase.close();
    }





    //SUMMARY_ACTIVITY
    public void expense(SummaryData summaryData) throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_SPENT_AMOUNT, summaryData.getAmount());
        contentValues.put(DatabaseHelper.KEY_EXPENSE_CATEGORY, summaryData.getCategory());
        contentValues.put(DatabaseHelper.KEY_EXPENSE_DATE, summaryData.getDate());
        contentValues.put(DatabaseHelper.KEY_EXPENSE_NOTE, summaryData.getNote());
        sqLiteDatabase.insert(DatabaseHelper.TABLE_EXPENSE, null, contentValues);
        sqLiteDatabase.close();
    }

    public void income(SummaryData summaryData) throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_COLLECTED_AMOUNT, summaryData.getAmount());
        contentValues.put(DatabaseHelper.KEY_INCOME_CATEGORY, summaryData.getCategory());
        contentValues.put(DatabaseHelper.KEY_INCOME_DATE, summaryData.getDate());
        contentValues.put(DatabaseHelper.KEY_INCOME_NOTE, summaryData.getNote());
        sqLiteDatabase.insert(DatabaseHelper.TABLE_INCOME, null, contentValues);
        sqLiteDatabase.close();
    }

    public void category(CategoryData categoryData) throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_CATEGORY_NAME, categoryData.getCategory());
        contentValues.put(DatabaseHelper.KEY_CATEGORY_TYPE, categoryData.getCategory_type());
        contentValues.put(DatabaseHelper.KEY_CATEGORY_COLOR, categoryData.getCategory_color());
        sqLiteDatabase.insert(DatabaseHelper.TABLE_CATEGORY, null, contentValues);
        sqLiteDatabase.close();
    }

    public void addbudget(BudgetData budgetData) throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_BUDGET_AMOUNT, budgetData.getBudget_amount());
        contentValues.put(DatabaseHelper.KEY_BUDGET_CATEGORY, budgetData.getBudget_category());
        contentValues.put(DatabaseHelper.KEY_BUDGET_DATE, budgetData.getBudget_date());
        sqLiteDatabase.insert(DatabaseHelper.TABLE_BUDGET, null, contentValues);
        sqLiteDatabase.close();
    }

    public void addaccount(AccountData accountData) throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_ACCOUNT_BALANCE, accountData.getBalance());
        contentValues.put(DatabaseHelper.KEY_CUSTOMER_BANK, accountData.getCustomer_bank());
        contentValues.put(DatabaseHelper.KEY_ACCOUNT_NUMBER, accountData.getAccount_number());
        contentValues.put(DatabaseHelper.KEY_BANK_ACCOUNT_TYPE, accountData.getAccount_type());
        sqLiteDatabase.insert(DatabaseHelper.TABLE_ACCOUNT, null, contentValues);
        sqLiteDatabase.close();
    }

    public void addbills(BillsData billsData) throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_BILLS_CATEGORY, billsData.getBill_category());
        contentValues.put(DatabaseHelper.KEY_BILLS_AMOUNT, billsData.getBill_amount());
        contentValues.put(DatabaseHelper.KEY_BILLS_DUE_DATE, billsData.getDue_date());
        contentValues.put(DatabaseHelper.KEY_BILLS_STATUS, billsData.getStatus());
        contentValues.put(DatabaseHelper.KEY_BILLS_NOTE, billsData.getNote());
        sqLiteDatabase.insert(DatabaseHelper.TABLE_BILLS, null, contentValues);
        sqLiteDatabase.close();
    }

    public List<BillsData> getBillsInfo() throws SQLException {
        List<BillsData> billsDataList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_BILLS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                BillsData billsData = new BillsData();
                billsData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BILLS_ID))));
                billsData.setBill_category(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BILLS_CATEGORY)));
                billsData.setBill_amount(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BILLS_AMOUNT)));
                billsData.setDue_date(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BILLS_DUE_DATE)));
                billsData.setStatus(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BILLS_STATUS)));
                billsData.setNote(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BILLS_NOTE)));
                billsDataList.add(billsData);
            } while (cursor.moveToNext());
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return billsDataList;
    }


    public boolean getCatStatus() throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.query(DatabaseHelper.TABLE_CATEGORY, null, null, null, null, null, null);
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        sqLiteDatabase.close();
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return false;
    }



    public int getID() {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.query(DatabaseHelper.TABLE_PASSCODE, null, null, null, null, null, null);
        mCursor.moveToFirst();
        sqLiteDatabase.close();
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.KEY_PASSCODE_ID)));
    }

    public boolean AccountInfo() throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.query(DatabaseHelper.TABLE_ACCOUNT, null, null, null, null, null, null);
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        sqLiteDatabase.close();
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return false;
    }

    public List<AccountData> getAccountInfo() throws SQLException {
        List<AccountData> accountDataList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_ACCOUNT, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                AccountData accountData = new AccountData();
                accountData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_ACCOUNT_ID))));
                accountData.setBalance(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_ACCOUNT_BALANCE)));
                accountData.setCustomer_bank(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CUSTOMER_BANK)));
                accountData.setAccount_number(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_ACCOUNT_NUMBER)));
                accountData.setAccount_type(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BANK_ACCOUNT_TYPE)));
                accountDataList.add(accountData);
            } while (cursor.moveToNext());
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return accountDataList;
    }

    public boolean MyBillInfo() throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.query(DatabaseHelper.TABLE_BILLS, null, null, null, null, null, null);
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        sqLiteDatabase.close();
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return false;
    }


    public boolean MyBudgetInfo() throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.query(DatabaseHelper.TABLE_BUDGET, null, null, null, null, null, null);
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        sqLiteDatabase.close();
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return false;
    }

    public List<BudgetData> getBudget() throws SQLException {
        List<BudgetData> budgetDataList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_BUDGET, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                BudgetData budgetData = new BudgetData();
                budgetData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BUDGET_ID))));
                budgetData.setBudget_amount(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BUDGET_AMOUNT)));
                budgetData.setBudget_category(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BUDGET_CATEGORY)));
                budgetData.setBudget_date(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BUDGET_DATE)));
                budgetDataList.add(budgetData);
            } while (cursor.moveToNext());
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return budgetDataList;
    }

    public List<String> getCategory(String type) throws SQLException {
        List<String> category = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_CATEGORY, new String[]{DatabaseHelper.KEY_CATEGORY_NAME}, " category_type =? ", new String[]{type}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                category.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CATEGORY_NAME)));
            } while (cursor.moveToNext());
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return category;
    }

    public List<String> getDebitCreditInfo() throws SQLException {
        List<String> debitCredit = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_ACCOUNT, new String[]{DatabaseHelper.KEY_ACCOUNT_NUMBER}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                debitCredit.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_ACCOUNT_NUMBER)));
            } while (cursor.moveToNext());
            if (!cursor.isClosed()) {
                cursor.close();
            }
        } else {
            debitCredit.add("NO ACCOUNT");
        }
        return debitCredit;
    }


    public List<SummaryData> getEXPENSE() throws SQLException {
        List<SummaryData> summaryDataList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getReadableDatabase();
        String SELECT_QUERY = "SELECT expense.id, expense.spent_amount, expense.expense_category, expense.expense_date, expense.expense_note, category.category_color  FROM " + TABLE_EXPENSE + " INNER JOIN " + TABLE_CATEGORY + " ON expense.expense_category = category.category_name";
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                SummaryData summaryData = new SummaryData();
                summaryData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_EXPENSE_ID))));
                summaryData.setAmount(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_SPENT_AMOUNT)));
                summaryData.setCategory(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_EXPENSE_CATEGORY)));
                summaryData.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_EXPENSE_DATE)));
                summaryData.setNote(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_EXPENSE_NOTE)));
                summaryData.setColor(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CATEGORY_COLOR)));
                summaryDataList.add(summaryData);
            } while (cursor.moveToNext());
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return summaryDataList;
    }

    public List<SummaryData> getINCOME() throws SQLException {
        List<SummaryData> summaryDataList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_INCOME + " INNER JOIN " + TABLE_CATEGORY + " ON income.income_category = category.category_name";
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                SummaryData summaryData = new SummaryData();
                summaryData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_INCOME_ID))));
                summaryData.setAmount(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_COLLECTED_AMOUNT)));
                summaryData.setCategory(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_INCOME_CATEGORY)));
                summaryData.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_INCOME_DATE)));
                summaryData.setNote(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_INCOME_NOTE)));
                summaryData.setColor(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CATEGORY_COLOR)));
                summaryDataList.add(summaryData);
            } while (cursor.moveToNext());
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return summaryDataList;
    }

    public void delIncome(long id) throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseHelper.TABLE_INCOME, DatabaseHelper.KEY_INCOME_ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void delExpense(long id) throws SQLException {
        SQLiteDatabase sqLiteDatabase = databaseHelperInstance.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseHelper.TABLE_EXPENSE, DatabaseHelper.KEY_EXPENSE_ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
}
