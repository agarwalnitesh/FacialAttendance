package com.example.welcome.attendance.data.model;

/**
 * Created by nitesh on 20/3/18.
 */

public class Post {

    private String gallery_name;
    private String subject_id;
    private String image;
    private String face_id;
    private Facial[] images;

    public Post(String image,String subject_id,String gallery_name)
    {
        this.image=image;
        this.subject_id=subject_id;
        this.gallery_name=gallery_name;
    }



    public String getFace_id() {

        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

    private class Facial
    {
        private Transaction transaction;
    }
    private class Transaction
    {

        private String subject_id;

        public void setSubject_id(String subject_id) {

            this.subject_id = subject_id;
        }


    }
    public String getSubject_id() {
        return images[0].transaction.subject_id;
    }

}
