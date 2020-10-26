package dca.example.lab7_9;

import android.content.Context;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<dca.example.lab7_9.ListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Recipe> recipeList;

    public ListAdapter(Context context, List<Recipe> recipes, OnRecipeClickListener onRecipeClickListener,OnCheckClickListener onCheckClickListener) {
        this.recipeList = recipes;
        this.inflater = LayoutInflater.from(context);
        this.onRecipeClickListener = onRecipeClickListener;
        this.onCheckClickListener=onCheckClickListener;
    }

    @Override
    public dca.example.lab7_9.ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(dca.example.lab7_9.ListAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.viewImage.setImageURI(Uri.parse(recipe.getPhoto()));
        holder.viewTitle.setText(recipe.getTitle());
        holder.viewDescription.setText(recipe.getDescription());
        holder.viewType.setText(recipe.getType());
        holder.viewTime.setText(recipe.getTime() + "");
        holder.viewID = recipe.getId();
        if(recipe.isFavorite()==1){
            holder.viewCheckBox.setChecked(true);
        }
    }


    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }
    public interface OnCheckClickListener {
        void onCheckClick(Recipe recipe,int i);
    }

    private OnRecipeClickListener onRecipeClickListener;
    private OnCheckClickListener onCheckClickListener;

//    public ListAdapter(OnRecipeClickListener onRecipeClickListener) {
//        this.onRecipeClickListener = onRecipeClickListener;
//    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        final ImageView viewImage;
        final TextView viewTitle, viewTime, viewType, viewDescription;
        int viewID;
        final CheckBox viewCheckBox;

        ViewHolder(View view) {
            super(view);
            this.viewImage = (ImageView) view.findViewById(R.id.listImage);
            this.viewTitle = view.findViewById(R.id.title);
            this.viewTime = view.findViewById(R.id.time);
            this.viewType = view.findViewById(R.id.type);
            this.viewDescription = view.findViewById(R.id.description);
            this.viewID = -1;
            this.viewCheckBox=view.findViewById(R.id.checkbox);
            this.viewCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recipe recipe = recipeList.get(getLayoutPosition());
                    if(viewCheckBox.isChecked()) {
                        onCheckClickListener.onCheckClick(recipe,1);
                    }else
                        onCheckClickListener.onCheckClick(recipe,0);
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Recipe recipe = recipeList.get(getLayoutPosition());
                    onRecipeClickListener.onRecipeClick(recipe);
                }
            });
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.add(0, 0, this.viewID, "Delete");//groupId, itemId, order, title
            menu.add(0, 1, this.viewID, "Edit");
        }
    }


}
