package com.example.dragablelistview.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.dragablelistview.EditModel;
import com.example.dragablelistview.R;
import com.example.dragablelistview.drag.DragSortListView;

public class DragableListView extends DragSortListView implements EditModel {

    // debug variable
    private static final boolean DBG = true;
    private static final String TAG = "DragableListView";

    // data variable
    private boolean inEdit = false;
    private EditModeListAdapter mAdapter;
    // datas
    private List<String> mList = new ArrayList<String>();

    public DragableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DragableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragableListView(Context context) {
        this(context, null);
    }

    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (EditModeListAdapter) adapter;
    }

    public final class EditModeListAdapter extends BaseAdapter implements
            DropListener {

        private class ViewHolder {
            TextView mName;
        }

        private LayoutInflater mFactory;

        public EditModeListAdapter(Context context) {
            mFactory = LayoutInflater.from(context);
            for (int i = 0; i < 100; i++) {
                mList.add("Item " + i);
            }
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mFactory.inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.mName = (TextView) convertView
                        .findViewById(R.id.item_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final String name = mList.get(position);

            holder.mName.setText(name);
            return convertView;
        }

        @Override
        public void drop(int from, int to) {
            if (mList != null && from != to) {
                final String fromItem = mList.get(from);
                mList.remove(from);
                mList.add(to, fromItem);
                notifyDataSetChanged();
            }
        }

    }

    @Override
    public void enterEdit(boolean anim) {
        log("enterEdit()...");
        if (inEdit)
            return;
        inEdit = true;
        setDragEnabled(true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void editOver(boolean anim) {
        log("editOver()...");
        if (!inEdit)
            return;
        inEdit = false;
        setDragEnabled(false);
        mAdapter.notifyDataSetChanged();
    }

    private void log(String msg) {
        if (DBG) {
            Log.d(TAG, msg);
        }
    }

    public boolean isInEditMode() {
        return inEdit;
    }

}
