package fr.univ_angers.agenda_ua.matieres;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.univ_angers.agenda_ua.R;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;

public class MatieresActivity extends AppCompatActivity{
        private Button _btn_valider_matieres;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_matieres);
            _btn_valider_matieres = (Button) findViewById(R.id.button_valider_matieres);
            // Get listview checkbox.
            final ListView listViewWithCheckbox = (ListView)findViewById(R.id.list_view_with_checkbox);

            // Initiate listview data.
            final List<MatieresListViewItem> initItemList = this.getInitViewItemDtoList();
            System.out.println("Size : "+initItemList.size());

            // Create a custom list view adapter with checkbox control.
            final MatieresAdapter listViewDataAdapter = new MatieresAdapter(getApplicationContext(), initItemList);

            listViewDataAdapter.notifyDataSetChanged();

            // Set data adapter to list view.
            listViewWithCheckbox.setAdapter(listViewDataAdapter);

            // When list view item is clicked.
            listViewWithCheckbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                    // Get user selected item.
                    Object itemObject = adapterView.getAdapter().getItem(itemIndex);

                    // Translate the selected item to DTO object.
                    MatieresListViewItem itemDto = (MatieresListViewItem) itemObject;

                    // Get the checkbox.
                    CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.list_view_item_checkbox);

                    // Reverse the checkbox and clicked item check state.
                    if(itemDto.isChecked())
                    {
                        itemCheckbox.setChecked(false);
                        itemDto.setChecked(false);
                    }else
                    {
                        itemCheckbox.setChecked(true);
                        itemDto.setChecked(true);
                    }

                    //Toast.makeText(getApplicationContext(), "select item text : " + itemDto.getItemText(), Toast.LENGTH_SHORT).show();
                }
            });


        }


        // Return an initialize list of ListViewItemDTO.
        private List<MatieresListViewItem> getInitViewItemDtoList()
        {
            ArrayList<String> matieres = GetEvents._listeMatieres;
            ArrayList<MatieresListViewItem> ret = new ArrayList<MatieresListViewItem>();
            for(String matiere : matieres)
            {
                MatieresListViewItem dto = new MatieresListViewItem();
                dto.setChecked(true);
                dto.setItemText(matiere);

                ret.add(dto);
            }
            return ret;
        }
}
