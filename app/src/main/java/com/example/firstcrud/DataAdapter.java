package com.example.firstcrud;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<DataDiri> dataDiriList;
    private Context context;

    public DataAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNama.setText(String.valueOf(dataDiriList.get(position).getNama()));
        holder.txtAlamat.setText(String.valueOf(dataDiriList.get(position).getAlamat()));

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new UpdateDialog(dataDiriList.get(position));
                dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "update dialog");
//                Toast.makeText(context, dataDiriList.get(position).getNama() + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("hellow", " sdsdfsdf");
        return dataDiriList.size();
    }

    public void setDataDiriList(List<DataDiri> data){
        this.dataDiriList = data;
        Log.d("wkwk", data.toString());
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtNama;
        private final TextView txtAlamat;
        private final CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.txtNama);
            txtAlamat = (TextView) itemView.findViewById(R.id.txtAlamat);
            parent = (CardView) itemView.findViewById(R.id.parent);
        }
    }
}
