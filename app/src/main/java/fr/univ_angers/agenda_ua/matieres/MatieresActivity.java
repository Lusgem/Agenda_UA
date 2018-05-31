package fr.univ_angers.agenda_ua.matieres;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fr.univ_angers.agenda_ua.FormationActivity;
import fr.univ_angers.agenda_ua.R;
import fr.univ_angers.agenda_ua.Traitements.Traitement;
import fr.univ_angers.agenda_ua.Utilisateur;
import fr.univ_angers.agenda_ua.calendrier.MainActivity;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.dataBase.DataSource;

/**
 * Cette activité est celle permettant de sélectionner les matières à afficher
 */

public class MatieresActivity extends AppCompatActivity{

    private final static String TAG = Activity.class.getName();
    public static final String BUNDLE = "BUNDLE";

    private DataSource _datasource;
    private Button _btn_valider_matieres;
    private ArrayList<String> _listeMatiereAEnlever = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matieres);

        Log.i(TAG, "MatieresActivity onCreate");

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
                        if(_listeMatiereAEnlever.get(i).equalsIgnoreCase(itemCheckbox.getText().toString()))
                            _listeMatiereAEnlever.remove(i);
                    }
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "MatieresActivity onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "MatieresActivity onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "MatieresActivity onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "MatieresActivity onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "MatieresActivity onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "MatieresActivity onDestroy");
        super.onDestroy();
    }

    /**
     * Cette fonction récupère les matières de la formation et les ajoute dans le listView
     * Si une matière à déja été enlevée par l'étudiant, elle sera automatiquement décochée
     * lorsqu'il reviendra sur cette activité
     */
    private List<MatieresListViewItem> getInitViewItemDtoList()
    {
        _datasource = new DataSource(this);
        _datasource.open();
        ArrayList<String> matieres = _datasource.getMatieres();
        ArrayList<String> matieresEnlevees = new ArrayList<>(); // Liste des matières déja enlevées
        if (!_datasource.utilisateurVide()) {
            Gson gson = new Gson();
            ArrayList<Utilisateur> uti = _datasource.getAllUtilisateur();
            String form = uti.get(0).get_formation();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            matieresEnlevees = gson.fromJson(form, type);
            if(!(matieresEnlevees==null)){
                _listeMatiereAEnlever=matieresEnlevees;
            }
        }
        ArrayList<MatieresListViewItem> ret = new ArrayList<MatieresListViewItem>();
        for(String matiere : matieres)
        {
            System.out.println("List : " + matiere);
            MatieresListViewItem mat = new MatieresListViewItem();
            if(!(matieresEnlevees==null)){
                for(String matiereEnlevee : matieresEnlevees){
                    if (matiereEnlevee.equalsIgnoreCase(matiere)){
                        mat.setChecked(false);
                        break;
                    }
                    else{
                        mat.setChecked(true);
                    }
                }
            }
            else {
                mat.setChecked(true);
                }
            mat.setItemText(matiere);
            ret.add(mat);
        }
        _datasource.close();
        return ret;
    }

    /**
     * Lors de l'appui sur le bouton,
     */
    public void onClickMatieres(View view){
                for (String s : _listeMatiereAEnlever) {
            System.out.println("Matieres activity : " + s);
        }
        Gson gson = new Gson();
        String inputString= gson.toJson(_listeMatiereAEnlever);
        _datasource = new DataSource(this);
        _datasource.open();
        _datasource.updateUtilisateur(1,inputString);
        Traitement.TraitementMatiere(_datasource);
        _datasource.close();
        Intent WeekView = new Intent(this, MainActivity.class);
        startActivity(WeekView);
        MatieresActivity.this.finish();
    }
}
