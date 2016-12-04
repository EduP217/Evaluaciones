package com.instituto.evaluaciones.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.instituto.evaluaciones.R;
import com.instituto.evaluaciones.beans.BeanAsignatura;
import com.instituto.evaluaciones.beans.BeanDetalleProfAsig;
import com.instituto.evaluaciones.beans.BeanSeccion;
import com.instituto.evaluaciones.dao.daoAsignatura;
import com.instituto.evaluaciones.dao.daoCiclo;
import com.instituto.evaluaciones.dao.daoDetalle;
import com.instituto.evaluaciones.dao.daoProfesor;
import com.instituto.evaluaciones.util.ListCursoxDoc;

import java.util.ArrayList;

/**
 * Created by Edu on 22/11/2016.
 */

public class FirstFragment extends Fragment {

    View main_view;
    ListView lstCurso;

    public void iniciarComponentes(){
        lstCurso = (ListView) main_view.findViewById(R.id.lstCursosxDocente);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_view = inflater.inflate(R.layout.principal_layout,container,false);
        iniciarComponentes();

        Bundle bundle = this.getArguments();
        String codProfesor = bundle.getString("idprof");
        poblarListado(codProfesor);

        return main_view;
    }

    public void poblarListado(String codProfesor){
        ArrayList<BeanDetalleProfAsig> detalle = new ArrayList<BeanDetalleProfAsig>();
        ArrayList<BeanAsignatura> cursos = new ArrayList<BeanAsignatura>();

        daoDetalle daoDetalle = new daoDetalle(getActivity());
        daoAsignatura daoAsigna = new daoAsignatura(getActivity());

        detalle = daoDetalle.listarAsignxProf(codProfesor);
        BeanAsignatura objBean;
        for(int i= 0;i<detalle.size();i++){
            objBean = daoAsigna.buscarAsignatura(detalle.get(i).getIdAsignatura());
            cursos.add(objBean);
            Log.i("array",objBean.getNomAsignatura());
        }
        ListCursoxDoc adapter = new ListCursoxDoc(cursos,getActivity());
        lstCurso.setAdapter(adapter);
    }

}
