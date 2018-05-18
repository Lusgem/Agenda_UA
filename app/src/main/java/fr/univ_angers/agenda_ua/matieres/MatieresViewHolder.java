package fr.univ_angers.agenda_ua.matieres;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MatieresViewHolder  extends RecyclerView.ViewHolder {

    private CheckBox itemCheckbox;

    public MatieresViewHolder(View itemView) {
        super(itemView);
    }

    public CheckBox getItemCheckbox() {
        return itemCheckbox;
    }

    public void setItemCheckbox(CheckBox itemCheckbox) {
        this.itemCheckbox = itemCheckbox;
    }

}