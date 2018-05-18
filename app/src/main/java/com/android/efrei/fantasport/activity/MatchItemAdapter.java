package com.android.efrei.fantasport.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.efrei.fantasport.R;
import com.android.efrei.fantasport.model.Match;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.ArrayList;
import java.util.List;



class MatchItemAdapter extends RecyclerView.Adapter<MatchItemAdapter.ViewHolder> implements SectionTitleProvider {

    private static final String TAG = "MatchItemAdapter";
    private List<Match> matchs = new ArrayList<>();

    private Listener listener;

    private Context context;

    MatchItemAdapter(List<Match> matchs, Context context) {
        this.matchs = matchs;
        this.context = context;
    }


    /**
     * Création des ViewHolder, i.e. des espaces nécessaires à la partie visible à l'écran (dépend de la taille de l'écran)
     *
     * @param parent ViewGroup
     * @param viewType int
     * @return ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);
        return new ViewHolder(layout);
    }
/*
    void setListener(Listener listener) {
        this.listener = listener;
    }*/


    @Override
    public void onBindViewHolder(MatchItemAdapter.ViewHolder holder, final int position) {
        Log.i(TAG,"onBindViewHolder");
        final LinearLayout containerView = holder.containerView;

        final Match match = matchs.get(position);
        if (matchs != null) {
            final TextView id = (TextView) containerView.findViewById(R.id.id_hidden);
            final TextView joueur1 = (TextView) containerView.findViewById(R.id.joueur1);
            final TextView joueur2 = (TextView) containerView.findViewById(R.id.joueur2);
            final TextView score1 = (TextView) containerView.findViewById(R.id.score1);
            final TextView score2 = (TextView) containerView.findViewById(R.id.score2);
            final TextView duree = (TextView) containerView.findViewById(R.id.duree);
            final TextView lieu = (TextView) containerView.findViewById(R.id.lieu);


            id.setText(match.getId() == null ? "" : match.getId().toString());
            joueur1.setText(match.getJoueur1());
            joueur2.setText(match.getJoueur2());
            score1.setText(match.getScore1());
            score2.setText(match.getScore2());
            duree.setText(match.getDuree());
            lieu.setText(match.getLieu());

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return matchs.size();
    }

    @Override
    public String getSectionTitle(int position) {
        //this String will be shown in a bubble for specified position
        return matchs.get(position).getLieu().substring(0, 1);
    }

    /**
     * Remplacement des données à afficher (plus optimisée que recréer un Adapter ou une liste à chaque fois)
     *
     * @param matchs la liste de Matchs
     */
    void swap(@NonNull List<Match> matchs) {
        if (!matchs.isEmpty()) {
            this.matchs.clear();
            this.matchs.addAll(matchs);
        } else {
            this.matchs = matchs;
        }
        notifyDataSetChanged();
    }

    /**
     * Listener de clic sur l'Adapter. Permet de découpler l'Adapter du traitement et améliorer la réutilisation
     */
    interface Listener {
        void onClick(int position);
    }

    /**
     * ViewHolder du RecyclerView.Adapter
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout containerView;

        ViewHolder(LinearLayout view) {
            super(view);
            this.containerView = view;
        }
    }
}
