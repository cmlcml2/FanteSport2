package com.android.efrei.fantasport.model;

/**
 * Created by PC-Acta on 20/04/2018.
 */

public class Match {

    private final Integer id;
    private final String joueur1;
    private final String joueur2;
    private final String score1;
    private final String score2;
    private final String duree;
    private final String lieu;

    /**
     * Constructeur rendant un objet vide.
     */
    public Match() {
        this.id = null;
        this.joueur1 = "";
        this.joueur2 = "";
        this.score1 = "";
        this.score2 = "";
        this.duree = "";
        this.lieu = "";
    }

    public Match(int id, String joueur1, String joueur2, String score1, String score2, String duree, String lieu) {
        this.id = id;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.score1 =  score1;
        this.score2 = score2;
        this.duree = duree;
        this.lieu = lieu;
    }

    public Match(String joueur1, String joueur2, String score1, String score2, String duree, String lieu) {
        this.id = null;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.score1 =  score1;
        this.score2 = score2;
        this.duree = duree;
        this.lieu = lieu;
    }

    /**
     * Accessor
     */
    public Integer getId() {
        return id;
    }

    /**
     * Accessor
     */
    public String getJoueur1() {
        return joueur1;
    }

    /**
     * Accessor
     */
    public String getJoueur2() {
        return joueur2;
    }

    /**
     * Accessor
     */
    public String getScore1() {
        return score1;
    }

    /**
     * Accessor
     */
    public String getScore2() {
        return score2;
    }

    /**
     * Accessor
     */
    public String getDuree() {
        return duree;
    }

    /**
     * Accessor
     */
    public String getLieu() {
        return lieu;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", joueur1='" + joueur1 + '\'' +
                ", joueur2='" + joueur2 + '\'' +
                ", score1='" + score1 + '\'' +
                ", score2='" + score2 + '\'' +
                ", duree='" + duree + '\'' +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
