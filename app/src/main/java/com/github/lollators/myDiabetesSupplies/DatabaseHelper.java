package com.github.lollators.myDiabetesSupplies;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyDiabetesSupplies.db";
    private static final String USER_TABLE = "user";
    private static final String SUPPLIES_TABLE = "supplies";
    private static final String USER_PRODUCT_TABLE = "user_product";
    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    //password hashing
    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    private boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + USER_TABLE +
                " (username TEXT PRIMARY KEY, password TEXT NOT NULL)";
        String createProductTable = "CREATE TABLE " + SUPPLIES_TABLE +
                " (serial_number TEXT PRIMARY KEY, name TEXT NOT NULL, " +
                "expiration_date TEXT NOT NULL, bin TEXT NOT NULL)";
        String createUserProduct = "CREATE TABLE " + USER_PRODUCT_TABLE +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, " +
                "serial_number TEXT NOT NULL, FOREIGN KEY (username) REFERENCES " + USER_TABLE +
                "(username), FOREIGN KEY (serial_number) REFERENCES " +
                SUPPLIES_TABLE + "(serial_number))";
        db.execSQL(createUserTable);
        db.execSQL(createProductTable);
        db.execSQL(createUserProduct);

        //add testing account
        db.execSQL("INSERT INTO " + USER_TABLE + " (username, password) VALUES ('admin','" + hashPassword("admin") + "')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPPLIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_PRODUCT_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUPPLIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_PRODUCT_TABLE);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    //create new user
    public boolean createUser(String username, String password){
        //check if user already exists
        SQLiteDatabase myDB = this.getWritableDatabase();
        String[] selection = {username};
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + USER_TABLE + " WHERE username=? LIMIT 1;";
            cursor = myDB.rawQuery(sql, selection);
            System.err.println(cursor.getCount());
            //user does not exist
            if(!(cursor.getCount()>0)) {
                //then add user
                SQLiteStatement stmt = myDB.compileStatement("INSERT INTO " + USER_TABLE + " (username, password) VALUES(?,?);");
                stmt.bindString(1, username);
                stmt.bindString(2, hashPassword(password));
                stmt.execute();
                cursor.close();
                return true;
            }
        } catch (SQLException e){
            System.out.println(e.toString());
            e.printStackTrace();
        } catch (Exception e2) {
            System.out.println(e2.toString());
            e2.printStackTrace();
        }

        cursor.close();
        return false;
    }

    //authenticate user
    public boolean login(String username, String password) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        String[] selection = {username};
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + USER_TABLE + " WHERE username=? LIMIT 1;";
            cursor = myDB.rawQuery(sql, selection);

            //if user exist
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                //fetch its stored password
                String hashedpassword = cursor.getString(cursor.getColumnIndex("password"));

                //compare stored password with the one in login screen
                if (checkPassword(password, hashedpassword)) {

                    //if they match user is authenticated
                    cursor.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        } catch (Exception e2) {
            System.out.println(e2.toString());
            e2.printStackTrace();
        }

        //in any other case return false
        cursor.close();
        return false;
    }

    public boolean addProduct(String serialNumber, String name, String expirationDate, String bin){
        SQLiteDatabase myDB = this.getWritableDatabase();
        String[] selection = {serialNumber};
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + SUPPLIES_TABLE + " WHERE serial_number=? LIMIT 1;";
            cursor = myDB.rawQuery(sql, selection);

            //if product exist
            if (cursor.getCount() > 0) {
                cursor.close();
                return false;
            } else {
                String[] product = {serialNumber,name,expirationDate,bin};
                sql = "INSERT INTO " + SUPPLIES_TABLE + "(serial_number, name, expiration_date, bin)" +
                        " VALUES (?,?,?,?);";
                myDB.execSQL(sql, product);
                cursor.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        } catch (Exception e2) {
            System.out.println(e2.toString());
            e2.printStackTrace();

        }

        //in any other case return false
        cursor.close();
        return false;
    }

    public boolean removeProduct(String serialNumber){
        SQLiteDatabase myDB = this.getWritableDatabase();
        String[] selection = {serialNumber};
        Cursor cursor = null;
        try {
            String sql = "SELECT * FROM " + SUPPLIES_TABLE + " WHERE serial_number=? LIMIT 1;";
            cursor = myDB.rawQuery(sql, selection);

            //if product exist - remove it
            if (cursor.getCount() > 0) {
                sql = "DELETE FROM " + SUPPLIES_TABLE + " WHERE serial_number=?;";
                myDB.execSQL(sql, selection);
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        } catch (Exception e2) {
            System.out.println(e2.toString());
            e2.printStackTrace();

        }

        //in any other case return false
        cursor.close();
        return false;
    }

    ArrayList getProduct(String productType) {
        ArrayList<Product> myProducts = new ArrayList<Product>();

        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = null;
        String[] selection = {productType};
        try {
            String sql = "SELECT * FROM " + SUPPLIES_TABLE + " WHERE name=? ORDER BY expiration_date DESC;";
            cursor = myDB.rawQuery(sql, selection);

            //if any products of certain category exist
            while (cursor.moveToNext()) {
                String serialNumber = cursor.getString(cursor.getColumnIndex("serial_number"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String expiration_date = cursor.getString(cursor.getColumnIndex("expiration_date"));
                String bin = cursor.getString(cursor.getColumnIndex("bin"));

                myProducts.add(new Product(serialNumber, name, expiration_date, bin));
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        } catch (Exception e2) {
            System.out.println(e2.toString());
            e2.printStackTrace();
        }

        return myProducts;
    }

}
