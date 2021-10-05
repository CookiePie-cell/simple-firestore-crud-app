package com.example.firstcrud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements UpdateDialog.UpdateDialogListener{
    private TextView txtPengumuman, loadingData;
    private EditText editTextNama, editTextAlamat;
    private Button btnKirim, btnTampil;
    private DataDiri dataDiri;
    private RecyclerView dataRecView;
    private List<DataDiri> data;
    private DataAdapter dataAdapter;

    private boolean tampilkanData = false;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListenerRegistration pengumumanListener;
    private ListenerRegistration dataDiriListListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPengumuman = findViewById(R.id.txtPengumuman);
        editTextNama = findViewById(R.id.editTextNama);
        editTextAlamat = findViewById(R.id.editTextAlamat);
        loadingData = findViewById(R.id.loadingData);
        btnKirim = findViewById(R.id.btnKirim);
        btnTampil = findViewById(R.id.btnTampil);
        dataRecView = findViewById(R.id.dataRecView);

        loadingData.setVisibility(View.VISIBLE);

        data = new ArrayList<>();

        dataAdapter = new DataAdapter(MainActivity.this);
        dataAdapter.setDataDiriList(data);
        dataRecView.setAdapter(dataAdapter);
        dataRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));



        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();
                String nama = editTextNama.getText().toString();
                String alamat = editTextAlamat.getText().toString();
                Log.d("alamat", String.valueOf(alamat.isEmpty()));
                dataDiri = new DataDiri(id, nama, alamat);
                db.collection("Data Diri").document(id).set(dataDiri).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        dataAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"Sukses Kirim Data",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Gagal Kirim Data",Toast.LENGTH_LONG).show();
                    }
                });
                editTextNama.getText().clear();
                editTextAlamat.getText().clear();
            }
        });

        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilkanData = !tampilkanData;
                if(tampilkanData){
                    dataRecView.setVisibility(View.VISIBLE);
                    btnTampil.setText("Sembunyikan");
                }
                else{
                    btnTampil.setText("Tampil data");
                    dataRecView.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void updateData(String id, String nama, String alamat) {
        db.collection("Data Diri").document(id).update("nama", nama, "alamat", alamat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"Sukses update Data",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Gagal update Data",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void deleteData(String id) {
        db.collection("Data Diri").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this,"Sukses hapus Data",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Gagal hapus Data",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        pengumumanListener = db.collection("Pengumuman").document("idPengumuman").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }
                if(value != null && value.exists()){
                    Log.d("whymen", String.valueOf(value.getData().get("pengumuman")));
                    txtPengumuman.setText(String.valueOf(value.getData().get("pengumuman")));
                }
            }
        });

        dataDiriListListener = db.collection("Data Diri").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }

                data = value.toObjects(DataDiri.class);
                dataAdapter.setDataDiriList(data);
                loadingData.setVisibility(View.INVISIBLE);


            }
        });
    }

    @Override
    protected void onStop() {
        if(pengumumanListener != null){
            pengumumanListener.remove();
        } if(dataDiriListListener != null){
            dataDiriListListener.remove();
        }
        super.onStop();
    }
}