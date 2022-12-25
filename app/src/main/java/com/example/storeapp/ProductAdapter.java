package com.example.storeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductCard> {

    List<Product> productList;
    myDatabaseHelper myStore ;

    public ProductAdapter(myDatabaseHelper myStore ) {
        this.myStore= myStore;
        this.productList = myStore.Retrive_Products();
    }

    @NonNull
    @Override
    public ProductCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //context==currentScreen
        //ViewHolder==CategoryCard
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorycard,parent,false);
        return new ProductAdapter.ProductCard(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductCard holder, int position) {
        //items of recyclerview created in veiwholder is put(viewed) here on onBindviewholder
        Product p = productList.get(position) ;
        Bitmap bitmap = BitmapFactory.decodeByteArray(p.getProductImage(), 0,p.getProductImage().length);
        holder.img.setImageBitmap(bitmap);
        holder.txt.setText(p.getName());

        holder.Deletefloatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myStore.DeleteProduct(p.getId());
            }
        });

        holder.Editfloatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),EditProductActivity.class);

                i.putExtra("productName",p.getName());
                i.putExtra("img", p.getProductImage());
                view.getContext().startActivity(i);

            }
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }



    public class ProductCard extends RecyclerView.ViewHolder {
        ImageView img ;
        TextView txt;
        FloatingActionButton Editfloatbtn;
        FloatingActionButton Deletefloatbtn;
        public ProductCard(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.product_imageView);
            txt=itemView.findViewById(R.id.productLabel_textView);
            Editfloatbtn=itemView.findViewById(R.id.editbtn_floatingButton);
            Deletefloatbtn=itemView.findViewById(R.id.deletebtn_floatingButton);

        }
}



}
