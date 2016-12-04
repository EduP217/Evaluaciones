package com.instituto.evaluaciones.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.instituto.evaluaciones.R;

/**
 * Created by Edu on 22/11/2016.
 */

public class SecondFragment extends Fragment {

    View main_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_view = inflater.inflate(R.layout.importar_layout,container,false);
        return main_view;
    }
}
