package com.android.efrei.fantasport.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Singleton pour récupérer et fermer la connexion à la base de données
 *
 */
public class SQLiteConnexion {
    private final static SQLiteConnexion instance = new SQLiteConnexion();
    private static SQLiteDatabase db;
    private FantasportDbHelper dbHelper = null;

    private SQLiteConnexion() {
    }

    /**
     * Accesseur
     */
    public static SQLiteConnexion getInstance() {
        return instance;
    }

    /**
     * Récupére la connexion SQLite courante ou en crée une nouvelle
     */
    public SQLiteDatabase ouvrir(Context context) {
        dbHelper = new FantasportDbHelper(context);
        if (db == null || !db.isOpen()) {
            Log.i("FTS", "Ouverture de la base de données par " + this.getClass().getName());
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }

    /**
     * Ferme la connexion à la base de données
     */
    public void fermer() {
        Log.i("FTS", "Fermeture de la base de données par " + this.getClass().getName());
        dbHelper.close();
    }
}