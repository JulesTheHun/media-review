package org.launchcode.mediareview.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=1, message = "Please enter a title")
    private String title;

    @NotNull
    @Size(min=1, message = "Text must not be empty")
    private String text;

    @ManyToOne
    private Media media;

    public Review (){}

    public Review (String title, String text) {
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
