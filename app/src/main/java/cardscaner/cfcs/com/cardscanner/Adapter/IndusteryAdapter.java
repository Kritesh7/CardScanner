package cardscaner.cfcs.com.cardscanner.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

import cardscaner.cfcs.com.cardscanner.Model.IndustryModel;
import cardscaner.cfcs.com.cardscanner.R;

/**
 * Created by Admin on 29-07-2017.
 */

public class IndusteryAdapter extends RecyclerView.Adapter<IndusteryAdapter.ViewHolder>
{
    public Context context;
    public ArrayList<IndustryModel> list = new ArrayList<>();

    public IndusteryAdapter(Context context, ArrayList<IndustryModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.businessvertical_list_item,parent,false);
        return new IndusteryAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        IndustryModel model = list.get(position);

        holder.checkBox.setText(model.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
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
