package ssu.cs360.fight;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by kgboperative on 12/8/13.
 */
public class HeroListItem implements HeroItem {
    private final String classTypeName;
    private final String classTypeDescription;
    private final LayoutInflater inflater;

    public HeroListItem(LayoutInflater inflater, String classTypeName,
                        String classTypeDescription) {
        this.classTypeName = classTypeName;
        this.classTypeDescription = classTypeDescription;
        this.inflater = inflater;
    }

    @Override
    public int getViewType() {
        return NavigationArrayAdapter.RowType.LIST_ITEM_HERO.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.hero_list_item, null);
            // Do something, maybe?
        }
        else {
            view = convertView;
        }

        TextView textView1 = (TextView) view.findViewById(R.id.list_content1);
        textView1.setText(classTypeName);
        TextView textView2 = (TextView) view.findViewById(R.id.list_content2);
        textView2.setText(classTypeDescription);

        return view;
    }

    public String getClassTypeName() {
        return classTypeName;
    }
}
