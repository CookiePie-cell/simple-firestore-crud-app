package com.example.firstcrud;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class UpdateDialog extends DialogFragment {
    private EditText dialogEditTxtNama;
    private EditText dialogEditTxtAlamat;
    private UpdateDialogListener listener;
    private DataDiri dataDiri;

    public UpdateDialog(DataDiri dataDiri){
        this.dataDiri = dataDiri;
    }

    public interface UpdateDialogListener{
        void updateData(String id, String nama, String alamat);
        void deleteData(String id);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);

        dialogEditTxtNama = view.findViewById(R.id.dialogEditTxtNama);
        dialogEditTxtAlamat = view.findViewById(R.id.dialogEditTxtAlamat);

        dialogEditTxtNama.setText(dataDiri.getNama());
        dialogEditTxtAlamat.setText(dataDiri.getAlamat());

        builder.setView(view)
                .setTitle("Update")
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.deleteData(dataDiri.getId());
                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nama = dialogEditTxtNama.getText().toString();
                        String alamat = dialogEditTxtAlamat.getText().toString();
                        listener.updateData(dataDiri.getId(), nama, alamat);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }
}
