package ssu.cs360.fight;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView fightTitle;
    private TextView heroTitleView;
    private String heroTitle;
    private TextView heroAbilitiesView1;
    private TextView heroAbilitiesView2;
    private TextView heroAbilitiesView3;
    private String heroAbilities1;
    private String heroAbilities2;
    private String heroAbilities3;
    private TextView heroReadyText;
    private TextView heroResetText;
    private TextView monsterTitleView;
    private String monsterTitle;
    private TextView monsterAbilitiesView1;
    private TextView monsterAbilitiesView2;
    private TextView monsterAbilitiesView3;
    private String monsterAbilities1;
    private String monsterAbilities2;
    private String monsterAbilities3;
    private TextView monsterReadyText;
    private TextView monsterResetText;
    private MainFragmentCallbacks mainFragmentCallbacks;

    boolean heroReady;
    boolean monsterReady;

    public MainFragment() {
        heroReady = false;
        heroTitle = "";
        heroAbilities1 = "";
        heroAbilities2 = "";
        heroAbilities3 = "";

        monsterReady = false;
        monsterTitle = "";
        monsterAbilities1 = "";
        monsterAbilities2 = "";
        monsterAbilities3 = "";
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mainFragmentCallbacks = (MainFragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AbilitySelectionCallbacks.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        fightTitle = (TextView) view.findViewById(R.id.fight_title);
        fightTitle.setText(R.string.fight_title_text);

        monsterTitleView = (TextView) view.findViewById(R.id.monster_display_title);
        monsterAbilitiesView1 = (TextView) view.findViewById(R.id.monster_ability_display_1);
        monsterAbilitiesView2 = (TextView) view.findViewById(R.id.monster_ability_display_2);
        monsterAbilitiesView3 = (TextView) view.findViewById(R.id.monster_ability_display_3);
        monsterReadyText = (TextView) view.findViewById(R.id.monster_ready_text);
        monsterResetText = (TextView) view.findViewById(R.id.reset_monster_text);

        monsterTitleView.setText(monsterTitle);
        monsterAbilitiesView1.setText(monsterAbilities1);
        monsterAbilitiesView2.setText(monsterAbilities2);
        monsterAbilitiesView3.setText(monsterAbilities3);

        heroTitleView = (TextView) view.findViewById(R.id.hero_display_title);
        heroAbilitiesView1 = (TextView) view.findViewById(R.id.hero_ability_display_1);
        heroAbilitiesView2 = (TextView) view.findViewById(R.id.hero_ability_display_2);
        heroAbilitiesView3 = (TextView) view.findViewById(R.id.hero_ability_display_3);
        heroReadyText = (TextView) view.findViewById(R.id.hero_ready_text);
        heroResetText = (TextView) view.findViewById(R.id.reset_hero_text);

        heroTitleView.setText(heroTitle);
        heroAbilitiesView1.setText(heroAbilities1);
        heroAbilitiesView2.setText(heroAbilities2);
        heroAbilitiesView3.setText(heroAbilities3);

        if (monsterReady) {
            monsterReadyText.setText(R.string.monster_ready);
            monsterResetText.setText(R.string.reset_monster_text);
            monsterResetText.setOnClickListener(this);
        }

        if (heroReady) {
            heroReadyText.setText(R.string.hero_ready);
            heroResetText.setText(R.string.reset_hero_text);
            heroResetText.setOnClickListener(this);
        }

        return view;
    }

    public void addHeroText(ArrayList<String> heroText) {
        if (heroText.size() >= 4) {
            heroTitle = heroText.get(0);
            heroAbilities1 = heroText.get(1);
            heroAbilities2 = heroText.get(2);
            heroAbilities3 = heroText.get(3);
            heroTitleView.setText(heroTitle);
            heroAbilitiesView1.setText(heroAbilities1);
            heroAbilitiesView2.setText(heroAbilities2);
            heroAbilitiesView3.setText(heroAbilities3);
            heroReadyText.setText(R.string.hero_ready);
            heroResetText.setText(R.string.reset_hero_text);
            heroResetText.setOnClickListener(this);
            heroReady = true;
            checkFightReady();
        }
    }

    public void addMonsterText(ArrayList<String> monsterText) {
        if (monsterText.size() >= 4) {
            monsterTitle = monsterText.get(0);
            monsterAbilities1 = monsterText.get(1);
            monsterAbilities2 = monsterText.get(2);
            monsterAbilities3 = monsterText.get(3);
            monsterTitleView.setText(monsterTitle);
            monsterAbilitiesView1.setText(monsterAbilities1);
            monsterAbilitiesView2.setText(monsterAbilities2);
            monsterAbilitiesView3.setText(monsterAbilities3);
            monsterReadyText.setText(R.string.monster_ready);
            monsterResetText.setText(R.string.reset_monster_text);
            monsterResetText.setOnClickListener(this);
            monsterReady = true;
            checkFightReady();
        }
    }

    private void checkFightReady() {
        if (heroReady && monsterReady) {
            fightTitle.setBackground(view.getResources().getDrawable(R.drawable.highlight));
            fightTitle.setOnClickListener(this);
        }
    }

    private void resetFightReady() {
        if (heroReady && monsterReady) {
            fightTitle.setBackground(view.getBackground());
        }
    }

    private void resetHero() {
        resetFightReady();
        heroReady = false;
        heroTitle = "";
        heroTitleView.setText("");
        heroAbilities1 = "";
        heroAbilities2 = "";
        heroAbilities3 = "";
        heroAbilitiesView1.setText("");
        heroAbilitiesView2.setText("");
        heroAbilitiesView3.setText("");
        heroReadyText.setText("");
        heroResetText.setText("");
    }

    private void resetMonster() {
        resetFightReady();
        monsterReady = false;
        monsterTitle = "";
        monsterTitleView.setText("");
        monsterAbilities1 = "";
        monsterAbilities2 = "";
        monsterAbilities3 = "";
        monsterAbilitiesView1.setText("");
        monsterAbilitiesView2.setText("");
        monsterAbilitiesView3.setText("");
        monsterReadyText.setText("");
        monsterResetText.setText("");
    }

    public void resetBattleReady() {
        resetMonster();
        resetHero();
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this.getActivity(), "FIGHT!", Toast.LENGTH_SHORT).show();
        switch (v.getId()) {
            case R.id.fight_title:
                if (heroReady && monsterReady)
                    mainFragmentCallbacks.startBattle();
                break;
            case R.id.reset_hero_text:
                if (heroReady)
                    resetHero();
                break;
            case R.id.reset_monster_text:
                if (monsterReady)
                    resetMonster();
                break;
        }
    }

    public static interface MainFragmentCallbacks {
        void startBattle();
    }
}