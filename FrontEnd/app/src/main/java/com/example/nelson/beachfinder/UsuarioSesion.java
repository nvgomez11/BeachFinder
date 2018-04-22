package com.example.nelson.beachfinder;

/**
 * Created by estadm on 21/4/2018.
 */

public class UsuarioSesion {
    private static UsuarioSesion instance = null;
    private int idUsuario;
    private String nameUser;
    private String lastName;
    private String nationality;
    private String profilePicture;
    private String phoneNumber;
    private String email;

    protected UsuarioSesion() {
        // Exists only to defeat instantiation.
    }
    public static UsuarioSesion getInstance() {
        if(instance == null) {
            instance = new UsuarioSesion();
        }
        return instance;
    }


    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
