package fr.univ_angers.agenda_ua.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.univ_angers.agenda_ua.evenement.Evenement;
import fr.univ_angers.agenda_ua.R;

/**
 * Created by Thibault Condemine on 14/05/2018.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Events_ViewHolder> {

    private Context _context;
    private ArrayList<Item> _data;

    public EventAdapter() {
        _data = new ArrayList<Item>();
    }

    @NonNull
    @Override
    public Events_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_recycler_view, parent, false);
        return new Events_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Events_ViewHolder holder, int position) {
        Item item = _data.get(position);
        holder.updateData(item);
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public void ajoute(Evenement evenement){
        EventAdapter.Item item = new EventAdapter.Item();
        if(evenement.get_matiere()!= null && evenement.get_matiere()!="") {
            item._date = evenement.toString_Date();
            item._lieu = evenement.get_location();
            item._detail = evenement.get_matiere();
            _data.add(item);
            this.notifyItemInserted(_data.size() - 1);
        }
    }

    static class Item{
        String _date;
        String _lieu;
        String _detail;
    }

    public class Events_ViewHolder extends RecyclerView.ViewHolder{
        private TextView _fragment_date;
        private TextView _fragment_lieu;
        private TextView _fragment_detail;

        public Events_ViewHolder(View itemView){
            super(itemView);
            _fragment_date = (TextView) itemView.findViewById(R.id.fragment_date);
            _fragment_lieu = (TextView) itemView.findViewById(R.id.fragment_lieu);
            _fragment_detail = (TextView) itemView.findViewById(R.id.fragment_detail);
        }

        public void updateData(Item item){
            _fragment_date.setText(item._date);
            _fragment_lieu.setText(item._lieu);
            _fragment_detail.setText(item._detail);
        }
    }
}
