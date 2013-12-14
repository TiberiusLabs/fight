package ssu.cs360.fight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kgboperative on 12/8/13.
 */
public class NavigationArrayAdapter extends ArrayAdapter<HeroItem> {
    private LayoutInflater mInflater;

    public enum RowType {
        LIST_ITEM_HERO, LIST_ITEM_MONSTER, HEADER_ITEM
    }

    public NavigationArrayAdapter(Context context, List<HeroItem> items) {
        super(context, 0, items);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, convertView);
    }

}
