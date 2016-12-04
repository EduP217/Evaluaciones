package com.instituto.evaluaciones.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.instituto.evaluaciones.R;
import com.instituto.evaluaciones.beans.BeanAsignatura;
import com.instituto.evaluaciones.beans.BeanCiclo;
import com.instituto.evaluaciones.beans.BeanDetalleProfAsig;
import com.instituto.evaluaciones.beans.BeanModalidad;
import com.instituto.evaluaciones.beans.BeanProfesor;
import com.instituto.evaluaciones.beans.BeanPrueba;
import com.instituto.evaluaciones.beans.BeanRegistroNota;
import com.instituto.evaluaciones.beans.BeanSeccion;
import com.instituto.evaluaciones.beans.BeanTipoPrueba;
import com.instituto.evaluaciones.dao.daoAlumno;
import com.instituto.evaluaciones.dao.daoAsignatura;
import com.instituto.evaluaciones.dao.daoCiclo;
import com.instituto.evaluaciones.dao.daoDetalle;
import com.instituto.evaluaciones.dao.daoModalidad;
import com.instituto.evaluaciones.dao.daoNotas;
import com.instituto.evaluaciones.dao.daoPrueba;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Edu on 22/11/2016.
 */

public class ThirdFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    View main_view;
    BeanProfesor objProfesor;

    daoCiclo daociclo;
    daoAsignatura daoasign;
    daoDetalle daodetalle;
    daoPrueba daoprueba;
    daoAlumno daoalumno;
    daoNotas daonota;
    daoModalidad daoMod;

    TextView txtDocExp;
    Spinner spnCicExp;
    Spinner spnModExp;
    Spinner spnCurExp;
    Spinner spnTExExp;
    Spinner spnNExExp;
    Button btnExport;

    String codprof,idasign;
    int idciclo,idmod,idtipo,idprueba;

    SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
    Date date = new Date();
    String fechaActual = curFormater.format(date);
    int cantidadExp=0;

    public void iniciarComponentes(){
        txtDocExp = (TextView) main_view.findViewById(R.id.txtDocenteExp);
        spnCicExp = (Spinner) main_view.findViewById(R.id.spnCicloExp);
        spnCicExp.setOnItemSelectedListener(this);
        spnModExp = (Spinner) main_view.findViewById(R.id.spnModExp);
        spnModExp.setOnItemSelectedListener(this);
        spnCurExp = (Spinner) main_view.findViewById(R.id.spnCursoExp);
        spnCurExp.setOnItemSelectedListener(this);
        spnTExExp = (Spinner) main_view.findViewById(R.id.spnTipopExp);
        spnTExExp.setOnItemSelectedListener(this);
        spnNExExp = (Spinner) main_view.findViewById(R.id.spnPruebaExp);
        spnNExExp.setOnItemSelectedListener(this);
        btnExport = (Button) main_view.findViewById(R.id.btnExportar);
        btnExport.setOnClickListener(this);

        daociclo = new daoCiclo(getActivity());
        daoasign = new daoAsignatura(getActivity());
        daodetalle = new daoDetalle(getActivity());
        daoprueba = new daoPrueba(getActivity());
        daoalumno = new daoAlumno(getActivity());
        daonota = new daoNotas(getActivity());
        daoMod = new daoModalidad(getActivity());
    }

    public void poblarSpinners(){
        ArrayList<BeanCiclo> arregloCiclo = daociclo.listarCiclos();
        ArrayAdapter<BeanCiclo> adaptador = new ArrayAdapter<BeanCiclo>(getActivity(),android.R.layout.simple_list_item_1,arregloCiclo);
        spnCicExp.setAdapter(adaptador);

        ArrayList<BeanModalidad> arregloModalidad = daoMod.listarModalidad("1");
        ArrayAdapter<BeanModalidad> adaptador3 = new ArrayAdapter<BeanModalidad>(getActivity(),android.R.layout.simple_list_item_1,arregloModalidad);
        spnModExp.setAdapter(adaptador3);

        ArrayList<BeanDetalleProfAsig> arregloDetalle = daodetalle.listarAsignxProf(objProfesor.getCodProfesor());
        ArrayList<BeanAsignatura> arreglocursos = new ArrayList<BeanAsignatura>();
        BeanAsignatura objBean;
        for(int i= 0;i<arregloDetalle.size();i++){
            objBean = daoasign.buscarAsignatura(arregloDetalle.get(i).getIdAsignatura());
            arreglocursos.add(objBean);
            Log.i("array",objBean.getNomAsignatura());
        }
        ArrayAdapter<BeanAsignatura> adaptador2 = new ArrayAdapter<BeanAsignatura>(getActivity(),android.R.layout.simple_list_item_1,arreglocursos);
        spnCurExp.setAdapter(adaptador2);

        ArrayList<BeanTipoPrueba> arregloTipoP = daoprueba.listarTipo();
        ArrayAdapter<BeanTipoPrueba> adaptador4 = new ArrayAdapter<BeanTipoPrueba>(getActivity(),android.R.layout.simple_list_item_1,arregloTipoP);
        spnTExExp.setAdapter(adaptador4);

        ArrayList<BeanPrueba> arregloNumP = daoprueba.listarNumP(1);
        ArrayAdapter<BeanPrueba> adaptador5 = new ArrayAdapter<BeanPrueba>(getActivity(),android.R.layout.simple_list_item_1,arregloNumP);
        spnNExExp.setAdapter(adaptador5);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_view = inflater.inflate(R.layout.exportar_layout,container,false);
        iniciarComponentes();

        Bundle bundle = this.getArguments();
        objProfesor = (BeanProfesor) bundle.getSerializable("objprof");
        txtDocExp.setText(objProfesor.getApeProfesor()+" "+objProfesor.getNomProfesor());
        codprof = objProfesor.getCodProfesor();
        poblarSpinners();

        return main_view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spnCicloExp:
                BeanCiclo beanCicloSelect = (BeanCiclo) parent.getItemAtPosition(position);
                idciclo = beanCicloSelect.getCicloID();
                ArrayList<BeanModalidad> arregloModalidad = daoMod.listarModalidad(""+beanCicloSelect.getCodMod());
                ArrayAdapter<BeanModalidad> adaptador = new ArrayAdapter<BeanModalidad>(getActivity(),android.R.layout.simple_list_item_1,arregloModalidad);
                spnModExp.setAdapter(adaptador);
                break;
            case R.id.spnModExp:
                BeanModalidad beanModSelect = (BeanModalidad) parent.getItemAtPosition(position);
                idmod = beanModSelect.getModID();
                break;
            case R.id.spnCursoExp:
                BeanAsignatura beanAsignSelect = (BeanAsignatura) parent.getItemAtPosition(position);
                idasign = beanAsignSelect.getCodAsignatura();
                break;
            case R.id.spnTipopExp:
                BeanTipoPrueba beanTipoPrSelect = (BeanTipoPrueba) parent.getItemAtPosition(position);
                idtipo = beanTipoPrSelect.getTipo();
                ArrayList<BeanPrueba> arregloNumP = daoprueba.listarNumP(idtipo);
                ArrayAdapter<BeanPrueba> adaptador2 = new ArrayAdapter<BeanPrueba>(getActivity(),android.R.layout.simple_list_item_1,arregloNumP);
                spnNExExp.setAdapter(adaptador2);
                break;
            case R.id.spnPruebaExp:
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
        if(v == btnExport){
            HiloExportar bucle = new HiloExportar();
            bucle.execute();
        }
    }

    private class HiloExportar extends AsyncTask<String,Void,Object> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setTitle("Exportando Datos");
            pd.setMessage("Cargando...");
            pd.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            pd.dismiss();
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle(""+fechaActual).setIcon(android.R.drawable.ic_menu_upload);
            if(cantidadExp==0){
                dialog.setMessage(""+o);
            } else {
                dialog.setMessage(""+o+" Total de Registros Exportados: "+cantidadExp);
            }
            dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
        }

        @Override
        protected Object doInBackground(String... params) {
            String resultado = null;
            try{
                HttpClient cliente=new DefaultHttpClient();
                HttpContext contexto=new BasicHttpContext();

                ArrayList<BeanRegistroNota> listaRegistro = daonota.listarRegistros(codprof,idasign,""+idprueba,""+idciclo);
                if(listaRegistro.size()>0){
                    for(BeanRegistroNota x:listaRegistro) {
                        cantidadExp++;
                        String URL = "http://institutoevaluaciones.pe.hu/registrar.php?cod="+x.getCodRegistro()+
                                "&fecreg="+x.getFechaRegistro()+
                                "&nota="+x.getNota()+
                                "&cprof="+x.getCodProfesor()+
                                "&calum="+x.getCodAlumno()+
                                "&casig="+x.getCodAsignatura()+
                                "&cprue="+x.getCodPrueba();
                        HttpGet get = new HttpGet(URL);
                        HttpResponse response = cliente.execute(get, contexto);
                        HttpEntity entity = response.getEntity();
                        resultado = EntityUtils.toString(entity, "UTF-8");
                    }
                } else {
                    resultado = "No existen datos a exportar";
                }
            }catch(Exception ex){
                resultado=ex.getMessage();
            }
            return resultado;
        }
    }
}
