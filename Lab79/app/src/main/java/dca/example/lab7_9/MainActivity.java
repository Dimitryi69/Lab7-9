package dca.example.lab7_9;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dca.example.lab7_9.Fragments.ContentFragmentForFullInfo;
import dca.example.lab7_9.Fragments.ContentFragmentForList;

;

public class MainActivity extends AppCompatActivity implements dca.example.lab7_9.Fragments.ContentFragmentForList.OnFragmentInteractionListener {

//    FragmentTransaction fragmentTransaction;
//    Fragment fragment;
//    ContentFragmentForFullInfo contentFragmentForFullInfo;
//    ContentFragmentForList contentFragmentForList;
//

//    TextView textViewAll;
//    TextView textViewIzbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(int ID, String Title, String Type, String D, String Re, String I, int Time, String F) {
        ContentFragmentForFullInfo fragment = (ContentFragmentForFullInfo) getSupportFragmentManager()
                .findFragmentById(R.id.listFragment2);
        if (fragment != null && fragment.isInLayout()) {
            fragment.creater(ID, Title, Type, D, Re, I, Time, F);
        }
//        textViewAll = findViewById(R.id.all);
//        textViewIzbr = findViewById(R.id.izbr);
    }


}