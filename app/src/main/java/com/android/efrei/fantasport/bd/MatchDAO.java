package com.android.efrei.fantasport.bd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.efrei.fantasport.model.Match;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.android.efrei.fantasport.bd.FantasportContract.Match.COLUMN_DUREE;
import static com.android.efrei.fantasport.bd.FantasportContract.Match.COLUMN_JOUEUR1;
import static com.android.efrei.fantasport.bd.FantasportContract.Match.COLUMN_JOUEUR2;
import static com.android.efrei.fantasport.bd.FantasportContract.Match.COLUMN_LIEU;
import static com.android.efrei.fantasport.bd.FantasportContract.Match.COLUMN_SCORE1;
import static com.android.efrei.fantasport.bd.FantasportContract.Match.COLUMN_SCORE2;
import static com.android.efrei.fantasport.bd.FantasportContract.Match.TABLE_NAME;


public class MatchDAO {

    private static final String ALL_FIELDS =
            _ID + ", " +
                    COLUMN_JOUEUR1 + ", " +
                    COLUMN_JOUEUR2 + ", " +
                    COLUMN_SCORE1 + ", " +
                    COLUMN_SCORE2 + ", " +
                    COLUMN_DUREE + ", " +
                    COLUMN_LIEU;
    private static final String REQ_CHERCHER_PAR_ID =
            "SELECT " + ALL_FIELDS +
                    " FROM " + TABLE_NAME +
                    " WHERE " + _ID + "= ?";
    private static final String REQ_CHERCHER_TOUS =
            "SELECT " + ALL_FIELDS +
                    " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_LIEU;
    private static final String REQ_SUPPRIMER =
            "DELETE FROM " + TABLE_NAME;
    private static final String REQ_NB_LIGNES =
            "SELECT COUNT(*) FROM " + TABLE_NAME;
    private SQLiteDatabase db = null;

    /**
     * Méthode de transformation cursor en objet MatchItem
     *
     * @param c le cursor SQL
     * @return Match renseigné à partir des informations du Cursor
     */
    private static Match cursorVersMatchItem(Cursor c) {

        return new Match(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4),
                c.getString(5), c.getString(6));
    }

    /**
     * Récupère en base un MatchItem par son id
     *
     * @param id identifiant technique
     * @return MatchItem
     */
    public Match recupererParId(long id) {
        return recupererMatch(REQ_CHERCHER_PAR_ID, new String[]{String.valueOf(id)});
    }


    /**
     * Méthode générique pour toute requête qui s'attend à récupérer 1 élément unique Match.
     *
     * @param req  la requête SQL (dont la clause SELECT doit demander l'ensemble des champs de la table)
     * @param args les arguments éventuels (null si aucun paramètre)
     * @return MatchItem
     */
    private Match recupererMatch(String req, String[] args) {
        Match res = null;
        final Cursor cursor = db.rawQuery(req, args);

        if (cursor.getCount() == 0) {
            Log.i("FTS", "Aucun élément trouvé pour la requête " + req + " et les args ");
            afficherArgs(args);
        } else {
            cursor.moveToFirst();
            res = cursorVersMatchItem(cursor);
        }

        cursor.close();
        return res;
    }

    /**
     * Méthode générique pour toute requête SQL qui récupère une liste de Match.
     *
     * @param req  requête SQL (la clause SELECT doit demander l'ensemble des champs de la table)
     * @param args arguments éventuels sous forme de tableau de String (null si aucun paramètre)
     * @return List<MatchItem>
     */
    private List<Match> recupererListeMatch(String req, String[] args) {
        final Cursor cursor = db.rawQuery(req, args);

        final List<Match> res = new ArrayList<>(cursor.getCount());

        if (cursor.getCount() == 0) {
            Log.i("FTS", "Aucun match trouvé pour la requête " + req + " et les args ");
            afficherArgs(args);
        } else {
            cursor.moveToFirst();

            do {
                res.add(cursorVersMatchItem(cursor));
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        return res;
    }


    /**
     * Ajoute une ligne dans la table Match
     *
     * @param match le Match à ajouter
     * @return l'id SQL de la ligne nouvellement ajoutée
     */
    public long ajouter(Match match) {
        final ContentValues values = genererContentValues(match);

        final long rowId = db.insert(FantasportContract.Match.TABLE_NAME, null, values);
        Log.i("FTS", "rowId du nouvel élément " + rowId);

        return rowId;
    }

    /**
     * Récupère l'ensemble de la table
     *
     * @return List<MatchItem>
     */
    public List<Match> recupererTous() {
        return recupererListeMatch(REQ_CHERCHER_TOUS, null);
    }

    /**
     * Remise à zéro de la table Match
     */
    public void raz() {
        db.execSQL(REQ_SUPPRIMER);
    }

    /**
     * Permet d'afficher une liste de String en Log
     *
     * @param args
     */
    private void afficherArgs(String[] args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                Log.i("FTS", "args[" + i + "] = " + args[i]);
            }
        }
    }

    /**
     * Injection de dépendance pour la base de données
     *
     * @param db la base de données
     */
    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Rend le nombre de lignes de la table Match
     *
     * @return nombre de lignes
     */
    public int nbLignes() {
        final Cursor cursor = db.rawQuery(REQ_NB_LIGNES, null);
        cursor.moveToFirst();

        final int res = cursor.getInt(0);

        cursor.close();

        return res;
    }

    /**
     * Constitue un ContentValues à partir d'un objet Match
     *
     * @param match
     * @return ContentValues
     */
    @NonNull
    private ContentValues genererContentValues(Match match) {
        final ContentValues values = new ContentValues();
        values.put(COLUMN_JOUEUR1, match.getJoueur1());
        values.put(COLUMN_JOUEUR2, match.getJoueur2());
        values.put(COLUMN_SCORE1, match.getScore1());
        values.put(COLUMN_SCORE2, match.getScore1());
        values.put(COLUMN_DUREE, match.getDuree());
        values.put(COLUMN_LIEU, match.getLieu());
        return values;
    }
}
