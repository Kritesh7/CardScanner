package cardscaner.cfcs.com.cardscanner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cardscaner.cfcs.com.cardscanner.Interface.BusinessVerticalInterface;
import cardscaner.cfcs.com.cardscanner.Interface.CustomerNameInterface;
import cardscaner.cfcs.com.cardscanner.Interface.IndustrySegmentInterface;
import cardscaner.cfcs.com.cardscanner.Interface.IndustryTypeInterface;
import cardscaner.cfcs.com.cardscanner.Interface.PrincipleTypeInterface;
import cardscaner.cfcs.com.cardscanner.Model.BusinessVerticalCheckList;
import cardscaner.cfcs.com.cardscanner.Model.CustomerDetailsModel;
import cardscaner.cfcs.com.cardscanner.R;


/**
 * Created by Admin on 16-06-2017.
 */

public class DemoCustomeAdapter extends BaseAdapter implements Filterable {

    private ArrayList<CustomerDetailsModel> mOriginalValues; // Original Values
    private ArrayList<CustomerDetailsModel> mDisplayedValues;    // Values to be displayed
    LayoutInflater inflater;
    public CustomerNameInterface anInterface;
    public boolean flag = true;
    public String chekingTypes = "";
    public BusinessVerticalInterface businessVerticalInterface;
    public IndustrySegmentInterface industrySegmentInterface;
    public IndustryTypeInterface industryTypeInterface;
    public PrincipleTypeInterface principleTypeInterface;
    boolean[] checkBoxState;
   /* private static final int NOT_SELECTED = -1;
    private int selectedPos = NOT_SELECTED;

    // if called with the same position multiple lines it works as toggle
    public void setSelection(int position) {
        if (selectedPos == position) {
            selectedPos = NOT_SELECTED;
        } else {
            selectedPos = position;
        }
        notifyDataSetChanged();
    }*/

    public DemoCustomeAdapter(Context context, ArrayList<CustomerDetailsModel> mProductArrayList ,
                              CustomerNameInterface anInterface,String checkingTypes,BusinessVerticalInterface businessVerticalInterface,
                              IndustrySegmentInterface industrySegmentInterface,IndustryTypeInterface industryTypeInterface,
                              PrincipleTypeInterface principleTypeInterface) {
        this.mOriginalValues = mProductArrayList;
        this.mDisplayedValues = mProductArrayList;
        this.anInterface = anInterface;
        this.chekingTypes = checkingTypes;
        this.businessVerticalInterface = businessVerticalInterface;
        this.industrySegmentInterface = industrySegmentInterface;
        this.industryTypeInterface = industryTypeInterface;
        this.principleTypeInterface = principleTypeInterface;
        inflater = LayoutInflater.from(context);
        checkBoxState=new boolean[mDisplayedValues.size()];
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mDisplayedValues = (ArrayList<CustomerDetailsModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<CustomerDetailsModel> FilteredArrList = new ArrayList<CustomerDetailsModel>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<CustomerDetailsModel>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getCustomerName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new CustomerDetailsModel(mOriginalValues.get(i).getCustomerName(),mOriginalValues.get(i).getCustomerId()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    private class ViewHolder {
        LinearLayout llContainer;
        CheckBox tvName;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.businessvertical_list_item, null);
            holder.tvName = (CheckBox) convertView.findViewById(R.id.vertical_checkbox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(mDisplayedValues.get(position).getCustomerName());

        final ViewHolder finalHolder = holder;


        holder.tvName.setChecked(checkBoxState[position]);
        holder.tvName.setTag(position);

        final ViewHolder finalHolder1 = holder;
        holder.tvName.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                anInterface.getCustomerName(mDisplayedValues.get(position).getCustomerName());
                anInterface.getCustomerId(mDisplayedValues.get(position).getCustomerId());

                if (((CheckBox) v).isChecked()) {

                    checkBoxState[position] = true;

                    if (chekingTypes.equalsIgnoreCase("1"))
                    {
                        businessVerticalInterface.getBusinessVerticalId(mDisplayedValues.get(position).getCustomerId(),
                                mDisplayedValues.get(position).getCustomerName());
                        finalHolder1.tvName.setChecked(true);
                    }else if (chekingTypes.equalsIgnoreCase("2"))
                    {
                        industrySegmentInterface.getIndustrySegmentId(mDisplayedValues.get(position).getCustomerId(),
                                mDisplayedValues.get(position).getCustomerName());
                        finalHolder1.tvName.setChecked(true);

                    }else if (chekingTypes.equalsIgnoreCase("3"))
                    {
                        industryTypeInterface.getIndustryTypeId(mDisplayedValues.get(position).getCustomerId(),
                                mDisplayedValues.get(position).getCustomerName());

                        finalHolder1.tvName.setChecked(true);

                    }else if (chekingTypes.equalsIgnoreCase("4"))
                    {
                        principleTypeInterface.getPrincipleTypeId(mDisplayedValues.get(position).getCustomerId(),
                                mDisplayedValues.get(position).getCustomerName());

                        finalHolder1.tvName.setChecked(true);
                    }

                }else
                    {
                        checkBoxState[position] = false;
                        finalHolder1.tvName.setChecked(false);
                    }

            }
        });



       /* holder.tvName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)
                {
                       *//* int getPosition = (Integer) compoundButton.getTag();

                        mOriginalValues.get(getPosition).setSelected(buttonView.isChecked());*//*

                    if (chekingTypes.equalsIgnoreCase("1"))
                    {
                        businessVerticalInterface.getBusinessVerticalId(mDisplayedValues.get(position).getCustomerId(),
                                mDisplayedValues.get(position).getCustomerName());
                    }else if (chekingTypes.equalsIgnoreCase("2"))
                    {
                        industrySegmentInterface.getIndustrySegmentId(mDisplayedValues.get(position).getCustomerId(),
                                mDisplayedValues.get(position).getCustomerName());

                    }else if (chekingTypes.equalsIgnoreCase("3"))
                    {
                        industryTypeInterface.getIndustryTypeId(mDisplayedValues.get(position).getCustomerId(),
                                mDisplayedValues.get(position).getCustomerName());

                    }else if (chekingTypes.equalsIgnoreCase("4"))
                    {
                        principleTypeInterface.getPrincipleTypeId(mDisplayedValues.get(position).getCustomerId(),
                                mDisplayedValues.get(position).getCustomerName());
                    }
                }
            }
        });*/

        return convertView;
    }
}