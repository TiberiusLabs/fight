package ssu.cs360.fight;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class BattleFragment extends Fragment implements View.OnClickListener {

    Character player;
    Character enemy;
    View view;
    TextView enemyNameView;
    TextView enemyHPView;
    TextView enemyHPBarView;
    ScrollView scrollView;
    LinearLayout battleTextLayout;
    TextView playerNameView;
    TextView playerHPView;
    TextView playerHPBarView;
    TextView playerSPView;
    TextView playerSPBarView;
    Button attackButton;
    Button ability1Button;
    Button ability2Button;
    Button ability3Button;
    TextView ability1SPView;
    TextView ability2SPView;
    TextView ability3SPView;
    BattleFragmentCallbacks mBattleFragmentCallbacks;

    public BattleFragment(Character player, Character enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mBattleFragmentCallbacks = (BattleFragmentCallbacks) activity;
        } catch (ClassCastException cce) {
            throw new ClassCastException("Class must implement BattleFragmentCallbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_battle, container, false);

        enemyNameView = (TextView) view.findViewById(R.id.enemyNameTextView);
        enemyHPView = (TextView) view.findViewById(R.id.enemyHP);
        enemyHPBarView = (TextView) view.findViewById(R.id.enemyHPBar);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        battleTextLayout = (LinearLayout) view.findViewById(R.id.battleTextLayout);
        playerNameView = (TextView) view.findViewById(R.id.playerNameTextView);
        playerHPView = (TextView) view.findViewById(R.id.playerHP);
        playerHPBarView = (TextView) view.findViewById(R.id.playerHPBar);
        playerSPView = (TextView) view.findViewById(R.id.playerSP);
        playerSPBarView = (TextView) view.findViewById(R.id.playerSPBar);
        attackButton = (Button) view.findViewById(R.id.attackButton);
        ability1Button = (Button) view.findViewById(R.id.ability1Button);
        ability2Button = (Button) view.findViewById(R.id.ability2Button);
        ability3Button = (Button) view.findViewById(R.id.ability3Button);
        ability1SPView = (TextView) view.findViewById(R.id.ability1SPView);
        ability2SPView = (TextView) view.findViewById(R.id.ability2SPView);
        ability3SPView = (TextView) view.findViewById(R.id.ability3SPView);

        if (player.getHP() == 0 || enemy.getHP() == 0) {
            player.heal(player.getMaxHP());
            player.restoreSP(player.getMaxSP());
            enemy.heal(enemy.getMaxHP());
            enemy.restoreSP(enemy.getMaxSP());
        }

        enemyNameView.setText(enemy.getName());
        playerNameView.setText(player.getName());

        updateEnemyHP();
        updatePlayerHP();
        updatePlayerSP();

        ability1Button.setText(player.getAbilities().get(0).GetName());
        ability2Button.setText(player.getAbilities().get(1).GetName());
        ability3Button.setText(player.getAbilities().get(2).GetName());

        ability1SPView.setText(player.getAbilities().get(0).GetCost() + " SP");
        ability2SPView.setText(player.getAbilities().get(1).GetCost() + " SP");
        ability3SPView.setText(player.getAbilities().get(2).GetCost() + " SP");

        attackButton.setOnClickListener(this);
        ability1Button.setOnClickListener(this);
        ability2Button.setOnClickListener(this);
        ability3Button.setOnClickListener(this);

        addBattleText("Your enemy stares you straight in the eyes. What do you do?");

        return view;
    }

    public void updateEnemyHP() {
        if (enemy.getHP() == 0) {
            enemyNameView.setText(enemyNameView.getText() + " x_x");
            onCharacterDeath(enemy);
        }

        String health = "Enemy HP: " + enemy.getHP() + "/" + enemy.getMaxHP() + " ";
        String healthBar = "[";

        for (int i = 0; i < 15; i++)
            healthBar += (enemy.getHP() > i * enemy.getMaxHP() / 15) ? "|" : " ";

        healthBar += "]";

        enemyHPView.setText(health);
        enemyHPBarView.setText(healthBar);
    }

    public void updatePlayerHP() {
        if (player.getHP() == 0) {
            playerNameView.setText(playerNameView.getText() + " x_x");
            onCharacterDeath(player);
        }

        String health = "Player HP: " + player.getHP() + "/" + player.getMaxHP() + " ";
        String healthBar = "[";

        for (int i = 0; i < 15; i++)
            healthBar += (player.getHP() > i * player.getMaxHP() / 15) ? "|" : " ";

        healthBar += "]";

        playerHPView.setText(health);
        playerHPBarView.setText(healthBar);
    }

    public void updatePlayerSP() {
        String SP = "Player SP: " + player.getSP() + "/" + player.getMaxSP() + " ";
        String SPBar = "[";

        for (int i = 0; i < 15; i++)
            SPBar += (player.getSP() > i * player.getMaxSP() / 15) ? "|" : " ";

        SPBar += "]";

        playerSPView.setText(SP);
        playerSPBarView.setText(SPBar);
    }

    public void onCharacterDeath(Character character) {
        if (character.equals(player)) {
            addBattleText("You are dead.\n");
        } else {
            addBattleText("The " + enemy.getName() + " falls to the ground, dead.\n");
        }

        battleOver();
    }

    public void addBattleText(String battleText) {
        TextView textView = new TextView(view.getContext());

        if (battleText.endsWith("damage!"))
            textView.setTextColor(Color.RED);
        else if (battleText.endsWith("health!"))
            textView.setTextColor(Color.GREEN);

        textView.setText(battleText);
        battleTextLayout.addView(textView);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view.getId() != R.id.attackButton && getAbilityCost(view) > player.getSP()) {
            Toast.makeText(view.getContext(), "Not enough SP", Toast.LENGTH_SHORT).show();
        } else {
            if (view.getId() == R.id.attackButton)
                playerAttack();
            else
                useAbility(view);

            upkeep();

            if (enemy.isAlive()) {
                enemyTurn();
                upkeep();
            }
        }
    }

    public void playerAttack() {
        int damage = player.attack(enemy);
        addBattleText("\n" + player.getAttackText(enemy.getName()));

        switch (damage) {
            case -1:
                addBattleText("She evades your attack!");
                break;
            case 0:
                addBattleText("You miss!");
                break;
            default:
                addBattleText(damage + " damage!");
        }
    }

    public int getAbilityCost(View view) {
        switch (view.getId()) {
            case R.id.ability1Button:
                return player.getAbilities().get(0).GetCost();
            case R.id.ability2Button:
                return player.getAbilities().get(1).GetCost();
            case R.id.ability3Button:
                return player.getAbilities().get(2).GetCost();
            default:
                return 0;
        }
    }

    public void useAbility(View view) {

        player.spendSP(getAbilityCost(view));
        int damage = 0;
        String type = "";
        String stat = "";

        switch (view.getId()) {
            case R.id.ability1Button:
                damage = player.getAbilities().get(0).Execute(player, enemy);
                type = player.getAbilities().get(0).GetActionType();
                stat = player.getAbilities().get(0).GetStatType().toLowerCase();
                addBattleText("\n" + player.getAbilities().get(0).GetPlayerBattleText());
                break;
            case R.id.ability2Button:
                damage = player.getAbilities().get(1).Execute(player, enemy);
                type = player.getAbilities().get(1).GetActionType();
                stat = player.getAbilities().get(1).GetStatType().toLowerCase();
                addBattleText("\n" + player.getAbilities().get(1).GetPlayerBattleText());
                break;
            case R.id.ability3Button:
                damage = player.getAbilities().get(2).Execute(player, enemy);
                type = player.getAbilities().get(2).GetActionType();
                stat = player.getAbilities().get(2).GetStatType().toLowerCase();
                addBattleText("\n" + player.getAbilities().get(2).GetPlayerBattleText());
                break;
        }

        if (type.equals("ATTACK")) {

            switch (damage) {
                case 0:
                    addBattleText("You miss!");
                    break;
                default:
                    addBattleText(damage + " damage!");
            }

        } else if (type.equals("HEAL")) {

            addBattleText(damage + " health!");

        } else if (type.equals("BUFF")) {

            stat = stat.substring(0, 1).toUpperCase() + stat.substring(1);
            addBattleText("Your " + stat + " has been raised!");

        } else if (type.equals("DEBUFF")) {

            switch (damage) {
                case 0:
                    addBattleText("You miss!");
                    break;
                default:
                    stat = stat.substring(0, 1).toUpperCase() + stat.substring(1);
                    addBattleText("Your enemy's " + stat + " has been lowered!");
            }
        }
    }

    public void enemyTurn() {
        int choice = new Dice(5).Roll();

        if (choice <= 3 && enemy.getSP() >= enemy.getAbilities().get(choice -1).GetCost())
            enemyUseAbility(choice - 1);
        else
            enemyAttack();
    }

    public void enemyAttack() {
        int damage = enemy.attack(player);
        addBattleText("\n" + enemy.getAttackText(enemy.getName()));

        switch (damage) {
            case -1:
                addBattleText("You evade the attack!");
                break;
            case 0:
                addBattleText("She misses!");
                break;
            default:
                addBattleText(damage + " damage!");
        }
    }

    public void enemyUseAbility(int choice) {

        enemy.spendSP(enemy.getAbilities().get(choice).GetCost());
        int damage = enemy.getAbilities().get(choice).Execute(enemy, player);
        String type = enemy.getAbilities().get(choice).GetActionType();
        String stat = enemy.getAbilities().get(choice).GetStatType().toLowerCase();
        addBattleText("\n" + enemy.getAbilities().get(choice).GetEnemyBattleText());

        if (type.equals("ATTACK")) {

            switch (damage) {
                case 0:
                    addBattleText("She misses!");
                    break;
                default:
                    addBattleText(damage + " damage!");
            }

        } else if (type.equals("HEAL")) {

            addBattleText(damage + " health!");

        } else if (type.equals("BUFF")) {

            stat = stat.substring(0, 1).toUpperCase() + stat.substring(1);
            addBattleText("Your enemy's " + stat + " has been raised!");

        } else if (type.equals("DEBUFF")) {

            switch (damage) {
                case 0:
                    addBattleText("She misses!");
                    break;
                default:
                    stat = stat.substring(0, 1).toUpperCase() + stat.substring(1);
                    addBattleText("Your " + stat + " has been lowered!");
            }
        }
    }

    public void upkeep() {
        updateEnemyHP();
        updatePlayerHP();
        updatePlayerSP();
    }

    public void battleOver() {
        attackButton.setClickable(false);
        ability1Button.setClickable(false);
        ability2Button.setClickable(false);
        ability3Button.setClickable(false);

        Button endButton = new Button(view.getContext());
        endButton.setBackground(getResources().getDrawable(R.drawable.back_button));
        endButton.setTextColor(getResources().getColor(R.color.off_white));
        endButton.setText("Go Back");
        battleTextLayout.addView(endButton);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBattleFragmentCallbacks.returnToMainFromBattle();
                //getActivity().getFragmentManager().popBackStack();
            }
        });
    }

    public interface BattleFragmentCallbacks {
        public void returnToMainFromBattle();
    }
}
