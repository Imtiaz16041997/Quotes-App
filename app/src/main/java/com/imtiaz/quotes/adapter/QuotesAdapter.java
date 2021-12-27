package com.imtiaz.quotes.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imtiaz.quotes.R;
import com.imtiaz.quotes.model.QuotesModel;

import java.util.ArrayList;
import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.myViewHolder>  {
    Context context;
    List<QuotesModel> quotes;
//    ArrayList<QuotesModel> backup;

    public QuotesAdapter(List<QuotesModel> quotes,Context context) {
        this.quotes = quotes;
        this.context = context;
//        backup = new ArrayList<>(quotes);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        //Problem is here
        holder.textView_Quotes_ID.setText(String.valueOf(position+1));

        holder.textView_Quotes.setText(quotes.get(position).getText());
        String text = holder.textView_Quotes.getText().toString();
        holder.textView_Author.setText(quotes.get(position).getAuthor());
        String author = holder.textView_Author.getText().toString();

        //ShareQuotes
        holder.shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,  text+"\n"+ author);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Via");
                context.startActivity(Intent.createChooser(shareIntent, "Share..."));
            }
        });

        //Copy Quotes

        holder.textView_Quotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData quotes = ClipData.newPlainText("Copied",text);
                clipboard.setPrimaryClip(quotes);
                Toast.makeText(context, "The Quote has been copied", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public void filterList(List<QuotesModel> filteredList){
            quotes = filteredList;
            notifyDataSetChanged();
    }

//    @Override
//    public Filter getFilter() {
//        return filter;
//    }

//    Filter filter=new Filter() {
//        @Override
//        // background thread
//        protected FilterResults performFiltering(CharSequence keyword)
//        {
//            ArrayList<QuotesModel> filtereddata=new ArrayList<>();
//
//            if(keyword.toString().isEmpty())
//                filtereddata.addAll(backup);
//            else
//            {
//                String filterPattern = keyword.toString().toLowerCase().trim();
//                for(QuotesModel obj : backup)
//                {
//                    if(obj.getText().toLowerCase().contains(filterPattern) && obj.getAuthor().toLowerCase().contains(filterPattern))
//                        filtereddata.add(obj);
//                }
//            }
//
//            FilterResults results=new FilterResults();
//            results.values=filtereddata;
//            return results;
//        }
//
//        @Override  // main UI thread
//        protected void publishResults(CharSequence constraint, FilterResults results)
//        {
//            quotes.clear();
//            quotes.addAll((ArrayList<QuotesModel>)results.values);
//            notifyDataSetChanged();
//        }
//    };




    class myViewHolder extends RecyclerView.ViewHolder{
        TextView textView_Quotes,textView_Author,textView_Quotes_ID;
        ImageView shareView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_Quotes = itemView.findViewById(R.id.textView_Quotes);
            textView_Author = itemView.findViewById(R.id.textView_Author);
            textView_Quotes_ID = itemView.findViewById(R.id.textView_Quotes_ID);
            shareView = itemView.findViewById(R.id.shareView);

        }
    }
}
