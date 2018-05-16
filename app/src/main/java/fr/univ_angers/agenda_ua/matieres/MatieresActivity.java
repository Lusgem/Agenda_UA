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

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_matieres);

            // Get listview checkbox.
            final ListView listViewWithCheckbox = (ListView)findViewById(R.id.list_view_with_checkbox);

            // Initiate listview data.
            final List<MatieresListViewItem> initItemList = this.getInitViewItemDtoList();

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

            // Click this button to select all listview items with checkbox checked.
            Button selectAllButton = (Button)findViewById(R.id.list_select_all);
            selectAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int size = initItemList.size();
                    for(int i=0;i<size;i++)
                    {
                        MatieresListViewItem dto = initItemList.get(i);
                        dto.setChecked(true);
                    }

                    listViewDataAdapter.notifyDataSetChanged();
                }
            });

            // Click this button to disselect all listview items with checkbox unchecked.
            Button selectNoneButton = (Button)findViewById(R.id.list_select_none);
            selectNoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int size = initItemList.size();
                    for(int i=0;i<size;i++)
                    {
                        MatieresListViewItem dto = initItemList.get(i);
                        dto.setChecked(false);
                    }

                    listViewDataAdapter.notifyDataSetChanged();
                }
            });

            // Click this button to reverse select listview items.
            Button selectReverseButton = (Button)findViewById(R.id.list_select_reverse);
            selectReverseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int size = initItemList.size();
                    for(int i=0;i<size;i++)
                    {
                        MatieresListViewItem dto = initItemList.get(i);

                        if(dto.isChecked())
                        {
                            dto.setChecked(false);
                        }else {
                            dto.setChecked(true);
                        }
                    }

                    listViewDataAdapter.notifyDataSetChanged();
                }
            });

            // Click this button to remove selected items from listview.
            Button selectRemoveButton = (Button)findViewById(R.id.list_remove_selected_rows);
            selectRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog alertDialog = new AlertDialog.Builder(MatieresActivity.this).create();
                    alertDialog.setMessage("Are you sure to remove selected listview items?");

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            int size = initItemList.size();
                            for(int i=0;i<size;i++)
                            {
                                MatieresListViewItem dto = initItemList.get(i);

                                if(dto.isChecked())
                                {
                                    initItemList.remove(i);
                                    i--;
                                    size = initItemList.size();
                                }
                            }

                            listViewDataAdapter.notifyDataSetChanged();
                        }
                    });

                    alertDialog.show();
                }
            });

        }


        // Return an initialize list of ListViewItemDTO.
        private List<MatieresListViewItem> getInitViewItemDtoList()
        {
            ArrayList<String> matieres = GetEvents._listeMatieres;
            ArrayList<MatieresListViewItem> ret = new ArrayList<MatieresListViewItem>();

            int length = matieres.size();

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
