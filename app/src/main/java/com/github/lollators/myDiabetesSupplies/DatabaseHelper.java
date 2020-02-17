package com.github.lollators.myDiabetesSupplies;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import org.mindrot.jbcrypt.BCrypt;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyDiabetesSupplies";
    private static final String USER_TABLE = "user";
    private static final String SUPPLIES_TABLE = "supplies";
    private static final String USER_PRODUCT_TABLE = "user_product";

    //password hashing
    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    private boolean checkPassword(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword))
            return true;
        else
            return false;
    }

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + USER_TABLE +
                " (username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL)";
        String createProductTable = "CREATE TABLE " + SUPPLIES_TABLE +
                " (serial_number TEXT PRIMARY KEY NOT NULL, name TEXT NOT NULL, " +
                "expiration_date TEXT NOT NULL, bin TEXT NOT NULL)";
        String createUserProduct = "CREATE TABLE " + USER_PRODUCT_TABLE +
                " (id INTEGER AUTOINCREMENT PRIMARY KEY NOT NULL, username TEXT NOT NULL, " +
                "serial_number TEXT NOT NULL, FOREIGN KEY (username) REFERENCES " + USER_TABLE +
                "(username), FOREIGN KEY (serial_number) REFERENCES " +
                SUPPLIES_TABLE + "(serial_number))";
        db.execSQL(createUserTable);
        db.execSQL(createProductTable);
        db.execSQL(createUserProduct);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPPLIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_PRODUCT_TABLE);
        onCreate(db);
    }

    //create new user
    //TODO: check that the user does not already exist
    public boolean createUser(String username, String password){
        //check if user already exists
        SQLiteDatabase myDB = this.getWritableDatabase();
        String columns[] = {"username"};
        String selection[] = {username};
        Cursor cursor = null;
        try {
            cursor = myDB.query(USER_TABLE, columns, "username=?", selection,null, null,null,"1");

            //user does not exist
            if(cursor!=null && cursor.getCount()==0) {
                //then add user
                SQLiteStatement stmt = myDB.compileStatement("INSERT INTO " + USER_TABLE + " (username, password) VALUES(?,?)");
                stmt.bindString(1, username);
                stmt.bindString(2, hashPassword(password));
                stmt.execute();
                cursor.close();
                myDB.close();
                return true;
            } else {
                cursor.close();
                myDB.close();
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            myDB.close();
            cursor.close();
            return false;
        }
    }

    //authenticate user
    public boolean login(String username, String password){
        SQLiteDatabase myDB = this.getReadableDatabase();
        String columns[] = {"username","password"};
        String selection[] = {username};
        Cursor cursor = null;
        try {
            cursor = myDB.query(USER_TABLE, columns, "username=?", selection,null, null,null,"1");

            if(cursor!=null && cursor.getCount()>0) {
                String hashedpassword = cursor.getString(cursor.getColumnIndex("password"));

                //user authenticated
                if(checkPassword(password, hashedpassword)) {
                    cursor.close();
                    myDB.close();
                    return true;
                    //wrong password
                } else {
                    cursor.close();
                    myDB.close();
                    return false;
                }

                //user does not exist
            } else {
                cursor.close();
                myDB.close();
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            myDB.close();
            cursor.close();
            return false;
        }
    }



}
