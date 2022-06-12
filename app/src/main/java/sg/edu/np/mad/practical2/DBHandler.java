package sg.edu.np.mad.practical2;

import static java.lang.Boolean.parseBoolean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "accountsDB.db";
    public static String USERS = "User";
    public static String COLUMN_NAME = "Name";
    public static String COLUMN_DESCRIPTION = "Description";
    public static String COLUMN_ID = "Id";
    public static String COLUMN_FOLLOWED = "Followed";
    public static int DATABASE_VERSION = 1;

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_DATABASE = "CREATE TABLE " + USERS + "(" + COLUMN_NAME
                + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_ID + " TEXT," + COLUMN_FOLLOWED + " TEXT" + ")";
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }

    /*public User findUser(String username){
        String query = "SELECT * FROM " + ACCOUNTS +
                " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"";
        //SELECT * FROM Accounts WHERE username = "???"
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery(query, null);

        User queryData = new User();

        if (cursor.moveToFirst()){
            queryData.setUserName(cursor.getString(0));
            queryData.setPassword(cursor.getString(1));
            cursor.close();
        }
        else{
            queryData = null;
        }
        db.close();
        return queryData;
    }*/

    public void addUser(User userData){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, userData.getName());
        values.put(COLUMN_DESCRIPTION, userData.getDescription());
        values.put(COLUMN_ID, userData.getId());
        values.put(COLUMN_FOLLOWED, userData.isFollowed());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(USERS, null, values);
        db.close();
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> userList = new ArrayList<>();

        for(int i = 1; i<=20; i++){
            String query = "SELECT * FROM " + USERS +
                    " WHERE " + COLUMN_ID + "=\"" + i + "\"";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor =db.rawQuery(query, null);

            User queryData = new User();
            if (cursor.moveToFirst()) {
                queryData.setName(cursor.getString(0));
                queryData.setDescription(cursor.getString(1));
                queryData.setId(Integer.parseInt(cursor.getString(2)));
                queryData.setFollowed(parseBoolean(cursor.getString(3)));
                cursor.close();
            } else {
                queryData = null;
            }
            db.close();
            userList.add(queryData);
        }
        return userList;
    }

    public void updateUser(User userData){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USERS, COLUMN_ID + "=" + userData.getId(), null);
        db.close();
        addUser(userData);

    }

}
