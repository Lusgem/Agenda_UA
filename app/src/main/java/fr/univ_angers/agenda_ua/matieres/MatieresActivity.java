package fr.univ_angers.agenda_ua.matieres;

import android.content.Intent;
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
import fr.univ_angers.agenda_ua.Traitements.Traitement;
import fr.univ_angers.agenda_ua.calendrier.MainActivity;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;

public class MatieresActivity extends AppCompatActivity{
        private Button _btn_valider_matieres;
        private ArrayList<String> _listeMatiereAEnlever = new ArrayList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_matieres);
            _btn_valider_matieres = (Button) findViewById(R.id.button_valider_matieres);
            final ListView listViewWithCheckbox = (ListView)findViewById(R.id.list_view_with_checkbox);
            final List<MatieresListViewItem> initItemList = this.getInitViewItemDtoList();
            final MatieresAdapter listViewDataAdapter = new MatieresAdapter(getApplicationContext(), initItemList);

            listViewDataAdapter.notifyDataSetChanged();

            listViewWithCheckbox.setAdapter(listViewDataAdapter);

            listViewWithCheckbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {

                    Object itemObject = adapterView.getAdapter().getItem(itemIndex);
                    MatieresListViewItem itemDto = (MatieresListViewItem) itemObject;
                    CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.list_view_item_checkbox);

                    if(itemDto.isChecked())
                    {
                        _listeMatiereAEnlever.add(itemCheckbox.getText().toString());
                        itemCheckbox.setChecked(false);
                        itemDto.setChecked(false);
                    }else
                    {
                        for (int i=0;i<_listeMatiereAEnlever.size();i++){
                            if(_listeMatiereAEnlever.get(i)==itemCheckbox.getText().toString())
                                _listeMatiereAEnlever.remove(i);
                        }
                        itemCheckbox.setChecked(true);
                        itemDto.setChecked(true);
                    }

                }
            });


        }

        private List<MatieresListViewItem> getInitViewItemDtoList()
        {
            ArrayList<String> matieres = GetEvents._listeMatieres;
            ArrayList<MatieresListViewItem> ret = new ArrayList<MatieresListViewItem>();
            for(String matiere : matieres)
            {
                MatieresListViewItem mat = new MatieresListViewItem();
                mat.setChecked(true);
                mat.setItemText(matiere);

                ret.add(mat);
            }
            return ret;
        }

        public void onClickMatieres(View view){
            GetEvents._listeMatieresAEnlever = _listeMatiereAEnlever;
            Traitement.TraitementMatiere();
            Intent WeekView = new Intent(this, MainActivity.class);
            startActivity(WeekView);
        }
}
