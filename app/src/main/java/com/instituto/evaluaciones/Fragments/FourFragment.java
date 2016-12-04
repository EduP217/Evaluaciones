package com.instituto.evaluaciones.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.instituto.evaluaciones.R;
import com.instituto.evaluaciones.beans.BeanAlumno;
import com.instituto.evaluaciones.beans.BeanAsignatura;
import com.instituto.evaluaciones.beans.BeanCiclo;
import com.instituto.evaluaciones.beans.BeanDetalleProfAsig;
import com.instituto.evaluaciones.beans.BeanProfesor;
import com.instituto.evaluaciones.beans.BeanPrueba;
import com.instituto.evaluaciones.beans.BeanRegistroNota;
import com.instituto.evaluaciones.beans.BeanSeccion;
import com.instituto.evaluaciones.beans.BeanTipoPrueba;
import com.instituto.evaluaciones.dao.daoAlumno;
import com.instituto.evaluaciones.dao.daoAsignatura;
import com.instituto.evaluaciones.dao.daoCiclo;
import com.instituto.evaluaciones.dao.daoDetalle;
import com.instituto.evaluaciones.dao.daoNotas;
import com.instituto.evaluaciones.dao.daoPrueba;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Edu on 22/11/2016.
 */

public class FourFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    View main_view;
    BeanProfesor objProfesor;
    daoCiclo daociclo;
    daoAsignatura daoasign;
    daoDetalle daodetalle;
    daoPrueba daoprueba;
    daoAlumno daoalumno;
    daoNotas daonota;

    ArrayList<BeanRegistroNota> arregloRegistro = new ArrayList<BeanRegistroNota>();
    Map<String,Object> mapNotas = new HashMap<String,Object>();
    Map<String,Object> mapEstadisticas = new HashMap<String,Object>();
    TextView txtSuma,txtProm,txtNMax,txtNMin,txtApr,txtDesap,txtPorcA,txtPorD,txtCantAlum;

    TableLayout tblAlumnos;
    TextView txtDocNota;
    Spinner spnCiclo,spnAsign,spnSeccion,spnTipop,spnPrueba;
    Button btnConsultar,btnConfirmar,btnRegistrar;
    String codprof;
    int idciclo,idseccion,idtipo,idprueba;
    String idasign;

    SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String fechaActual = curFormater.format(date);

    public void iniciarComponentes(){
        tblAlumnos = (TableLayout) main_view.findViewById(R.id.tblAlumno);

        txtDocNota = (TextView) main_view.findViewById(R.id.txtDocNota);
        spnCiclo = (Spinner) main_view.findViewById(R.id.spnCiclo);
        spnCiclo.setOnItemSelectedListener(this);
        spnAsign = (Spinner) main_view.findViewById(R.id.spnAsign);
        spnAsign.setOnItemSelectedListener(this);
        spnSeccion = (Spinner) main_view.findViewById(R.id.spnSeccion);
        spnSeccion.setOnItemSelectedListener(this);
        spnTipop = (Spinner) main_view.findViewById(R.id.spnTipoP);
        spnTipop.setOnItemSelectedListener(this);
        spnPrueba = (Spinner) main_view.findViewById(R.id.spnNumP);
        spnPrueba.setOnItemSelectedListener(this);

        btnConsultar = (Button) main_view.findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(this);
        btnConfirmar = (Button) main_view.findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(this);
        btnRegistrar = (Button) main_view.findViewById(R.id.btnRegistrarForm);
        btnRegistrar.setOnClickListener(this);
        btnRegistrar.setVisibility(View.GONE);

        daociclo = new daoCiclo(getActivity());
        daoasign = new daoAsignatura(getActivity());
        daodetalle = new daoDetalle(getActivity());
        daoprueba = new daoPrueba(getActivity());
        daoalumno = new daoAlumno(getActivity());
        daonota = new daoNotas(getActivity());

        txtCantAlum = (TextView) main_view.findViewById(R.id.txtCantAlumnos);
        txtSuma = (TextView) main_view.findViewById(R.id.txtResSuma);
        txtProm = (TextView) main_view.findViewById(R.id.txtResProm);
        txtNMax = (TextView) main_view.findViewById(R.id.txtResMax);
        txtNMin = (TextView) main_view.findViewById(R.id.txtResMin);
        txtApr  = (TextView) main_view.findViewById(R.id.txtResApr);
        txtDesap = (TextView) main_view.findViewById(R.id.txtResDesa);
        txtPorcA = (TextView) main_view.findViewById(R.id.txtResPorApr);
        txtPorD  = (TextView) main_view.findViewById(R.id.txtResPorDesa);
    }

    public void poblarSpinners(){
        ArrayList<BeanCiclo> arregloCiclo = daociclo.listarCiclos();
        ArrayAdapter<BeanCiclo> adaptador = new ArrayAdapter<BeanCiclo>(getActivity(),android.R.layout.simple_list_item_1,arregloCiclo);
        spnCiclo.setAdapter(adaptador);

        ArrayList<BeanDetalleProfAsig> arregloDetalle = daodetalle.listarAsignxProf(objProfesor.getCodProfesor());
        ArrayList<BeanAsignatura> arreglocursos = new ArrayList<BeanAsignatura>();
        BeanAsignatura objBean;
        for(int i= 0;i<arregloDetalle.size();i++){
            objBean = daoasign.buscarAsignatura(arregloDetalle.get(i).getIdAsignatura());
            arreglocursos.add(objBean);
            Log.i("array",objBean.getNomAsignatura());
        }
        ArrayAdapter<BeanAsignatura> adaptador2 = new ArrayAdapter<BeanAsignatura>(getActivity(),android.R.layout.simple_list_item_1,arreglocursos);
        spnAsign.setAdapter(adaptador2);

        ArrayList<BeanSeccion> arregloSeccion = daociclo.listarSecciones(1);
        ArrayAdapter<BeanSeccion> adaptador3 = new ArrayAdapter<BeanSeccion>(getActivity(),android.R.layout.simple_list_item_1,arregloSeccion);
        spnSeccion.setAdapter(adaptador3);

        ArrayList<BeanTipoPrueba> arregloTipoP = daoprueba.listarTipo();
        ArrayAdapter<BeanTipoPrueba> adaptador4 = new ArrayAdapter<BeanTipoPrueba>(getActivity(),android.R.layout.simple_list_item_1,arregloTipoP);
        spnTipop.setAdapter(adaptador4);

        ArrayList<BeanPrueba> arregloNumP = daoprueba.listarNumP(1);
        ArrayAdapter<BeanPrueba> adaptador5 = new ArrayAdapter<BeanPrueba>(getActivity(),android.R.layout.simple_list_item_1,arregloNumP);
        spnPrueba.setAdapter(adaptador5);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_view = inflater.inflate(R.layout.pendientes_layout,container,false);
        iniciarComponentes();

        Bundle bundle = this.getArguments();
        objProfesor = (BeanProfesor) bundle.getSerializable("objprof");
        txtDocNota.setText(objProfesor.getApeProfesor()+" "+objProfesor.getNomProfesor());
        codprof = objProfesor.getCodProfesor();
        poblarSpinners();
        return main_view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
        switch (parent.getId()) {
            case R.id.spnCiclo:
                BeanCiclo beanCicloSelect = (BeanCiclo) parent.getItemAtPosition(position);
                idciclo = beanCicloSelect.getCicloID();
                ArrayList<BeanSeccion> arregloSeccion = daociclo.listarSecciones(idciclo);
                ArrayAdapter<BeanSeccion> adaptador = new ArrayAdapter<BeanSeccion>(getActivity(),android.R.layout.simple_list_item_1,arregloSeccion);
                spnSeccion.setAdapter(adaptador);
                break;
            case R.id.spnAsign:
                BeanAsignatura beanAsignSelect = (BeanAsignatura) parent.getItemAtPosition(position);
                idasign = beanAsignSelect.getCodAsignatura();
                break;
            case R.id.spnSeccion:
                BeanSeccion beanSeccionSelect = (BeanSeccion) parent.getItemAtPosition(position);
                idseccion = beanSeccionSelect.getSeccionID();
                break;
            case R.id.spnTipoP:
                BeanTipoPrueba beanTipoPrSelect = (BeanTipoPrueba) parent.getItemAtPosition(position);
                idtipo = beanTipoPrSelect.getTipo();
                ArrayList<BeanPrueba> arregloNumP = daoprueba.listarNumP(idtipo);
                ArrayAdapter<BeanPrueba> adaptador2 = new ArrayAdapter<BeanPrueba>(getActivity(),android.R.layout.simple_list_item_1,arregloNumP);
                spnPrueba.setAdapter(adaptador2);
                break;
            case R.id.spnNumP:
                BeanPrueba beanPruebaSelect = (BeanPrueba) parent.getItemAtPosition(position);
                idprueba = beanPruebaSelect.getCodPrueba();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v == btnConsultar){
            btnRegistrar.setVisibility(View.GONE);
            tblAlumnos.removeAllViews();
            mapNotas.clear();
            mapEstadisticas.clear();
            //publicarResutlados();
            //Toast.makeText(getActivity(),""+idciclo+"-"+idseccion+"-"+idtipo+"-"+idprueba+"-"+idasign,Toast.LENGTH_LONG).show();
            List<BeanAlumno> listarAlumnosxSec = daoalumno.listarAlumnosxSec(""+idseccion);
            TableRow row;
            TextView txtcode,txtNombres,txtEstado,txtNotaTitle;
            //NumberPicker edtnota;

            if(listarAlumnosxSec.size()<1){
                Toast.makeText(getActivity(),"No existen alumnos registrados en esa seccion",Toast.LENGTH_SHORT).show();
            } else {
                row = new TableRow(getActivity());
                row.setWeightSum(10);
                txtcode = new TextView(getActivity());
                txtNombres = new TextView(getActivity());
                txtNombres.setPadding(3,1,3,1);
                txtEstado = new TextView(getActivity());
                txtNotaTitle = new TextView(getActivity());
                txtNotaTitle.setPadding(3,0,0,0);

                txtcode.setText("Alumno");
                txtNombres.setText("Apellidos y Nombres");
                txtEstado.setText("Estado");
                txtNotaTitle.setText("Nota");
                
                row.addView(txtcode);
                row.addView(txtNombres);
                row.addView(txtEstado);
                row.addView(txtNotaTitle);
                tblAlumnos.addView(row);
                
                for (BeanAlumno x : listarAlumnosxSec) {
                    row = new TableRow(getActivity());
                    txtcode = new TextView(getActivity());
                    txtNombres = new TextView(getActivity());
                    txtNombres.setLayoutParams(new TableRow.LayoutParams(0 , TableRow.LayoutParams.WRAP_CONTENT, 1));
                    txtNombres.setPadding(3,1,3,1);
                    txtEstado = new TextView(getActivity());
                    txtEstado.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar.make(v, "Matricula Regular", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    });
                    final TextView txtNota = new TextView(getActivity());
                    txtNota.setPadding(3,0,0,0);
                    txtNota.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnRegistrar.setVisibility(View.GONE);
                            mapEstadisticas.clear();
                            final Dialog d = new Dialog(getActivity());
                            d.setTitle("Selecciona la Nota");
                            d.setContentView(R.layout.dialog_number_picker);
                            Button b1 = (Button) d.findViewById(R.id.btnSaveN);
                            final NumberPicker np = (NumberPicker) d.findViewById(R.id.NotaPicker);
                            np.setMaxValue(20);
                            np.setMinValue(0);
                            np.setValue(Integer.parseInt(""+txtNota.getText()));
                            np.setWrapSelectorWheel(false);
                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    txtNota.setText(String.valueOf(np.getValue()));
                                    d.dismiss();
                                }
                            });
                            d.show();
                        }
                    });

                    txtcode.setText(""+x.getCodAlumno());
                    txtNombres.setText(""+x.getApeAlumno() + "," + x.getNomAlumno());
                    txtEstado.setText("M. R.");
                    BeanRegistroNota beanNota = daonota.buscarNotas(codprof,""+x.getCodAlumno(),""+idasign,""+idprueba);
                    if(beanNota!=null){
                        /*Log.i("beanNota",""+beanNota.getCodProfesor());
                        Log.i("beanNota",""+beanNota.getCodAsignatura());
                        Log.i("beanNota",""+beanNota.getFechaRegistro());
                        Log.i("beanNota",""+beanNota.getCodAlumno());
                        Log.i("beanNota",""+beanNota.getNota());*/
                        txtNota.setText(""+beanNota.getNota());
                    } else {
                        txtNota.setText("0");
                    }

                    row.addView(txtcode);
                    row.addView(txtNombres);
                    row.addView(txtEstado);
                    row.addView(txtNota);
                    tblAlumnos.addView(row);
                    mapNotas.put(""+x.getCodAlumno(),txtNota);
                }
            }
        }
        if(v == btnConfirmar){
            btnRegistrar.setVisibility(View.VISIBLE);
            mapEstadisticas.clear();
            //Toast.makeText(getActivity(),"Seleccionaste Confirmar",Toast.LENGTH_LONG).show();
            int cantAprob = 0;
            int cantDesap = 0;
            int notaMax = 1;
            int notaMin = 20;
            int suma = 0;
            int cantTotal = 0;
            int prom = 0;
            int porcenAprob = 0;
            int porcenDesaprob = 0;

            for(Map.Entry<String,Object> entrada:mapNotas.entrySet()){
                cantTotal++;
                String key = entrada.getKey();
                TextView txt = (TextView) entrada.getValue();
                int nota = Integer.parseInt(txt.getText().toString());
                suma += nota;
                if(nota<13){
                    cantDesap++;
                } else {
                    cantAprob++;
                }
                if(nota>notaMax){
                    notaMax = nota;
                }
                if(nota<notaMin){
                    notaMin = nota;
                }
            }
            prom = (int)(suma/cantTotal);
            porcenAprob = (int)(cantAprob*100/cantTotal);
            porcenDesaprob = (int)(cantDesap*100/cantTotal);

            mapEstadisticas.put("cant",cantTotal);
            mapEstadisticas.put("sum",suma);
            mapEstadisticas.put("prom",prom);
            mapEstadisticas.put("max",notaMax);
            mapEstadisticas.put("min",notaMin);
            mapEstadisticas.put("aprobados",cantAprob);
            mapEstadisticas.put("desaproba",cantDesap);
            mapEstadisticas.put("Porcapro",porcenAprob);
            mapEstadisticas.put("Pordesap",porcenDesaprob);

            publicarResutlados();
        }
        if(v == btnRegistrar){
            if(mapEstadisticas.isEmpty()){
                Toast.makeText(getActivity(),"Confirme los datos antes de registrar porfavor.",Toast.LENGTH_SHORT).show();
            } else {

                for (Map.Entry<String, Object> entrada : mapNotas.entrySet()) {
                    String key = entrada.getKey();
                    TextView txt = (TextView) entrada.getValue();
                    int nota = Integer.parseInt(txt.getText().toString());

                    BeanRegistroNota beanNota = daonota.buscarNotas(codprof, key, "" + idasign, "" + idprueba);
                    if (beanNota != null) {
                        beanNota.setNota(nota);
                        daonota.updateNota(beanNota);
                    } else {
                        beanNota = new BeanRegistroNota();
                        beanNota.setCodRegistro(daonota.UltimaNota());
                        beanNota.setNota(nota);
                        beanNota.setFechaRegistro(fechaActual);
                        beanNota.setCodProfesor(codprof);
                        beanNota.setCodAlumno(Integer.parseInt(key));
                        beanNota.setCodAsignatura(idasign);
                        beanNota.setCodPrueba(idprueba);
                        daonota.insertNota(beanNota);
                    }
                }
                mostrarResultados();
            }
        }
    }

    public void publicarResutlados(){

        txtCantAlum.setText(""+mapEstadisticas.get("cant")+" Alumnos");
        txtSuma.setText(""+mapEstadisticas.get("sum"));
        txtProm.setText(""+mapEstadisticas.get("prom"));
        txtNMax.setText(""+mapEstadisticas.get("max"));
        txtNMin.setText(""+mapEstadisticas.get("min"));
        txtApr.setText(""+mapEstadisticas.get("aprobados"));
        txtDesap.setText(""+mapEstadisticas.get("desaproba"));
        txtPorcA.setText(""+mapEstadisticas.get("Porcapro")+"  %");
        txtPorD.setText(""+mapEstadisticas.get("Pordesap")+"  %");

    }

    public void mostrarResultados(){
        BeanAsignatura basig = daoasign.buscarAsignatura(""+idasign);
        BeanPrueba bpr = daoprueba.buscarPrueba(""+idprueba);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_register_success,null);
        builder.setView(v);
        builder.setIcon(android.R.drawable.ic_menu_info_details);
        builder.setTitle("Registro de Notas");
        TextView txtfecha = (TextView)v.findViewById(R.id.txtFechaRes);
        TextView txtdoc = (TextView)v.findViewById(R.id.txtDocRes);
        TextView txtasign = (TextView)v.findViewById(R.id.txtAsigRes);
        TextView txtprueb = (TextView)v.findViewById(R.id.txtPrRes);
        TextView txtcantA = (TextView)v.findViewById(R.id.txtCantRes);
        TextView txtMsg = (TextView)v.findViewById(R.id.txtMsgRes);

        txtfecha.setText(fechaActual);
        txtdoc.setText(objProfesor.getApeProfesor()+" "+objProfesor.getNomProfesor());
        txtasign.setText(basig.getNomAsignatura());
        txtprueb.setText(bpr.getNumPrueba());
        txtcantA.setText(""+mapEstadisticas.get("cant"));
        txtMsg.setText("LOS DATOS SE REGISTRARON CORRECTAMENTE EN EL SISTEMA.");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
