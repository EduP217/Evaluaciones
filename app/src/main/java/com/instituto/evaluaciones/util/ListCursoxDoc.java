package com.instituto.evaluaciones.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.instituto.evaluaciones.R;
import com.instituto.evaluaciones.beans.BeanAsignatura;
import com.instituto.evaluaciones.beans.BeanSeccion;

import java.util.ArrayList;

/**
 * Created by Edu on 23/11/2016.
 */

public class ListCursoxDoc extends BaseAdapter {

    Context contexto;
    ArrayList<BeanAsignatura> cursos;

    public ListCursoxDoc(ArrayList<BeanAsignatura> cursos,Context context) {
        this.cursos = cursos;
        this.contexto = context;
    }

    @Override
    public int getCount() {
        return cursos.size();
    }

    @Override
    public Object getItem(int position) {
        return cursos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.adap_princ1, parent,false);

        // Locate the TextViews in listview_item.xml
        TextView txtCursoID = (TextView) itemView.findViewById(R.id.txtCursID);
        TextView txtCurso = (TextView) itemView.findViewById(R.id.txtCursoxSec);
        // Capture position and set to the TextViews
        txtCursoID.setText(cursos.get(position).getCodAsignatura());
        txtCurso.setText(cursos.get(position).getNomAsignatura());

        return itemView;
    }
}
