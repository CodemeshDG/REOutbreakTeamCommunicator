package com.dommyg.reoutbreakteamcommunicator;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChangeStatusDeadFragment extends Fragment {
    private OnDataPass dataPasser;

//    private CheckBox checkBoxItem1;
//    private CheckBox checkBoxItem2;
//    private CheckBox checkBoxItem3;
//    private CheckBox checkBoxItem4;
//    private CheckBox checkBoxItem5;
//    private CheckBox checkBoxItem6;
//    private CheckBox checkBoxItem7;
//    private CheckBox checkBoxItem8;
//
    private AutoCompleteTextView textViewItem1;
    private AutoCompleteTextView textViewItem2;
    private AutoCompleteTextView textViewItem3;
    private AutoCompleteTextView textViewItem4;
    private AutoCompleteTextView textViewItem5;
    private AutoCompleteTextView textViewItem6;
    private AutoCompleteTextView textViewItem7;
    private AutoCompleteTextView textViewItem8;
    private AutoCompleteTextView textViewLocation;

    private Button buttonCancel;
    private Button buttonSubmit;

    private String[] locations;
    private String[] itemsAll;

//    private boolean item1;
//    private boolean item2;
//    private boolean item3;
//    private boolean item4;
//    private boolean item5;
//    private boolean item6;
//    private boolean item7;
//    private boolean item8;

    private boolean isYoko;

    public interface OnDataPass {
        void onDataPass(boolean[] data, String[] items, String location, int resultCode);
    }

    public static ChangeStatusDeadFragment newInstance(String[] locations, String[] itemsHealing,
                                                       String[] itemsWeapon, String[] itemsAmmo,
                                                       String[] itemsKey, boolean isYoko) {
        return new ChangeStatusDeadFragment(locations, itemsHealing, itemsWeapon, itemsAmmo,
                itemsKey, isYoko);
    }

    public ChangeStatusDeadFragment(String[] locations, String[] itemsHealing,  String[] itemsWeapon,
                                    String[] itemsAmmo, String[] itemsKey, boolean isYoko) {
        this.locations = locations;
        this.itemsAll = compileMasterItemList(itemsHealing, itemsWeapon, itemsAmmo, itemsKey);
        this.isYoko = isYoko;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  OnDataPass) {
            dataPasser = (OnDataPass) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_status_dead, container, false);

        setUpCheckBoxes(v);
        setUpLocations(v);
        setUpButtons(v);

        return v;
    }

    private void passData(boolean[] data, String[] selectedItems, String selectedLocation) {
        dataPasser.onDataPass(data, selectedItems, selectedLocation, StatusType.DEAD.getType());
    }

    /**
     * Finds views for the check boxes, sets up their listeners and the adapters for their
     * autoCompleteTextViews.
     */
    private void setUpCheckBoxes(View v) {
        textViewItem1 = setUpCheckBox(
                v, R.id.autoCompleteTextViewItem1, R.id.checkBoxDeadItem1, 1);
        textViewItem2 = setUpCheckBox(
                v, R.id.autoCompleteTextViewItem2, R.id.checkBoxDeadItem2, 2);
        textViewItem3 = setUpCheckBox(
                v, R.id.autoCompleteTextViewItem3, R.id.checkBoxDeadItem3, 3);
        textViewItem4 = setUpCheckBox(
                v, R.id.autoCompleteTextViewItem4, R.id.checkBoxDeadItem4, 4);
        textViewItem5 = setUpCheckBox(
                v, R.id.autoCompleteTextViewItem5, R.id.checkBoxDeadItem5, 5);
        textViewItem6 = setUpCheckBox(
                v, R.id.autoCompleteTextViewItem6, R.id.checkBoxDeadItem6, 6);
        textViewItem7 = setUpCheckBox(
                v, R.id.autoCompleteTextViewItem7, R.id.checkBoxDeadItem7, 7);
        textViewItem8 = setUpCheckBox(
                v, R.id.autoCompleteTextViewItem8, R.id.checkBoxDeadItem8, 8);

//        ArrayAdapter<String> item1Adapter = new ArrayAdapter<>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line, itemsAll);
//
//        textViewItem1 = v.findViewById(R.id.autoCompleteTextViewItem1);
//        textViewItem1.setAdapter(item1Adapter);
//        textViewItem1.setThreshold(0);
//
//        checkBoxItem1 = v.findViewById(R.id.checkBoxDeadItem1);
//        checkBoxItem1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                item1 = b;
//                checkIfShowAutoCompleteTextView(item1, textViewItem1);
//            }
//        });
//
//        textViewItem2 = v.findViewById(R.id.autoCompleteTextViewItem2);
//        checkBoxItem2 = v.findViewById(R.id.checkBoxDeadItem2);
//        checkBoxItem2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                item2 = b;
//                checkIfShowAutoCompleteTextView(item2, textViewItem2);
//            }
//        });
//
//        textViewItem3 = v.findViewById(R.id.autoCompleteTextViewItem3);
//        checkBoxItem3 = v.findViewById(R.id.checkBoxDeadItem3);
//        checkBoxItem3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                item3 = b;
//                checkIfShowAutoCompleteTextView(item3, textViewItem3);
//            }
//        });
//
//        textViewItem4 = v.findViewById(R.id.autoCompleteTextViewItem4);
//        checkBoxItem4 = v.findViewById(R.id.checkBoxDeadItem4);
//        checkBoxItem4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                item4 = b;
//                checkIfShowAutoCompleteTextView(item4, textViewItem4);
//            }
//        });
//
//        textViewItem5 = v.findViewById(R.id.autoCompleteTextViewItem5);
//        checkBoxItem5 = v.findViewById(R.id.checkBoxDeadItem5);
//        if (isYoko) {
//            checkBoxItem5.setVisibility(View.VISIBLE);
//        }
//        checkBoxItem5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                item5 = b;
//                checkIfShowAutoCompleteTextView(item5, textViewItem5);
//            }
//        });
//
//        textViewItem6 = v.findViewById(R.id.autoCompleteTextViewItem6);
//        checkBoxItem6 = v.findViewById(R.id.checkBoxDeadItem6);
//        if (isYoko) {
//            checkBoxItem6.setVisibility(View.VISIBLE);
//        }
//        checkBoxItem6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                item6 = b;
//                checkIfShowAutoCompleteTextView(item6, textViewItem6);
//            }
//        });
//
//        textViewItem7 = v.findViewById(R.id.autoCompleteTextViewItem7);
//        checkBoxItem7 = v.findViewById(R.id.checkBoxDeadItem7);
//        if (isYoko) {
//            checkBoxItem7.setVisibility(View.VISIBLE);
//        }
//        checkBoxItem7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                item7 = b;
//                checkIfShowAutoCompleteTextView(item7, textViewItem7);
//            }
//        });
//
//        textViewItem8 = v.findViewById(R.id.autoCompleteTextViewItem8);
//        checkBoxItem8 = v.findViewById(R.id.checkBoxDeadItem8);
//        if (isYoko) {
//            checkBoxItem8.setVisibility(View.VISIBLE);
//        }
//        checkBoxItem8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                item8 = b;
//                checkIfShowAutoCompleteTextView(item8, textViewItem8);
//            }
//        });
    }

    private AutoCompleteTextView setUpCheckBox(View v, int autoCompleteID, int checkBoxID, int checkBoxNumber) {
        @SuppressWarnings("ConstantConditions")
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, itemsAll);

        final AutoCompleteTextView autoCompleteTextView = v.findViewById(autoCompleteID);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(0);

        CheckBox checkBox = v.findViewById(checkBoxID);
        if (checkBoxNumber > 4 && isYoko) {
            checkBox.setVisibility(View.VISIBLE);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkIfShowAutoCompleteTextView(b, autoCompleteTextView);
            }
        });

        return autoCompleteTextView;
    }

    /**
     * Finds the view for the location field and sets up the adapter for the autoCompleteTextView.
     */
    private void setUpLocations(View v) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, locations);

        textViewLocation = v.findViewById(R.id.autoCompleteTextViewLocation);
        textViewLocation.setAdapter(adapter);
        textViewLocation.setThreshold(0);
    }

    /**
     * Finds views for the buttons and sets them and their listeners.
     */
    private void setUpButtons(View v) {
        buttonSubmit = v.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] data = {item1, item2, item3, item4, item5, item6, item7, item8};
                String[] selectedItems = {textViewItem1.getText().toString(),
                textViewItem2.getText().toString(),
                textViewItem3.getText().toString(),
                textViewItem4.getText().toString(),
                textViewItem5.getText().toString(),
                textViewItem6.getText().toString(),
                textViewItem7.getText().toString(),
                textViewItem8.getText().toString()};
                String selectedLocation = textViewLocation.getText().toString();
                passData(data, selectedItems, selectedLocation);
            }
        });

        buttonCancel = v.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private String[] compileMasterItemList(String[] itemsHealing,  String[] itemsWeapon,
                                           String[] itemsAmmo, String[] itemsKey) {
        itemsAll = new String[itemsHealing.length + itemsWeapon.length
                + itemsAmmo.length + itemsKey.length];

        int counter = 0;

        for (String s : itemsHealing) {
            itemsAll[counter] = s;
            counter++;
        }

        for (String s : itemsWeapon) {
            itemsAll[counter] = s;
            counter++;
        }

        for (String s : itemsAmmo) {
            itemsAll[counter] = s;
            counter++;
        }

        for (String s : itemsKey) {
            itemsAll[counter] = s;
            counter++;
        }

        return itemsAll;
    }

    /**
     * Controls an autoCompleteTextView's visibility based upon its associated check box's status.
     * @param isChecked If the check box is checked.
     * @param textView The autoCompleteTextView to be modified.
     */
    private void checkIfShowAutoCompleteTextView(boolean isChecked, AutoCompleteTextView textView) {
        if (isChecked) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
