package com.instituto.evaluaciones.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.instituto.evaluaciones.MainActivity;
import com.instituto.evaluaciones.R;
import com.instituto.evaluaciones.beans.BeanAlumno;
import com.instituto.evaluaciones.beans.BeanAsignatura;
import com.instituto.evaluaciones.beans.BeanCiclo;
import com.instituto.evaluaciones.beans.BeanDetalleProfAsig;
import com.instituto.evaluaciones.beans.BeanModalidad;
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
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by NelsonDennis on 19/11/2016.
 */

public class backgroundImportar extends AsyncTask<String,Void,Object> {

    Context contexto;
    ProgressDialog pd;

    public backgroundImportar(Context ctx){
        contexto = ctx;
    }

    @Override
    protected Object doInBackground(String... params) {
        Log.i("doInBack","Inicio de la importación");

        daoAlumno daoAlumn = new daoAlumno(contexto);
        daoModalidad daoMod = new daoModalidad(contexto);
        daoCiclo daoCic = new daoCiclo(contexto);
        daoAsignatura daoAsign = new daoAsignatura(contexto);
        daoDetalle daoDeta = new daoDetalle(contexto);
        daoNotas daoNota = new daoNotas(contexto);
        daoPrueba daoPrueb = new daoPrueba(contexto);

        String resultado;

        try{
            HttpClient cliente = new DefaultHttpClient();
            HttpContext contexto = new BasicHttpContext();

            String URLAlumnos = "http://institutoevaluaciones.pe.hu/listadoAlumnos.php";
            HttpGet getAlumnos = new HttpGet(URLAlumnos);
            HttpResponse response1 = cliente.execute(getAlumnos,contexto);
            HttpEntity entity1 = response1.getEntity();
            resultado = EntityUtils.toString(entity1,"UTF-8");

            JSONArray arregloAlumnos = new JSONArray(resultado);

            BeanAlumno beanAlum;

            for(int i=0;i<arregloAlumnos.length();i++) {
                beanAlum = new BeanAlumno();
                beanAlum.setCodAlumno(arregloAlumnos.getJSONObject(i).getInt("codAlumno"));
                beanAlum.setNomAlumno(arregloAlumnos.getJSONObject(i).getString("nomAlumno"));
                beanAlum.setApeAlumno(arregloAlumnos.getJSONObject(i).getString("apeAlumno"));
                beanAlum.setCodSeccion(arregloAlumnos.getJSONObject(i).getInt("codSeccion"));

                BeanAlumno ba2 = daoAlumn.buscarAlumno("" + beanAlum.getCodAlumno());
                if (ba2 == null) {
                    daoAlumn.insertAlumno(beanAlum);
                } else {
                    daoAlumn.updateAlumno(beanAlum);
                }
            }

            String URLSeccion = "http://institutoevaluaciones.pe.hu/listadoSeccion.php";
            HttpGet getSeccion = new HttpGet(URLSeccion);
            HttpResponse response2 = cliente.execute(getSeccion,contexto);
            HttpEntity entity2 = response2.getEntity();
            resultado = EntityUtils.toString(entity2,"UTF-8");

            JSONArray arregloSeccion = new JSONArray(resultado);

            BeanSeccion beanSecc;

            for(int i=0;i<arregloSeccion.length();i++){
                beanSecc = new BeanSeccion();
                beanSecc.setSeccionID(arregloSeccion.getJSONObject(i).getInt("codSeccion"));
                beanSecc.setSeccion(arregloSeccion.getJSONObject(i).getString("nomSeccion"));
                beanSecc.setCodCiclo(arregloSeccion.getJSONObject(i).getInt("codCiclo"));

                BeanSeccion bs2 = daoCic.buscarSeccion(""+beanSecc.getSeccionID());
                if(bs2==null){
                    daoCic.insertSeccion(beanSecc);
                } else {
                    daoCic.updateSeccion(beanSecc);
                }
            }

            String URLCiclo = "http://institutoevaluaciones.pe.hu/listadoCiclo.php";
            HttpGet getCiclo = new HttpGet(URLCiclo);
            HttpResponse response3 = cliente.execute(getCiclo,contexto);
            HttpEntity entity3 = response3.getEntity();
            resultado = EntityUtils.toString(entity3,"UTF-8");

            JSONArray arregloCiclo = new JSONArray(resultado);

            BeanCiclo beanCiclo;

            for(int i=0;i<arregloCiclo.length();i++){
                beanCiclo = new BeanCiclo();
                beanCiclo.setCicloID(arregloCiclo.getJSONObject(i).getInt("codCiclo"));
                beanCiclo.setCiclo(arregloCiclo.getJSONObject(i).getString("nomCiclo"));
                beanCiclo.setCodMod(arregloCiclo.getJSONObject(i).getInt("codModalidad"));

                BeanCiclo bc2 = daoCic.buscarCiclo(""+beanCiclo.getCicloID());
                if(bc2==null){
                    daoCic.insertCiclo(beanCiclo);
                } else {
                    daoCic.updateCiclo(beanCiclo);
                }
            }

            String URLModal = "http://institutoevaluaciones.pe.hu/listadoModalidad.php";
            HttpGet getModal = new HttpGet(URLModal);
            HttpResponse response4 = cliente.execute(getModal,contexto);
            HttpEntity entity4 = response4.getEntity();
            resultado = EntityUtils.toString(entity4,"UTF-8");

            JSONArray arregloModal = new JSONArray(resultado);

            BeanModalidad beanModal;

            for(int i=0;i<arregloModal.length();i++){
                beanModal = new BeanModalidad();
                beanModal.setModID(arregloModal.getJSONObject(i).getInt("codModalidad"));
                beanModal.setModalidad(arregloModal.getJSONObject(i).getString("nomModalidad"));

                BeanModalidad bm2 = daoMod.buscarModal(""+beanModal.getModID());
                if(bm2==null){
                    daoMod.insertModalidad(beanModal);
                } else {
                    daoMod.updateModalidad(beanModal);
                }
            }

            String URLAsignat = "http://institutoevaluaciones.pe.hu/listadoAsignatura.php";
            HttpGet getAsignat = new HttpGet(URLAsignat);
            HttpResponse response5 = cliente.execute(getAsignat,contexto);
            HttpEntity entity5 = response5.getEntity();
            resultado = EntityUtils.toString(entity5,"UTF-8");

            JSONArray arregloAsign = new JSONArray(resultado);

            BeanAsignatura beanAsign;

            for(int i=0;i<arregloAsign.length();i++){
                beanAsign = new BeanAsignatura();
                beanAsign.setCodAsignatura(arregloAsign.getJSONObject(i).getString("codAsignatura"));
                beanAsign.setNomAsignatura(arregloAsign.getJSONObject(i).getString("nomAsignatura"));

                BeanAsignatura ba2 = daoAsign.buscarAsignatura(""+beanAsign.getCodAsignatura());
                if(ba2==null){
                    daoAsign.insertAsignatura(beanAsign);
                } else {
                    daoAsign.updateAsignatura(beanAsign);
                }
            }

            String URLPrueba = "http://institutoevaluaciones.pe.hu/listadoPrueba.php";
            HttpGet getPrueba = new HttpGet(URLPrueba);
            HttpResponse response6 = cliente.execute(getPrueba,contexto);
            HttpEntity entity6 = response6.getEntity();
            resultado = EntityUtils.toString(entity6,"UTF-8");

            JSONArray arregloPrueba = new JSONArray(resultado);

            BeanPrueba beanPrueba;

            for(int i=0;i<arregloPrueba.length();i++){
                beanPrueba = new BeanPrueba();
                beanPrueba.setCodPrueba(arregloPrueba.getJSONObject(i).getInt("codPrueba"));
                beanPrueba.setNumPrueba(arregloPrueba.getJSONObject(i).getString("numPrueba"));
                beanPrueba.setCodTipo(arregloPrueba.getJSONObject(i).getInt("codTipo"));

                BeanPrueba bp2 = daoPrueb.buscarPrueba(""+beanPrueba.getCodPrueba());
                if(bp2==null){
                    daoPrueb.insertPrueba(beanPrueba);
                } else {
                    daoPrueb.updatePrueba(beanPrueba);
                }
            }

            String URLTipoPrueba = "http://institutoevaluaciones.pe.hu/listadoTipoPrueba.php";
            HttpGet getTipoP = new HttpGet(URLTipoPrueba);
            HttpResponse response7 = cliente.execute(getTipoP,contexto);
            HttpEntity entity7 = response7.getEntity();
            resultado = EntityUtils.toString(entity7,"UTF-8");

            JSONArray arregloTipoP = new JSONArray(resultado);

            BeanTipoPrueba beanTipo;

            for(int i=0;i<arregloTipoP.length();i++){
                beanTipo = new BeanTipoPrueba();
                beanTipo.setTipo(arregloTipoP.getJSONObject(i).getInt("codTipo"));
                beanTipo.setNomTipo(arregloTipoP.getJSONObject(i).getString("nomTipo"));

                BeanTipoPrueba btp2 = daoPrueb.buscarTipo(""+beanTipo.getTipo());
                if(btp2==null){
                    daoPrueb.insertTipoPrueba(beanTipo);
                } else {
                    daoPrueb.updateTipoPrueba(beanTipo);
                }
            }

            String URLNotas = "http://institutoevaluaciones.pe.hu/listadoNotas.php";
            HttpGet getNotas = new HttpGet(URLNotas);
            HttpResponse response8 = cliente.execute(getNotas,contexto);
            HttpEntity entity8 = response8.getEntity();
            resultado = EntityUtils.toString(entity8,"UTF-8");

            JSONArray arregloNotas = new JSONArray(resultado);

            BeanRegistroNota beanNota;

            for(int i=0;i<arregloNotas.length();i++){
                beanNota = new BeanRegistroNota();
                beanNota.setCodRegistro(arregloNotas.getJSONObject(i).getInt("codRegistro"));
                beanNota.setFechaRegistro(arregloNotas.getJSONObject(i).getString("fechaReg"));
                beanNota.setNota(arregloNotas.getJSONObject(i).getInt("nota"));
                beanNota.setCodProfesor(arregloNotas.getJSONObject(i).getString("codProfesor"));
                beanNota.setCodAlumno(arregloNotas.getJSONObject(i).getInt("codAlumno"));
                beanNota.setCodAsignatura(arregloNotas.getJSONObject(i).getString("codAsignatura"));
                beanNota.setCodPrueba(arregloNotas.getJSONObject(i).getInt("codPrueba"));

                BeanRegistroNota brn2 = daoNota.buscarNotas(""+beanNota.getCodProfesor(),""+beanNota.getCodAlumno(),""+beanNota.getCodAsignatura(),""+beanNota.getCodPrueba());
                if(brn2==null){
                    daoNota.insertNota(beanNota);
                } else {
                    daoNota.updateNota(beanNota);
                }
            }

            String URLDetalle = "http://institutoevaluaciones.pe.hu/listadoDetalle.php";
            HttpGet getDetalle = new HttpGet(URLDetalle);
            HttpResponse response9 = cliente.execute(getDetalle,contexto);
            HttpEntity entity9 = response9.getEntity();
            resultado = EntityUtils.toString(entity9,"UTF-8");

            JSONArray arregloDetalle = new JSONArray(resultado);

            BeanDetalleProfAsig beanDetalle;

            for(int i=0;i<arregloDetalle.length();i++){
                beanDetalle = new BeanDetalleProfAsig();
                beanDetalle.setIdProfesor(arregloDetalle.getJSONObject(i).getString("codProfesor"));
                beanDetalle.setIdAsignatura(arregloDetalle.getJSONObject(i).getString("codAsignatura"));

                BeanDetalleProfAsig bdpa2 = daoDeta.buscarDetalle(""+beanDetalle.getIdProfesor(),""+beanDetalle.getIdAsignatura());
                if(bdpa2==null){
                    daoDeta.insertDetalle(beanDetalle);
                } else {

                }
            }

        }catch(IOException ex){
            resultado=ex.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = ProgressDialog.show(contexto,"Importando-Datos","Espere porfavor..",true,false);
        pd.setIcon(android.R.drawable.ic_input_get);
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        pd.dismiss();
        Log.i("OnPostExec","Fin de la importación");
    }

}

