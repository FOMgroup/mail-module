package ru.fom.mail.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YandexUser {

    @SerializedName("login") private String email;
    @SerializedName("uid") private String id;
    @SerializedName("enabled") private String status;
    @SerializedName("fio") private String userName;
    @SerializedName("fname") private String userSurname;
    @SerializedName("iname") private String userFirstName;
    @SerializedName("birth_date") private String dateOfBirth;
    @SerializedName("sex") private String gender;
    @SerializedName("hintq") private String hint;
    @SerializedName("ready") private String mailboxReady;
    @SerializedName("maillist") private String inMailList;
    @SerializedName("aliases") private List<String> aliases;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsEnabled() {
        return status.equals("yes");
    }

    public void setIsEnabled(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public boolean getIsMailboxReady() {
        return mailboxReady.equals("yes");
    }

    public void setMailboxReady(String mailboxReady) {
        this.mailboxReady = mailboxReady;
    }

    public boolean getIsInMailList() {
        return inMailList.equals("yes");
    }

    public void setInMailList(String inMailList) {
        this.inMailList = inMailList;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

}
