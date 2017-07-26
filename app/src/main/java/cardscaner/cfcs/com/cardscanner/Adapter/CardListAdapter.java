package cardscaner.cfcs.com.cardscanner.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cardscaner.cfcs.com.cardscanner.Model.CardListModel;
import cardscaner.cfcs.com.cardscanner.R;

/**
 * Created by Admin on 26-07-2017.
 */

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder>
{

    public ArrayList<CardListModel> list = new ArrayList<>();
    public Context context;

    public CardListAdapter(Context context, ArrayList<CardListModel> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_recycler_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CardListModel model = list.get(position);

        holder.nameTxt.setText(model.getName());
        holder.companyNameTxt.setText(model.getCompName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameTxt,companyNameTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTxt = (TextView)itemView.findViewById(R.id.name_txt);
            companyNameTxt = (TextView)itemView.findViewById(R.id.comapnyname_text);

        }
    }
}
