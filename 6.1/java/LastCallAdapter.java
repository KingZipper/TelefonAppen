package com.example.williamrudwall.nyatelefonappen;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class LastCallAdapter extends RecyclerView.Adapter<LastCallAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mContext;
    private ArrayList<String> phoneNumber;

// konstruktor för adaptern
    public LastCallAdapter(Context mContext, ArrayList<String> phoneNumber) {
        this.mContext = mContext;
        this.phoneNumber = phoneNumber;
    }

//Skapar/inflatear vyn
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.items_contacts, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //Sätter de olika telenummrena på plats
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.number.setText(phoneNumber.get(position));
// Lystnare som tar en vidare när man klickar på numren i samtalshistoriken
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callDial = new Intent(Intent.ACTION_DIAL);
                callDial = callDial.setData(Uri.parse("tel:"+phoneNumber.get(position)));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    mContext.startActivity(callDial);
                    return;
                }
                Toast.makeText(mContext, phoneNumber.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Håller koll på hur många nummer som är i listan
    @Override
    public int getItemCount() {
        return phoneNumber.size();
    }

    // Håller vyn och gör sig redo att lägga till nästa
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView number;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.contact_number);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }



}
