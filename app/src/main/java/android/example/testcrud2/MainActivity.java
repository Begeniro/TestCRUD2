package android.example.testcrud2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> dataId,dataTanggal, dataKeterangan, dataJumlah;
    RecyclerView rv;
    TextView tvIncome, tvExpanses, tvBalance;
    DBHelper dbcenter;
    Cursor cursor;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper dbcenter = new DBHelper(this);
        Button tambah = findViewById(R.id.tambah_catatan);
        tvIncome = findViewById(R.id.income);
        tvExpanses = findViewById(R.id.expenses);
        tvBalance=findViewById(R.id.balance);

        dataId = new ArrayList<>();
        dataTanggal = new ArrayList<>();
        dataKeterangan=new ArrayList<String>();
        dataJumlah = new ArrayList<String>();



        SQLiteDatabase db = dbcenter.getReadableDatabase();
        int i = 0;
        int e = 0;
        //mengambil jumlah income user dari sqlite
        cursor = db.rawQuery("SELECT SUM(jumlah) FROM transaksi WHERE  jenis = 'Income'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) //jika hasil query tidak kosong
        {
            cursor.moveToPosition(0);
            if (cursor.isNull(0)) {
                tvIncome.setText("0"); //jika jumlah nya null isi text view dengan angka 0
                i = 0;
            } else { //jika tidak null isi sesuai databasw
                tvIncome.setText(cursor.getString(0).toString());
                i = Integer.parseInt(cursor.getString(0).toString());
            }

        }
        //mengambil jumlah expenses user dari sqlite
        cursor = db.rawQuery("SELECT SUM(jumlah) FROM transaksi WHERE jenis = 'Expenses'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) //jika hasil query tidak kosong
        {
            cursor.moveToPosition(0);
            if (cursor.isNull(0)) { //jika jumlah nya null isi text view dengan angka 0
                tvExpanses.setText("0");
                e = 0;
            } else{
                tvExpanses.setText(cursor.getString(0).toString());
                e = Integer.parseInt(cursor.getString(0).toString());
            }
        }
        int total = i-e; //total diperoleh dari income - expenses
        tvBalance.setText(Integer.toString(total));


        //mengambil data dari database untuk isi dari recycler view
        cursor = db.rawQuery("SELECT * FROM transaksi", null);
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            //masukkan data kedalam array
            dataId.add(cursor.getString(0).toString());
            dataTanggal.add(cursor.getString(1).toString());
            dataKeterangan.add(cursor.getString(5).toString());
            dataJumlah.add(cursor.getString(4).toString());
        }
        rv = (RecyclerView) findViewById(R.id.rv_main);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new MyAdapter(dataId,dataTanggal,dataKeterangan,dataJumlah);
        rv.setAdapter(adapter);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent(getApplicationContext(),TambahCatatan.class);
                startActivity(m);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}