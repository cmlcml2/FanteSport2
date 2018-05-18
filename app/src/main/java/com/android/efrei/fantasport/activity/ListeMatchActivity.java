package com.android.efrei.fantasport.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.efrei.fantasport.R;
import com.android.efrei.fantasport.bd.MatchDAO;
import com.android.efrei.fantasport.bd.SQLiteConnexion;
import com.android.efrei.fantasport.model.Match;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

public class ListeMatchActivity extends AppCompatActivity {

    private static final String TAG = "FTS.ListeMatchActivity";
    private final MatchDAO matchDAO = new MatchDAO();
    private RecyclerView listView;
    private MatchItemAdapter adapter;
    // la liste de Matchs à afficher
    private List<Match> matchs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_match);

        //on recupére la liste des matchs
        matchDAO.setDb(SQLiteConnexion.getInstance().ouvrir(this));
        matchs = matchDAO.recupererTous();
        SQLiteConnexion.getInstance().fermer();

        Log.i(TAG,""+matchs.size());


        listView = (RecyclerView) findViewById(R.id.listMatchItems);

        adapter = new MatchItemAdapter(matchs, this.getBaseContext());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        listView.setAdapter(adapter);
        listView.setLayoutManager(layoutManager);

        //Ajout du fastscroll
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listMatchItems);
        FastScroller fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        fastScroller.setRecyclerView(recyclerView);
    }

    protected void onResume() {
        Log.i(TAG, "...onResume");
        super.onResume();
        listView.setVisibility(View.VISIBLE);
    }
}
