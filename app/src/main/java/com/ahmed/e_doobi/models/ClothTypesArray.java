package com.ahmed.e_doobi.models;

import com.ahmed.e_doobi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClothTypesArray {

    private ArrayList<String> mClothTypesArrayList;
    private Map<String, Integer> mClothTypesMap;

    public ClothTypesArray() {
        populateClothTypesArray();
        populateClothTypesMap();
    }

    private void populateClothTypesArray() {
        mClothTypesArrayList = new ArrayList<>();
        mClothTypesArrayList.add("Omani Mussar");
        mClothTypesArrayList.add("Omani Dishdasha");
        mClothTypesArrayList.add("Omani Kuma");
        mClothTypesArrayList.add("Hijab");
        mClothTypesArrayList.add("T-Shirt");
        mClothTypesArrayList.add("Jacket");
        mClothTypesArrayList.add("Suit");
        mClothTypesArrayList.add("Trousers");
        mClothTypesArrayList.add("Bed Sheet");
    }

    public ArrayList<String> getClothTypesArrayList() {
        return mClothTypesArrayList;
    }

    private void populateClothTypesMap() {
        mClothTypesMap = new HashMap<>();
        mClothTypesMap.put("Omani Mussar", R.drawable.ic_mussar);
        mClothTypesMap.put("Omani Dishdasha", R.drawable.ic_thobe);
        mClothTypesMap.put("Omani Kuma", R.drawable.ic_kuma);
        mClothTypesMap.put("Hijab", R.drawable.ic_hijab);
        mClothTypesMap.put("T-Shirt", R.drawable.ic_tshirt);
        mClothTypesMap.put("Jacket", R.drawable.ic_jacket);
        mClothTypesMap.put("Suit", R.drawable.ic_suit);
        mClothTypesMap.put("Trousers", R.drawable.ic_trousers);
        mClothTypesMap.put("Bed Sheet", R.drawable.ic_bed_sheet);
    }

    public Map<String, Integer> getClothTypesMap() {
        return mClothTypesMap;
    }
}
