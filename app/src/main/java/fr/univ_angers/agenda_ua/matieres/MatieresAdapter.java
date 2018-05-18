package fr.univ_angers.agenda_ua.matieres;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import fr.univ_angers.agenda_ua.R;

public class MatieresAdapter extends BaseAdapter {

    private List<MatieresListViewItem> listViewItemDtoList = null;

    private Context ctx;

    public MatieresAdapter(Context ctx, List<MatieresListViewItem> listViewItemDtoList) {
        this.ctx = ctx;
        this.listViewItemDtoList = listViewItemDtoList;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(listViewItemDtoList!=null)
        {
            ret = listViewItemDtoList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object ret = null;
        if(listViewItemDtoList!=null) {
            ret = listViewItemDtoList.get(itemIndex);
        }
        return ret;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }

    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {

        MatieresViewHolder viewHolder;

        if(convertView!=null)
        {
            viewHolder = (MatieresViewHolder) convertView.getTag();

        }else
        {
            convertView = View.inflate(ctx, R.layout.matieres_item, null);

            CheckBox listItemCheckbox = (CheckBox) convertView.findViewById(R.id.list_view_item_checkbox);

            //TextView listItemText = (TextView) convertView.findViewById(R.id.list_view_item_text);

            viewHolder = new MatieresViewHolder(convertView);

            viewHolder.setItemCheckbox(listItemCheckbox);

            //viewHolder.setItemTextView(listItemText);

            convertView.setTag(viewHolder);
        }

        MatieresListViewItem listViewItemDto = listViewItemDtoList.get(itemIndex);
        viewHolder.getItemCheckbox().setChecked(listViewItemDto.isChecked());
        viewHolder.getItemCheckbox().setText(listViewItemDto.getItemText());


        return convertView;
    }
}
