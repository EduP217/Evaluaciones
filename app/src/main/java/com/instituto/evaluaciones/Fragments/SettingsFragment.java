package com.instituto.evaluaciones.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.instituto.evaluaciones.R;
import com.instituto.evaluaciones.beans.BeanProfesor;
import com.instituto.evaluaciones.dao.daoUsuario;

/**
 * Created by Edu on 22/11/2016.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    View main_view;
    BeanProfesor objProfesor;
    daoUsuario dao;

    CheckBox checkUpdate;

    private void iniciarComponentes(){
        checkUpdate = (CheckBox)main_view.findViewById(R.id.chkActualizacion);
        checkUpdate.setOnClickListener(this);
        dao = new daoUsuario(getActivity());
    }

    private void validarEstado(String codigo){
        int estado = dao.estadoSetting(codigo);
        if(estado==0){
            checkUpdate.setChecked(false);
        } else {
            checkUpdate.setChecked(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_view = inflater.inflate(R.layout.settings_layout,container,false);
        iniciarComponentes();

        Bundle bundle = this.getArguments();
        objProfesor = (BeanProfesor) bundle.getSerializable("objprof");
        validarEstado(objProfesor.getCodProfesor());
        return main_view;
    }

    @Override
    public void onClick(View v) {
        if(v == checkUpdate){
            if(checkUpdate.isChecked()){
                dao.updateSetting(objProfesor.getCodProfesor(),1);
            } else {
                dao.updateSetting(objProfesor.getCodProfesor(),0);
            }
        }
    }
}
