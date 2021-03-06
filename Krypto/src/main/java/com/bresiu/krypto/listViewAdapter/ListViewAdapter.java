package com.bresiu.krypto.listViewAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bresiu.krypto.R;
import com.bresiu.krypto.db.Message;
import com.bresiu.krypto.utils.cipher.CaesarDecrypt;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    Activity context;
    List<Message> values;
    CaesarDecrypt cd = new CaesarDecrypt();

    public ListViewAdapter(Activity context, List<Message> values) {
        super();
        this.context = context;
        this.values = values;
    }


    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return values.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            //TODO: reverse view
            if (values.get(position).getOwn() == 0) {
                convertView = inflater.inflate(R.layout.message_received, null);
            } else {
                convertView = inflater.inflate(R.layout.message_sent, null);
            }

            holder = new ViewHolder();
            holder.messageView = (TextView) convertView.findViewById(R.id.message_box);
            holder.phoneView = (TextView) convertView.findViewById(R.id.phone_box);
            holder.dateView = (TextView) convertView.findViewById(R.id.date_box);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.messageView.setText(cd.caesarDecrypt(values.get(position).getMessage()));
        holder.dateView.setText(values.get(position).getTime());
        holder.phoneView.setText(values.get(position).getPhone());

        return convertView;
    }

    private class ViewHolder {
        TextView messageView;
        TextView phoneView;
        TextView dateView;
    }

}