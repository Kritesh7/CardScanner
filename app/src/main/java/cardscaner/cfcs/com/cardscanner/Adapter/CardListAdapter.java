package cardscaner.cfcs.com.cardscanner.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import cardscaner.cfcs.com.cardscanner.MainClass.CustomerDetailsActivity;
import cardscaner.cfcs.com.cardscanner.Model.CardListModel;
import cardscaner.cfcs.com.cardscanner.R;
import cardscaner.cfcs.com.cardscanner.source.SettingConstant;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 26-07-2017.
 */

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder>
{

    public ArrayList<CardListModel> list = new ArrayList<>();
    public Context context;
    public Activity activity;

    public CardListAdapter(Context context, ArrayList<CardListModel> list, Activity activity)
    {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_recycler_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final CardListModel model = list.get(position);

        holder.nameTxt.setText(model.getName());
        holder.companyNameTxt.setText(model.getCompName());
        holder.phonenumber.setText(model.getPhonenUmber());
        holder.designationtxt.setText(model.getDesignation());

        Picasso.with(context).load(SettingConstant.ImageUrl+model.getPhoto()).error(R.drawable.card).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, CustomerDetailsActivity.class);
                intent.putExtra("CustomerId", model.getCustId());
                activity.startActivity(intent);
                activity. overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameTxt,companyNameTxt,phonenumber,designationtxt;
        ImageView imageView;
        public LinearLayout cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTxt = (TextView)itemView.findViewById(R.id.name_txt);
            companyNameTxt = (TextView)itemView.findViewById(R.id.comapnyname_text);
            designationtxt = (TextView)itemView.findViewById(R.id.designationtxt);
            phonenumber = (TextView)itemView.findViewById(R.id.phonenumbertxt);
            imageView = (ImageView)itemView.findViewById(R.id.imagecards);
            cardView = (LinearLayout)itemView.findViewById(R.id.cardview);

        }
    }

   /* public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
        } catch (IOException e) {
            Log.e(TAG, "Could not load Bitmap from: " + url);
        } finally {
            closeStream(in);
            closeStream(out);
        }

        return bitmap;
    }*/
}
