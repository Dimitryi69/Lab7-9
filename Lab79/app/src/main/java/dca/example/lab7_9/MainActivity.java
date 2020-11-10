package dca.example.lab7_9;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.navigation.NavigationView;

import dca.example.lab7_9.Fragments.ContentFragmentForFullInfo;
import dca.example.lab7_9.Fragments.ContentFragmentForList;

;

public class MainActivity extends AppCompatActivity implements dca.example.lab7_9.Fragments.ContentFragmentForList.OnFragmentInteractionListener {

    public static FragmentTransaction fTrans;
    Fragment frag1, frag2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frag1 = new ContentFragmentForList();
        frag2 = new ContentFragmentForFullInfo();
        fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.listFragment, frag1);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            fTrans.replace(R.id.listFragment2, frag2);
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    @Override
    public void onFragmentInteraction(int ID, String Title, String Type, String D, String Re, String I, int Time, String F) {
        ContentFragmentForFullInfo fragment = (ContentFragmentForFullInfo)getSupportFragmentManager().findFragmentById(R.id.listFragment2);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.replace(R.id.listFragment2, frag2);
            fTrans.addToBackStack(null);
            fTrans.commit();
            fragment.creater(ID, Title, Type, D, Re, I, Time, F);
        }
//        textViewAll = findViewById(R.id.all);
//        textViewIzbr = findViewById(R.id.izbr);
    }


}