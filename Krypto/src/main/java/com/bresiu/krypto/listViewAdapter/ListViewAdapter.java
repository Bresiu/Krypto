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

    public int getCount() {
        return values.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.message, null);
            holder = new ViewHolder();
            holder.messageView = (TextView) convertView.findViewById(R.id.message_box);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.messageView.setText(cd.caesarDecrypt(values.get(position).getMessage()));

        return convertView;
    }

    private class ViewHolder {
        TextView messageView;
    }

}