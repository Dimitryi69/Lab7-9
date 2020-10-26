package dca.example.lab7_9.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import dca.example.lab7_9.DatabaseHelper;
import dca.example.lab7_9.FullInfo;
import dca.example.lab7_9.ListAdapter;
import dca.example.lab7_9.MainActivity;
import dca.example.lab7_9.R;
import dca.example.lab7_9.Recipe;

import static android.app.Activity.RESULT_OK;


public class ContentFragmentForList extends Fragment {


    int changeManager;
    dca.example.lab7_9.Fragments.ContentFragmentForList contentFragmentForList;
    ContentFragmentForFullInfo contentFragmentForFullInfo;
    View view;
    String path;
    ArrayList<Recipe> recipeList;
    FragmentTransaction fragmentTransaction;
    RecyclerView.LayoutManager layoutManager;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;

    private OnFragmentInteractionListener mListener;

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteRecipe(item.getOrder());
                    }
                });
                builder.setNegativeButton("No", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            case 1:
                addOrChangeRecipe(item.getOrder());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recipe, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                addOrChangeRecipe(-1);
                return true;
            case R.id.item2:
                recipeList = new ArrayList<>();
                cursor = new SelectAllFromBD().doInBackground(DatabaseHelper.TABLE_RECIPE_NAME);//db.rawQuery("select * from " + DatabaseHelper.TABLE_RECIPE_NAME, null);
                if (cursor.moveToFirst()) {
                    do {
                        recipeList.add(new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8)));
                    } while (cursor.moveToNext());
                }
                newAdapterForList();
                return true;
            case R.id.item3:
                recipeList = new ArrayList<>();
                cursor = new SelectAllFromBD().doInBackground(DatabaseHelper.TABLE_RECIPE_NAME);// db.rawQuery("select * from " + DatabaseHelper.TABLE_RECIPE_NAME, null);
                if (cursor.moveToFirst()) {
                    do {
                        if (cursor.getInt(8) == 1)
                            recipeList.add(new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8)));
                    } while (cursor.moveToNext());
                }
                newAdapterForList();
                return true;
            case R.id.item4:
                if (changeManager == 0) {
                    layoutManager = linearLayoutManager;
                    changeManager = 1;
                } else {
                    if (changeManager==1) {
                        layoutManager = gridLayoutManager;
                        changeManager=0;
                    }
                }
                newAdapterForList();
                return true;
            case R.id.item5:
                super.getActivity().getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(int ID, String Title, String Type, String D, String Re, String I, int Time, String F);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }


    private void deleteRecipe(int id) {
        for (Recipe r : recipeList) {
            if (r.getId() == id) {
                File file = new File(r.getPhoto());
                db = databaseHelper.getWritableDatabase();
                new DeleteFromBD().doInBackground(id);
                db = databaseHelper.getReadableDatabase();
                file.delete();
                recipeList.remove(r);

                newAdapterForList();
                return;
            }
        }
    }

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        changeManager = 1;
        linearLayoutManager = new LinearLayoutManager(super.getActivity());
        gridLayoutManager = new GridLayoutManager(super.getActivity(), 2);
        layoutManager = linearLayoutManager;
        recipeList = new ArrayList<>();
        path = super.getActivity().getFilesDir().getPath();
        databaseHelper = new DatabaseHelper(super.getActivity().getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        cursor = new SelectAllFromBD().doInBackground(DatabaseHelper.TABLE_RECIPE_NAME);//cursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_RECIPE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                recipeList.add(new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8)));
            } while (cursor.moveToNext());
        }
        newAdapterForList();
        contentFragmentForFullInfo = new ContentFragmentForFullInfo();
        contentFragmentForList = new dca.example.lab7_9.Fragments.ContentFragmentForList();
        return view;
    }

    public void newAdapterForList() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter(view.getContext(), recipeList, new ListAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(Recipe recipe) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mListener.onFragmentInteraction(recipe.getId(), recipe.getTitle(), recipe.getType(), recipe.getDescription(), recipe.getRecipe(), recipe.getIngredients(), recipe.getTime(), recipe.getPhoto());

                } else {
//                      fragmentTransaction.remove(new ContentFragmentForList());
//                    Intent intent = new Intent(view.getContext(), FullInfo.class);
//                    intent.putExtra("ID", recipe.getId());
//                    intent.putExtra("D", recipe.getDescription());
//                    intent.putExtra("F", recipe.getPhoto());
//                    intent.putExtra("I", recipe.getIngredients());
//                    intent.putExtra("R", recipe.getRecipe());
//                    intent.putExtra("Time", recipe.getTime());
//                    intent.putExtra("Title", recipe.getTitle());
//                    intent.putExtra("Type", recipe.getType());
//                    startActivity(intent);
//                    dca.example.lab7_9.Fragments.ContentFragmentForList.super.getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);

                    view.findViewById(R.id.listFragment);
                    MainActivity.fTrans = ContentFragmentForList.super.getActivity().getSupportFragmentManager().beginTransaction();
                    ContentFragmentForFullInfo fragment = new ContentFragmentForFullInfo();
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", recipe.getId());
                    bundle.putString("D", recipe.getDescription());
                    bundle.putString("F", recipe.getPhoto());
                    bundle.putString("I", recipe.getIngredients());
                    bundle.putString("R", recipe.getRecipe());
                    bundle.putInt("Time", recipe.getTime());
                    bundle.putString("Title", recipe.getTitle());
                    bundle.putString("Type", recipe.getType());
                    fragment.setArguments(bundle);
                    MainActivity.fTrans.replace(R.id.listFragment, fragment);
                    MainActivity.fTrans.addToBackStack(null);
                    MainActivity.fTrans.commit();
                }
            }
        }, new ListAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(Recipe recipe, int i) {
                databaseHelper.insertRecipe(db, recipe.getId(), recipe.getTitle(), recipe.getType(), recipe.getDescription(), recipe.getRecipe(), recipe.getIngredients(), recipe.getTime(), recipe.getFoto(), i);
                new DeleteFromBD().doInBackground(recipe.id);
                recipe.setFavorite(i);
                new SaveInBD().doInBackground(recipe);

            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(layoutManager);
        registerForContextMenu(recyclerView);
    }

    View dialogView;

    ArrayList<String> ingredientsChecked;
    ArrayList<String> ingredientsList;
    boolean checkPhoto;

    private void addOrChangeRecipe(final int inputID) {
        ingredientsChecked = new ArrayList<>();
        checkPhoto = false;
        ingredientsList = new ArrayList<>();
        ingredientsList.add("Onions");
        ingredientsList.add("Potato");
        ingredientsList.add("Pork");
        ingredientsList.add("Berries");
        ingredientsList.add("Butter");
        ingredientsList.add("Peper");
        LayoutInflater li = LayoutInflater.from(view.getContext());
        dialogView = li.inflate(R.layout.dialog_add, null);

        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(view.getContext());

        LinearLayout linearLayout = dialogView.findViewById(R.id.layoutIngredients);
        for (String p : ingredientsList) {
            CheckBox checkBox = new CheckBox(view.getContext());
            checkBox.setText(p);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkBoxClick = (CheckBox) view;
                    if (checkBoxClick.isChecked()) {
                        ingredientsChecked.add(checkBoxClick.getText().toString());
                    } else {
                        ingredientsChecked.remove(checkBoxClick.getText().toString());
                    }
                }
            });
            linearLayout.addView(checkBox);
        }
        Button buttonAddPhoto = dialogView.findViewById(R.id.AddPhoto);
        buttonAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPhoto();
            }
        });

        if (inputID != -1) {
            Recipe recipe = null;
            for (Recipe r : recipeList) {
                if (r.getId() == inputID) {
                    recipe = r;
                    break;
                }
            }

            ((EditText) dialogView.findViewById(R.id.title)).setText(recipe.getTitle());
            ((EditText) dialogView.findViewById(R.id.type)).setText(recipe.getType());
            ((EditText) dialogView.findViewById(R.id.description)).setText(recipe.getDescription());
            ((EditText) dialogView.findViewById(R.id.time)).setText(recipe.getTime() + "");
            ((EditText) dialogView.findViewById(R.id.recipe)).setText(recipe.getRecipe());
            ((ImageView) dialogView.findViewById(R.id.image)).setImageURI(Uri.parse(recipe.getPhoto()));
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) linearLayout.getChildAt(i);

                if (recipe.getIngredients().indexOf(checkBox.getText().toString()) != -1) {
                    checkBox.setChecked(true);
                    ingredientsChecked.add(checkBox.getText().toString());
                }
            }

        }


        mDialogBuilder.setView(dialogView);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Сохранить", null)
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        final AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button button = ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    Integer time = null;

                    @Override
                    public void onClick(View view) {
                        boolean check = true;
                        if (((EditText) dialogView.findViewById(R.id.time)).getText().toString().intern() == "") {
                            dialogs("Введите время");
                            check = false;
                        } else {
                            try {
                                time = Integer.parseInt(((EditText) dialogView.findViewById(R.id.time)).getText().toString());
                            } catch (NumberFormatException a) {
                                dialogs("Введите корректное время(целое число)");
                                check = false;
                            }
                        }
                        if (check) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
                            builder.setMessage("Уверены, что хотите cохранить?");
                            builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String title = ((EditText) dialogView.findViewById(R.id.title)).getText().toString();
                                    String description = ((EditText) dialogView.findViewById(R.id.description)).getText().toString();
                                    String type = ((EditText) dialogView.findViewById(R.id.type)).getText().toString();
                                    String recipe = ((EditText) dialogView.findViewById(R.id.recipe)).getText().toString();
                                    String ingredients = "";
                                    for (String s : ingredientsChecked) {
                                        ingredients += s + "\n";
                                    }
                                    int id;
                                    if (recipeList != null && !recipeList.isEmpty())
                                        id = recipeList.get(recipeList.size() - 1).getId() + 1;
                                    else {
                                        id = 0;
                                        recipeList = new ArrayList<>();
                                    }
                                    Recipe recipeClass = new Recipe(id, title, type, description, recipe, ingredients, time, path + "/" + id + ":" + title + ".png", 0);

                                    if (inputID != -1 && checkPhoto) {
                                        for (Recipe r : recipeList) {
                                            if (r.getId() == inputID) {
                                                File file = new File(r.getPhoto());
                                                file.delete();
                                                recipeList.remove(r);
                                                break;
                                            }

                                        }
                                    }
                                    if (inputID != -1 && !checkPhoto) {
                                        for (Recipe r : recipeList) {
                                            if (r.getId() == inputID) {
                                                File file = new File(r.getPhoto());
                                                file.renameTo(new File(path, id + ":" + title + ".png"));
                                                recipeList.remove(r);
                                                break;
                                            }

                                        }
                                    } else {
                                        String filename = recipeClass.getId() + ":" + recipeClass.getTitle();

                                        try {
                                            FileOutputStream fos = null;
                                            try {
                                                fos = new FileOutputStream(new File(path, filename + ".png"));
                                                bitmapPhoto.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                            } finally {
                                                if (fos != null) fos.close();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    db = databaseHelper.getWritableDatabase();

//                                    saveToJSON();
                                    if (inputID == -1) {
//                                        databaseHelper.insertRecipe(db, id, title, type, description, recipe, ingredients, time, path + "/" + id + ":" + title + ".png", 0);
                                        new SaveInBD().doInBackground(recipeClass);
                                    } else {
                                        databaseHelper.insertRecipe(db, id, title, type, description, recipe, ingredients, time, path + "/" + id + ":" + title + ".png", 0);
                                        new DeleteFromBD().doInBackground(inputID);
                                        new SaveInBD().doInBackground(recipeClass);
                                    }
                                    db = databaseHelper.getReadableDatabase();


                                    recipeList = new ArrayList<>();
                                    cursor = new SelectAllFromBD().doInBackground(DatabaseHelper.TABLE_RECIPE_NAME);// cursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_RECIPE_NAME, null);
                                    if (cursor.moveToFirst()) {
                                        do {
                                            recipeList.add(new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8)));
                                        } while (cursor.moveToNext());
                                    }
                                    newAdapterForList();

                                    newAdapterForList();
                                    alertDialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("Нет", null);

                            AlertDialog alertDialogForCheck = builder.create();
                            alertDialogForCheck.show();

                        }
                    }
                });
            }
        });
        alertDialog.show();


    }

    Bitmap bitmapPhoto;
    byte[] bytesBitmap;
    String photoPath;
    Uri photoUri;

    public void dialogs(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(s);
        builder.setPositiveButton("Ok", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void AddPhoto() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {
                        photoUri = data.getData();
                        InputStream imageStream = super.getActivity().getContentResolver().openInputStream(photoUri);
                        bitmapPhoto = BitmapFactory.decodeStream(imageStream);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmapPhoto.compress(Bitmap.CompressFormat.PNG, 20, stream);
                        photoPath = photoUri.toString();
                        bytesBitmap = stream.toByteArray();
                        ((ImageView) dialogView.findViewById(R.id.image)).setImageBitmap(bitmapPhoto);
                        checkPhoto = true;
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    class SaveInBD extends AsyncTask<Recipe, Void, Void> {
        @Override
        protected Void doInBackground(Recipe... recipes) {

            Recipe recipe = recipes[0];
            databaseHelper.insertRecipe(db, recipe.getId(), recipe.getTitle(), recipe.getType(), recipe.getDescription(), recipe.getRecipe(), recipe.getIngredients(), recipe.getTime(), recipe.getPhoto(), recipe.isFavorite());
            return null;
        }
    }
    class DeleteFromBD extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            databaseHelper.deleteRecipe(db, integers[0]);
            return null;

        }
    }
    class SelectAllFromBD extends AsyncTask<String, Void, Cursor> {
        @Override
        protected Cursor doInBackground(String... strings) {
            return db.rawQuery("select * from " + strings[0], null);
        }
    }
}