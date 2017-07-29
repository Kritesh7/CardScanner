package cardscaner.cfcs.com.cardscanner.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

import cardscaner.cfcs.com.cardscanner.Model.PrincipalTypeModel;
import cardscaner.cfcs.com.cardscanner.R;

/**
 * Created by Admin on 29-07-2017.
 */

public class PrincipalTypeAdapter extends RecyclerView.Adapter<PrincipalTypeAdapter.ViewHolder>
{

    public Context context;
    public ArrayList<PrincipalTypeModel> list = new ArrayList<>();

    public PrincipalTypeAdapter(Context context, ArrayList<PrincipalTypeModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.businessvertical_list_item,parent,false);
        return new PrincipalTypeAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PrincipalTypeModel model = list.get(position);
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
