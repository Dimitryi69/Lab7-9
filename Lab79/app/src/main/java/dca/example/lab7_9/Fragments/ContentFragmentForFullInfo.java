package dca.example.lab7_9.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import dca.example.lab7_9.R;
import dca.example.lab7_9.MainActivity;
import dca.example.lab7_9.Recipe;

public class ContentFragmentForFullInfo extends Fragment {

    View view;
    Recipe recipe;

    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_full_info, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                super.getActivity().getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_full_info, container, false);

        Button updateButton = view.findViewById(R.id.buttonBack);

        updateButton.setOnClickListener(v -> {
//            Intent intent = new Intent(view.getContext(), MainActivity.class);
//            startActivity(intent);
//            ContentFragmentForFullInfo.super.getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            MainActivity.fTrans = ContentFragmentForFullInfo.super.getActivity().getSupportFragmentManager().beginTransaction();
            MainActivity.fTrans.replace(R.id.listFragment, new ContentFragmentForList());
            MainActivity.fTrans.addToBackStack(null);
            MainActivity.fTrans.commit();
        });
        updateButton.setVisibility(View.VISIBLE);
        Bundle arguments = super.getActivity().getIntent().getExtras();

        if (arguments != null) {
            recipe = new dca.example.lab7_9.Recipe(arguments.getInt("ID"), arguments.getString("Title"), arguments.getString("Type"), arguments.getString("D"), arguments.getString("R"), arguments.getString("I"), arguments.getInt("Time"), arguments.getString("F"),0);

            ((TextView) view.findViewById(R.id.title)).setText(recipe.getTitle() + "\n");
            ((TextView) view.findViewById(R.id.type)).setText(recipe.getType() + "\n");
            ((TextView) view.findViewById(R.id.description)).setText(recipe.getDescription() + "\n");
            ((TextView) view.findViewById(R.id.time)).setText(recipe.getTime() + " мин.\n");
            ((TextView) view.findViewById(R.id.recipe)).setText(recipe.getRecipe() + "\n");
            ((TextView) view.findViewById(R.id.viewIngredients)).setText(recipe.getIngredients());
            ((ImageView) view.findViewById(R.id.image)).setImageURI(Uri.parse(recipe.getPhoto()));
        }
        else
            updateButton.setVisibility(View.INVISIBLE);

        return view;
    }

    public void creater(int ID, String Title, String Type, String D, String Re, String I, int Time, String F) {
        recipe = new dca.example.lab7_9.Recipe(ID, Title, Type, D, Re, I, Time, F,0);
        ((TextView) view.findViewById(R.id.title)).setText(recipe.getTitle() + "\n");
        ((TextView) view.findViewById(R.id.type)).setText(recipe.getType() + "\n");
        ((TextView) view.findViewById(R.id.description)).setText(recipe.getDescription() + "\n");
        ((TextView) view.findViewById(R.id.time)).setText(recipe.getTime() + " мин.\n");
        ((TextView) view.findViewById(R.id.recipe)).setText(recipe.getRecipe() + "\n");
        ((TextView) view.findViewById(R.id.viewIngredients)).setText(recipe.getIngredients());
        ((ImageView) view.findViewById(R.id.image)).setImageURI(Uri.parse(recipe.getPhoto()));
    }
}
