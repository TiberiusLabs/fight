package ssu.cs360.fight;

import java.util.ArrayList;

/**
 * Created by kgboperative on 12/2/13.
 */
public class Abilities {
    private ArrayList<Ability> abilityArrayList;

    public Abilities() {
        abilityArrayList = new ArrayList<Ability>();
    }

    public void add(Ability ability) {
        if (!abilityArrayList.contains(ability)) {
            abilityArrayList.add(ability);
        }
    }

    public void remove(Ability ability) {
        abilityArrayList.remove(ability);
    }

    public Ability get(int position) {
        return abilityArrayList.get(position);
    }

    public Ability getWithName(String name) {
        for (int i = 0; i < abilityArrayList.size(); i++) {
            if (name == abilityArrayList.get(i).name)
                return abilityArrayList.get(i);
        }

        return new Ability();
    }

    public String getNameAt(int position) {
        if (position >= 0 && position < abilityArrayList.size())
            return abilityArrayList.get(position).name;
        return "";
    }

    public String getDescriptionAt(int position) {
        if (position >= 0 && position < abilityArrayList.size())
            return abilityArrayList.get(position).description;
        return "";
    }

    public String[] getAllNames() {
        String[] names = new String[abilityArrayList.size()];

        for (int i = 0; i < abilityArrayList.size(); i++)
            names[i] = abilityArrayList.get(i).name;

        return names;
    }

    public String[] getAllDescriptions() {
        String[] desc = new String[abilityArrayList.size()];

        for (int i = 0; i < abilityArrayList.size(); i++)
            desc[i] = abilityArrayList.get(i).description;

        return desc;
    }
}
