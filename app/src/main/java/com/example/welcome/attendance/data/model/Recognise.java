package com.example.welcome.attendance.data.model;

/**
 * Created by nitesh on 21/3/18.
 */

public class Recognise {
    private String gallery_name;
    private String image;
    private Facial[] images;


    public Recognise(String image,String gallery_name)
    {
        this.image=image;
        this.gallery_name=gallery_name;
    }
    public class Facial
    {
        private Detail[] candidates;
    }

    public class  Detail
    {

        private String subject_id;
    }

    public String getSubject_id() {
        return images[0].candidates[0].subject_id;
    }


}
