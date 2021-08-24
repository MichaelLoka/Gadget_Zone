package com.example.storeapp;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    List<String> Descriptions;
    List<String> Price;
    List<Integer> images;
    LayoutInflater inflater;
    private clickMenuItemListener clickMenuItemListener;

    public Adapter(Context context,List<String> Descriptions,List<Integer> images, List<String> Price,clickMenuItemListener clickMenuItemListener) {
        this.Descriptions = Descriptions;
        this.images = images;
        this.Price = Price;
        this.inflater = LayoutInflater.from(context);
        this.clickMenuItemListener = clickMenuItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);
        return new ViewHolder(view,clickMenuItemListener);
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        holder.Description.setText(Descriptions.get(position));
        holder.Price.setText(Price.get(position));
        holder.gridicon.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return Descriptions.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener
    {
        private static final String TAG = "MyViewHolder";
        TextView Description;
        TextView Price;
        ImageView gridicon;
        clickMenuItemListener clickMenuItemListener;

        public ViewHolder(View itemView,clickMenuItemListener clickMenuItemListener) {
            super(itemView);
            Description = itemView.findViewById(R.id.Description);
            Price = itemView.findViewById(R.id.Price);
            gridicon = itemView.findViewById(R.id.imageView3);

            this.clickMenuItemListener = clickMenuItemListener;
            gridicon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            boolean result = clickMenuItemListener.clickMenuItem(menuItem, getAdapterPosition());
            return result;
        }
    }
    public interface clickMenuItemListener{
        boolean clickMenuItem(MenuItem menuItem,int position);
    }
}