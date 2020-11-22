package android.example.testcrud2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "wallenote";
    private static final String TABLE_TRANSAKSI= "transaksi";
    private static final String KEY_ID_TRANSAKSI = "id_transaksi";
    private static final String KEY_JUDUL = "judul";
    private static final String KEY_TANGGAL ="tanggal";
    private static final String KEY_JUMLAH ="jumlah";
    private static final String KEY_JENIS = "jenis";
    private static final String KEY_KETERANGAN = "keterangan";

    private static final String CREATE_TABLE_TRANSAKSI = "CREATE TABLE "
            + TABLE_TRANSAKSI + "("
            + KEY_ID_TRANSAKSI+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_JUDUL + "TEXT ,"
            + KEY_TANGGAL + " DATE ,"
            + KEY_JENIS+" TEXT ,"
            + KEY_JUMLAH+" INTEGER,"
            + KEY_KETERANGAN+" TEXT );";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //mengeksekusi perintah create tabel transaksi
        db.execSQL(CREATE_TABLE_TRANSAKSI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TRANSAKSI + "'");
        onCreate(db);
    }
    //method untuk menambah transaksi
    public void addTransaksi(String tanggal, String judul, String jenis, int jumlah, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TANGGAL, tanggal);
        values.put(KEY_JUDUL, judul);
        values.put(KEY_JENIS, jenis);
        values.put(KEY_JUMLAH,jumlah);
        values.put(KEY_KETERANGAN, keterangan);
        db.insert(TABLE_TRANSAKSI, null, values);
    }
    //method untuk update transaksi
    public void updateTransaksi(int id, String tanggal, String judul, String jenis, int jumlah, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TANGGAL, tanggal);
        values.put(KEY_JUDUL, judul);
        values.put(KEY_JENIS, jenis);
        values.put(KEY_JUMLAH,jumlah);
        values.put(KEY_KETERANGAN, keterangan);
        db.update(TABLE_TRANSAKSI, values, KEY_ID_TRANSAKSI + " = ?", new String[]{String.valueOf(id)});
    }

}
