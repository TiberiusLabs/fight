package ssu.cs360.fight;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by kgboperative on 12/1/13.
 */
public class AbilitySelectionFragment extends Fragment
        implements AdapterView.OnItemClickListener, Button.OnClickListener, AdapterView.OnItemLongClickListener {

    private AbilitySelectionCallbacks abilitySelectionCallbacks;

    private View AbilitySelectionView;

    private String classTypeName;
    private Abilities abilities;
    private ArrayList<Ability> selectedAbilities;

    AbilitySelectionFragment() {
        classTypeName = "Random Class Type Abilities";
        abilities = new Abilities();
        selectedAbilities = new ArrayList<Ability>();

        for (int i = 0; i < 6; i++) {
            Ability ability = new Ability();
            ability.name = "Random Ability #" + i;
            ability.description = "Random Description #" + i;
            abilities.add(ability);
        }
    }

    AbilitySelectionFragment(String classTypeName, Abilities classAbilities) {
        this.classTypeName = classTypeName;
        abilities = classAbilities;
        selectedAbilities = new ArrayList<Ability>();
    }

    AbilitySelectionFragment(Abilities abilities) {
        this.abilities = abilities;
        selectedAbilities = new ArrayList<Ability>();
    }

    public String getClassTypeName() {
        return classTypeName;
    }

    public Abilities getAbilities() {
        Abilities selectedAbilities = new Abilities();
        for (Ability a : this.selectedAbilities)
            selectedAbilities.add(a);
        return selectedAbilities;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AbilitySelectionView = inflater.inflate(R.layout.ability_selection_fragment, container, false);

        ListView abilityListView = (ListView) AbilitySelectionView.findViewById(R.id.ability_selection_list);
        abilityListView.setAdapter(new ArrayAdapter<String>(
                AbilitySelectionView.getContext(),
                android.R.layout.simple_list_item_1,
                abilities.getAllNames()));
        abilityListView.setOnItemClickListener(this);
        abilityListView.setOnItemLongClickListener(this);

        TextView classTypeText = (TextView) AbilitySelectionView.findViewById(R.id.class_type_title_text);
        classTypeText.setText(classTypeName + " Abilities");

        Button acceptButton = (Button) AbilitySelectionView.findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(this);

        Button cancelButton = (Button) AbilitySelectionView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);

        return AbilitySelectionView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            abilitySelectionCallbacks = (AbilitySelectionCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AbilitySelectionCallbacks.");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        abilitySelectionCallbacks = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!selectedAbilities.contains(abilities.get(position))) {
            if (selectedAbilities.size() < 3) {
                selectedAbilities.add(abilities.get(position));
                view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            }
        }
        else {
            selectedAbilities.remove(abilities.get(position));
            view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accept_button:
                if (selectedAbilities.size() == 3)
                    abilitySelectionCallbacks.exitAbilitySelection(false);
                else
                    Toast.makeText(AbilitySelectionView.getContext(), "You must select three abilities first!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancel_button:
                abilitySelectionCallbacks.exitAbilitySelection(true);
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(AbilitySelectionView.getContext());
        alert.setMessage(abilities.getDescriptionAt(position)).setTitle(abilities.getNameAt(position));
        alert.show();
        return true;
    }

    public static interface AbilitySelectionCallbacks {
        void exitAbilitySelection(boolean cancelled);
    }

}