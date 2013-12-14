package ssu.cs360.fight;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by kgboperative on 12/8/13.
 */
public interface HeroItem {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}