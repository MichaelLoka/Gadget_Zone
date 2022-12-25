package com.example.storeapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    List<String> Names;
    List<Integer> Prices;
    List<Integer> images;
    LayoutInflater inflater;
    private clickMenuItemListener clickMenuItemListener;

    private ArrayList<Product> mProducts;

    private Context mContext;

    public Adapter(Context context, ArrayList<Product> mProducts ,clickMenuItemListener clickMenuItemListener){
        this.mProducts = mProducts;
        this.mContext = context;
        this.clickMenuItemListener = clickMenuItemListener;
    }

    public Adapter(Context context,List<String> name,List<Integer> images, List<Integer> Price,clickMenuItemListener clickMenuItemListener) {
        this.Names = name;
        this.images = images;
        this.Prices = Price;
        this.inflater = LayoutInflater.from(context);
        this.clickMenuItemListener = clickMenuItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        final myDatabaseHelper shoppingDatabase = new myDatabaseHelper(mContext);
        final Product productItem = mProducts.get(position);

        holder.name.setText(productItem.getName());
        holder.price.setText(String.valueOf(productItem.getPrice()));
        holder.gridicon.setImageResource(productItem.getImange_id());
        View.OnClickListener onClickListenerPlus = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.valueOf(holder.product_quantity.getText().toString());
                value++;
                holder.product_quantity.setText(String.valueOf(value));
            }
        };
        View.OnClickListener onClickListenerMinus = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(holder.product_quantity.getText().toString())>0){
                    int value = Integer.valueOf(holder.product_quantity.getText().toString());
                    value--;
                    holder.product_quantity.setText(String.valueOf(value));
                }
            }
        };
        View.OnClickListener onClickListenerCart = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Proposed Product ID", String.valueOf(productItem.getId()));
                shoppingDatabase.InsertTOCart(MainActivity.Phone_Number, productItem.getId(),
                        Integer.valueOf(holder.product_quantity.getText().toString()));
                Toast.makeText(mContext, holder.product_quantity.getText().toString()
                        +" "+productItem.getName()+" added to your cart.", Toast.LENGTH_SHORT).show();
            }
        };
        holder.plus.setOnClickListener(onClickListenerPlus);
        holder.minus.setOnClickListener(onClickListenerMinus);
        holder.add_to_cart.setOnClickListener(onClickListenerCart);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener
    {
        TextView name;
        TextView price;
        ImageView gridicon;
        clickMenuItemListener clickMenuItemListener;
        public ImageButton add_to_cart;
        public ImageView plus;
        public EditText product_quantity;
        public ImageView minus;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Description);
            price = itemView.findViewById(R.id.Price_contanier);
            gridicon = itemView.findViewById(R.id.imageView3);
            this.add_to_cart = (ImageButton) itemView.findViewById(R.id.add_to_cart);
            this.plus = (ImageView) itemView.findViewById(R.id.plus);
            this.product_quantity = (EditText) itemView.findViewById(R.id.product_quantity);
            this.minus = (ImageView) itemView.findViewById(R.id.minus);
            //this.clickMenuItemListener = clickMenuItemListener;
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