package cardscaner.cfcs.com.cardscanner.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import cardscaner.cfcs.com.cardscanner.Model.BusinessVerticalCheckList;
import cardscaner.cfcs.com.cardscanner.R;

/**
 * Created by Admin on 29-07-2017.
 */

public class BusinessVerticalAdapter extends RecyclerView.Adapter<BusinessVerticalAdapter.ViewHolder>
{
    public Context context;
    public ArrayList<BusinessVerticalCheckList> lists = new ArrayList<>();

    public BusinessVerticalAdapter(Context context, ArrayList<BusinessVerticalCheckList> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.businessvertical_list_item,parent,false);
        return new BusinessVerticalAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BusinessVerticalCheckList model = lists.get(position);

        holder.checkBox.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
       // TextView nameTxt,companyNameTxt;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.vertical_checkbox);
            //companyNameTxt = (TextView)itemView.findViewById(R.id.comapnyname_text);

        }
    }
}
