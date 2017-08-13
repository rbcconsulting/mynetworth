package com.rbcconsulting.mobile.mynetworth.databaseHelper;

import android.content.ContentValues;
import android.database.Cursor;

import com.rbcconsulting.mobile.mynetworth.model.Debt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ralph on 13/08/2017.
 */

public class DebtHelper {

    private DatabaseHelper dbHelper;

    public DebtHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Debt addDebt(Debt debt) {
        ContentValues contentValues = new ContentValues();
        Cursor debtCursor;
        Debt createdDebt;

        contentValues.put(DATABASE_CONSTANTS._DEBTS_TYPE, debt.getType());
        contentValues.put(DATABASE_CONSTANTS._DEBTS_AMOUNT, debt.getAmount());
        contentValues.put(DATABASE_CONSTANTS._DEBTS_REMARKS, debt.getRemarks());
        contentValues.put(DATABASE_CONSTANTS._DEBTS_DATE, debt.getDate());

        long id = dbHelper.createRecord(DATABASE_CONSTANTS.TBL_DEBTS, contentValues);

        debtCursor = dbHelper.getRecord(DATABASE_CONSTANTS.TBL_DEBTS, null,
                DATABASE_CONSTANTS._DEBTS_ID + "=?",
                new String[]{String.valueOf(id)});

        debtCursor.moveToFirst();

        createdDebt = new Debt(debtCursor.getInt(0),
                debtCursor.getString(1),
                debtCursor.getDouble(2),
                debtCursor.getString(3),
                debtCursor.getString(4));

        debtCursor.close();

        return createdDebt;
    }

    public int deleteDebt(Debt debt) {
        return dbHelper.deleteRecord(DATABASE_CONSTANTS.TBL_DEBTS,
                DATABASE_CONSTANTS._DEBTS_ID + "=?",
                new String[]{String.valueOf(debt.getDebtId())});
    }

    public Debt getDebt(Debt debt) {
        Cursor debtCursor;
        Debt createdDebt;

        debtCursor = dbHelper.getRecord(DATABASE_CONSTANTS.TBL_DEBTS, null,
                DATABASE_CONSTANTS._DEBTS_ID + "=?",
                new String[]{String.valueOf(debt.getDebtId())});

        debtCursor.moveToFirst();
        createdDebt = new Debt(debtCursor.getInt(0),
                debtCursor.getString(1),
                debtCursor.getDouble(2),
                debtCursor.getString(3),
                debtCursor.getString(4));

        debtCursor.close();

        return createdDebt;
    }

    public List<Debt> getAllDebts() {
        Cursor debtCursor;
        List<Debt> debts = new ArrayList<Debt>();

        debtCursor = dbHelper.getRecord(DATABASE_CONSTANTS.TBL_DEBTS, null, null, null);

        try {
            while (debtCursor.moveToNext()) {
                debts.add(new Debt(debtCursor.getInt(0),
                        debtCursor.getString(1),
                        debtCursor.getDouble(2),
                        debtCursor.getString(3),
                        debtCursor.getString(4)));
            }
        } finally {
            debtCursor.close();
        }

        return debts;
    }

    public int countAllDebts() {
        return dbHelper.countRecords(DATABASE_CONSTANTS.TBL_DEBTS, null, null);
    }

    public int countAllDebtsWhere(String[] columns, String[] whereValues) {
        StringBuilder whereClause = new StringBuilder("");
        for (int ctr = 0; ctr < columns.length; ctr++) {
            whereClause.append(whereClause);
            whereClause.append(columns[ctr]);
            whereClause.append("=?");
            if (columns.length - 1 != ctr) {
                whereClause.append(" AND ");
            }
        }
        return dbHelper.countRecords(DATABASE_CONSTANTS.TBL_DEBTS, whereClause.toString(), whereValues);
    }

    public int updateDebt(Debt debt) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DATABASE_CONSTANTS._DEBTS_TYPE, debt.getType());
        contentValues.put(DATABASE_CONSTANTS._DEBTS_AMOUNT, debt.getAmount());
        contentValues.put(DATABASE_CONSTANTS._DEBTS_DATE, debt.getDate());
        contentValues.put(DATABASE_CONSTANTS._DEBTS_REMARKS, debt.getRemarks());

        return dbHelper.updateRecord(DATABASE_CONSTANTS.TBL_DEBTS,
                DATABASE_CONSTANTS._DEBTS_ID + "=?",
                new String[]{String.valueOf(debt.getDebtId())},
                contentValues
        );
    }
}
