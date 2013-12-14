package ssu.cs360.fight;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by kgboperative on 12/8/13.
 */
public class HeroHeader implements HeroItem {
    private final String         name;

    public HeroHeader(LayoutInflater inflater, String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return NavigationArrayAdapter.RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.header, null);
            // Do some initialization
        }
        else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.separator);
        text.setText(name);

        return view;
    }

}
