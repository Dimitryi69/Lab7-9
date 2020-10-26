package dca.example.lab7_9;


public class Recipe {
    public String title;
    public String type;
    public String description;
    public int time;
    public String photo;
    public String ingredients;
    public String recipe;
    public int favorite;

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getType() {
        return type;
    }

    public int getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getPhoto() {
        return photo;
    }

    public int isFavorite() {
        return favorite;
    }

    public int id;

    public int getId() {
        return id;
    }

    public Recipe(){}
    public Recipe(int id, String title, String type, String description, String recipe, String ingredients, Integer time, String photo,int favorite) {
        this.id=id;
        this.description = description;
        this.photo = photo;
        this.time = time;
        this.title = title;
        this.type = type;
        this.recipe=recipe;
        this.ingredients=ingredients;
        this.favorite=favorite;
    }
}
