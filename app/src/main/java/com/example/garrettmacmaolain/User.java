package com.example.garrettmacmaolain;

import android.graphics.Bitmap;

import com.facebook.model.GraphUser;
import com.parse.ParseUser;

/**
 * Created by jesper on 24/11/14.
 */
public class User {

    private String firstName;
    private String facebookId;
    private String userName;
    private String objectId;
    private String generalSize;
    private String homeTown;
    private String email;
    private Bitmap profilePicture;

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", userName='" + userName + '\'' +
                ", objectId='" + objectId + '\'' +
                ", generalSize='" + generalSize + '\'' +
                ", homeTown='" + homeTown + '\'' +
                '}';
    }

    public User(GraphUser graphUser, final ParseUser parseUser){
        this.firstName = graphUser.getFirstName();
        this.facebookId = graphUser.getId();
        this.homeTown = graphUser.getLocation().getName();
        this.email = (String)graphUser.getProperty("email");
        String url = "http://graph.facebook.com/"+this.facebookId+"/picture?type=large";

        InternetConnectionMethods.getImageFromWeb(url, new InternetConnectionMethods.ImageSetListener() {
            @Override
            public void onImageSet(Bitmap image) {
                setProfilePicture(image);
                ParseAPI.sendUserInfoToParse(parseUser, User.this);
            }
        });

    }

    public User(){

    }
    public User(String firstName, String facebookId, String userName, String objectId, String generalSize, String homeTown) {

        this.firstName = firstName;
        this.facebookId = facebookId;
        this.userName = userName;
        this.objectId = objectId;
        this.generalSize = generalSize;
        this.homeTown = homeTown;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getGeneralSize() {
        return generalSize;
    }

    public void setGeneralSize(String generalSize) {
        this.generalSize = generalSize;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public interface UserImageSetListener {
        public void onUserImageSet(Bitmap userPic);
    }
}
