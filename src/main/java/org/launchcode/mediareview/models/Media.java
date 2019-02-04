package org.launchcode.mediareview.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Media {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=1, message = "Please enter a title")
    private String title;

    @OneToMany
    @JoinColumn(name = "media_id")
    private List<Review> reviews = new ArrayList<>();

    public Media(){}

    public Media(String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
