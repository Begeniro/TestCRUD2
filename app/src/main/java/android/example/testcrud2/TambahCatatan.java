package android.example.testcrud2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahCatatan extends AppCompatActivity {
    Button addincome, addexp, save;
    Context context;
    EditText tanggal, judul, keterangan, jumlah;
    String jenis = "Income";
    DBHelper dbcenter;
    final int sdk = android.os.Build.VERSION.SDK_INT;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText tvDateResult;
    private Button btDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan);
        dbcenter = new DBHelper(this);
        final Button addincome = findViewById(R.id.addincome);
        final Button addexp = findViewById(R.id.addexp);
        /* final EditText tanggal = findViewById(R.id.date); */
        final EditText judul = findViewById(R.id.edtjudul);
        final EditText keterangan = findViewById(R.id.edtnote);
        final EditText jumlah = findViewById(R.id.edtjumlah);

        addincome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                jenis = "Income";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    addincome.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.colorPrimary));
                    addincome.setTextColor(ContextCompat.getColor(context, R.color.putih));
                    addexp.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.putih));
                } else {
                    addincome.setBackground(ContextCompat.getDrawable(TambahCatatan.this, R.color.colorPrimary));
                    addincome.setTextColor(ContextCompat.getColor(TambahCatatan.this, R.color.putih));
                    addincome.setTextColor(ContextCompat.getColor(TambahCatatan.this, R.color.putih));
                    addexp.setBackground(ContextCompat.getDrawable(TambahCatatan.this, R.color.putih));
                    addexp.setTextColor(ContextCompat.getColor(TambahCatatan.this, R.color.hitam));
                }
            }
        });
        addexp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                jenis = "Expenses";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    addexp.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.colorPrimary));
                    addexp.setTextColor(ContextCompat.getColor(context, R.color.putih));
                    addincome.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.putih));


                } else {
                    addexp.setBackground(ContextCompat.getDrawable(TambahCatatan.this, R.color.colorPrimary));
                    addexp.setTextColor(ContextCompat.getColor(TambahCatatan.this, R.color.putih));
                    addincome.setBackground(ContextCompat.getDrawable(TambahCatatan.this, R.color.putih));
                    addincome.setTextColor(ContextCompat.getColor(TambahCatatan.this, R.color.hitam));

                }
            }
        });

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        EditText tvDateResult = findViewById(R.id.date);
        Button btDatePicker = findViewById(R.id.calendar);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });


        Button save = findViewById(R.id.btnsave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbcenter.addTransaksi(
                        tanggal.getText().toString(),
                        judul.getText().toString(),
                        jenis,
                        Integer.parseInt(jumlah.getText().toString()),
                        keterangan.getText().toString());
                Intent m = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(m);

            }
        });
    }

    private void showDateDialog() {

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tvDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

}
