package com.instituto.evaluaciones;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.instituto.evaluaciones.Fragments.FirstFragment;
import com.instituto.evaluaciones.Fragments.FourFragment;
import com.instituto.evaluaciones.Fragments.SecondFragment;
import com.instituto.evaluaciones.Fragments.SettingsFragment;
import com.instituto.evaluaciones.Fragments.ThirdFragment;
import com.instituto.evaluaciones.beans.BeanProfesor;
import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.dao.daoUsuario;
import com.instituto.evaluaciones.util.backgroundImportar;

import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private bdconexion db;
    private daoUsuario dao;
    private beanUsuario usuario;
    private BeanProfesor profesor;
    Bitmap intentImage = null;
    private ImageView imgMenu;
    private TextView txtCodMenu;
    private TextView txtNomMenu;
    private TextView txtPerfMenu;
    /*---MENÚ DESPLEGABLE---*/
    Toolbar toolbar;
    //FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    private void iniciarComponentes(){
        db = new bdconexion(this);
        dao = new daoUsuario(this);
        /*--MENÚ DESPLEGABLE--*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*---------------------*/
        View nView =  navigationView.getHeaderView(0);
        imgMenu = (ImageView) nView.findViewById(R.id.imgMenu);
        txtCodMenu = (TextView) nView.findViewById(R.id.txtCodMenu);
        txtNomMenu = (TextView) nView.findViewById(R.id.txtNomMenu);
        txtPerfMenu = (TextView) nView.findViewById(R.id.txtPerfilMenu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarComponentes();
        //setSupportActionBar(toolbar);
        //RECUPERAR EL INTENT "IR"
        Intent re = getIntent();
        if (re.getSerializableExtra("obj") != null) {
            usuario = (beanUsuario) re.getSerializableExtra("obj");
            try {
                FileInputStream is = this.openFileInput(usuario.getUrlImagen());
                intentImage = BitmapFactory.decodeStream(is);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            imgMenu.setImageBitmap(intentImage);
            if(re.getSerializableExtra("objProf")!=null){
                profesor = (BeanProfesor) re.getSerializableExtra("objProf");
                txtCodMenu.setText(profesor.getCodProfesor());
                txtNomMenu.setText(profesor.getApeProfesor()+", "+profesor.getNomProfesor());
                txtPerfMenu.setText(usuario.getPerfil());
                if(dao.estadoSetting(profesor.getCodProfesor())!=0) {
                    backgroundImportar importarEstaticos = new backgroundImportar(this);
                    importarEstaticos.execute();
                }
            }
        } else {
            goLoginScreen();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentmanager = getFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putSerializable("objprof", profesor);

        if (id == R.id.nav_main) {
            bundle.putString("idprof", profesor.getCodProfesor() );
            FirstFragment firstFragment=new FirstFragment();
            firstFragment.setArguments(bundle);
            fragmentmanager.beginTransaction().replace(R.id.content_frame,firstFragment).commit();
        } else if (id == R.id.nav_download) {
            SecondFragment secondFragment=new SecondFragment();
            secondFragment.setArguments(bundle);
            fragmentmanager.beginTransaction().replace(R.id.content_frame,secondFragment).commit();
        } else if (id == R.id.nav_upload) {
            ThirdFragment thirdFragment=new ThirdFragment();
            thirdFragment.setArguments(bundle);
            fragmentmanager.beginTransaction().replace(R.id.content_frame,thirdFragment).commit();
        } else if (id == R.id.nav_work) {
            FourFragment fourtFragment=new FourFragment();
            fourtFragment.setArguments(bundle);
            fragmentmanager.beginTransaction().replace(R.id.content_frame,fourtFragment).commit();
        } else if (id == R.id.nav_setting) {
            SettingsFragment settFragment = new SettingsFragment();
            settFragment.setArguments(bundle);
            fragmentmanager.beginTransaction().replace(R.id.content_frame,settFragment).commit();
        } else if (id == R.id.nav_logout) {
            final AlertDialog.Builder builderLogOff = new AlertDialog.Builder(MainActivity.this);
            builderLogOff.setTitle("Cerrar Sesión").setIcon(android.R.drawable.ic_menu_close_clear_cancel).setMessage("¿Estas seguro de salir de la sesión?").setCancelable(false);
            builderLogOff.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
            builderLogOff.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builderLogOff.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        /*if(v == fab){
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }*/
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
