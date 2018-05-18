package com.android.efrei.fantasport.bd;


import android.provider.BaseColumns;

/**
 * Schéma de données contenant toutes les tables SQLite
 */
class FantasportContract {
    private FantasportContract() {
    }

    /**
     * Table Match
     */
    static class Match implements BaseColumns {
        static final String TABLE_NAME = "match";
        static final String COLUMN_JOUEUR1 = "joueur1";
        static final String COLUMN_JOUEUR2 = "joueur2";
        static final String COLUMN_SCORE1 = "score1";
        static final String COLUMN_SCORE2 = "score2";
        static final String COLUMN_DUREE = "duree";
        static final String COLUMN_LIEU = "lieu";



        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + Match.TABLE_NAME + " (" +
                        Match._ID + " INTEGER PRIMARY KEY, " +
                        Match.COLUMN_JOUEUR1 + " TEXT, " +
                        Match.COLUMN_JOUEUR2 + " TEXT, " +
                        Match.COLUMN_SCORE1 + " TEXT, " +
                        Match.COLUMN_SCORE2 + " TEXT, " +
                        Match.COLUMN_DUREE + " TEXT, " +
                        Match.COLUMN_LIEU + " TEXT) ";


        static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + Match.TABLE_NAME;
    }

    /**
     * Table Personne
     */
    static class Personne implements BaseColumns {
        static final String TABLE_NAME = "personne";
        static final String COLUMN_NOM = "nom";
        static final String COLUMN_PRENOM = "prenom";

        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + Personne.TABLE_NAME + " (" +
                        Personne._ID + " INTEGER PRIMARY KEY, " +
                        Personne.COLUMN_NOM + " TEXT, " +
                        Personne.COLUMN_PRENOM + " TEXT)";

        static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + Personne.TABLE_NAME;
    }

}
