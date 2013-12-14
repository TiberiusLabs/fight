package ssu.cs360.fight;

import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        AbilitySelectionFragment.AbilitySelectionCallbacks,
        MainFragment.MainFragmentCallbacks,
        BattleFragment.BattleFragmentCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private MainFragment mMainFragment;
    private AbilitySelectionFragment mAbilitySelectionFragment;
    private BattleFragment mBattleFragment;

    private String json = "https://raw.github.com/KGBOperative/fight_json/master/abilities.json";
    private JSONArray jArray;
    private Abilities meleeAbilities;
    private Abilities rangedAbilities;
    private Abilities magicAbilities;
    private Hero hero;
    private Monster monster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
                .findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mMainFragment = new MainFragment();
        getFragmentManager().beginTransaction().add(R.id.container, mMainFragment).commit();

        meleeAbilities = new Abilities();
        rangedAbilities = new Abilities();
        magicAbilities = new Abilities();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(json, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
            try {
                jArray = new JSONArray(response);
                for (int i = 0; i < jArray.length(); ++i) {

                    JSONObject jObject = jArray.getJSONObject(i);
                    Ability ability;

                    if (jObject.getString("type").equals("ATTACK"))
                        ability = new AttackAbility();
                    else if (jObject.getString("type").equals("HEAL"))
                        ability = new HealAbility();
                    else if (jObject.getString("type").equals("BUFF"))
                        ability = new BuffAbility();
                    else if (jObject.getString("type").equals("DEBUFF"))
                        ability = new DebuffAbility();
                    else
                        continue;

                    ability.SetStatType(jObject.getString("stat"));
                    ability.SetName(jObject.getString("name"));
                    ability.SetDescription(jObject.getString("description"));
                    ability.SetPlayerBattleText(jObject.getString("player_battle_text"));
                    ability.SetEnemyBattleText(jObject.getString("enemy_battle_text"));
                    ability.SetPower(jObject.getInt("power"));
                    ability.SetCost(jObject.getInt("cost"));

                    if (jObject.getString("character_type").equals("melee"))
                        meleeAbilities.add(ability);
                    else if (jObject.getString("character_type").equals("ranged"))
                        rangedAbilities.add(ability);
                    else if (jObject.getString("character_type").equals("magic"))
                        magicAbilities.add(ability);
                }
                //Toast.makeText(getApplicationContext(), "FIGHT!", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Log.d("JSON Parse", e.toString());
            }
            }
        });
    }

    @Override
    public void startBattle() {
        mBattleFragment = new BattleFragment(hero, monster);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.container, mBattleFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(boolean heroSelected, int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if (position >= 0) {
            String classTypeName;
            if (heroSelected) {
                classTypeName = mNavigationDrawerFragment.getSelectedHero(position);

                switch (classTypeName.charAt(0)) {
                    case 'R':
                        hero = new Ranged();
                        break;
                    case 'M':
                        hero = new Magic();
                        break;
                    case 'F':
                        hero = new Melee();
                        break;
                    default:
                        hero = new Melee();
                        break;
                }

                mAbilitySelectionFragment = new AbilitySelectionFragment(
                        classTypeName, getClassAbilities(classTypeName));
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mAbilitySelectionFragment)
                        .addToBackStack("AbilitySelector")
                        .commit();
            }
            else {
                classTypeName = mNavigationDrawerFragment.getSelectedMonster(position);
                switch (classTypeName.charAt(0)) {
                    case 'A':
                        monster = new Assassin(rangedAbilities, classTypeName);
                        break;
                    case 'W':
                        monster = new Warlock(magicAbilities, classTypeName);
                        break;
                    case 'M':
                        monster = new Mercenary(meleeAbilities, classTypeName);
                        break;
                    default:
                        monster = new Assassin(rangedAbilities, "Assassin");
                        break;
                }
                ArrayList<String> abilities = new ArrayList<String>(
                        Arrays.asList(monster.getAbilities().getAllNames()));
                abilities.add(0, monster.getName());
                mMainFragment.addMonsterText(abilities);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, mMainFragment)
                        .commit();
            }
        }
        else {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, mMainFragment)
                    .commit();
        }
    }

    @Override
    public void exitAbilitySelection(boolean cancelled) {
        FragmentManager fragmentManager = getFragmentManager();
        if (!cancelled) {
            ArrayList<String> abilities = new ArrayList<String>(
                    Arrays.asList(mAbilitySelectionFragment.getAbilities().getAllNames()));
            abilities.add(0, mAbilitySelectionFragment.getClassTypeName());
            mMainFragment.addHeroText(abilities);
            hero.addAbilities(mAbilitySelectionFragment.getAbilities());
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, mMainFragment)
                .commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setTitle(mTitle);
    }

    Abilities getClassAbilities(String className) {
        if (className.equals("Ranger") || className.equals("Assassin"))
            return rangedAbilities;
        else if (className.equals("Mage") || className.equals("Warlock"))
            return magicAbilities;
        else
            return meleeAbilities;
    }

    @Override
    public void returnToMainFromBattle() {
        mMainFragment.resetBattleReady();
        getFragmentManager().beginTransaction().replace(R.id.container, mMainFragment).commit();
    }
}
