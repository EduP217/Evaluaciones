package com.instituto.evaluaciones;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.IslamicCalendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
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

import com.instituto.evaluaciones.beans.beanUsuario;
import com.instituto.evaluaciones.conexion.bdconexion;
import com.instituto.evaluaciones.util.backgroundImage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private bdconexion db;
    private beanUsuario usuario;
    private ImageView imgAvatar;
    private TextView txtuserMenu;
    private TextView txtperfMenu;
    LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
    View v = inflater.inflate(R.layout.nav_header_main,null);
    /*---MENÚ DESPLEGABLE---*/
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    private void iniciarComponentes(){
        db = new bdconexion(this);
        /*--MENÚ DESPLEGABLE--*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        txtuserMenu = (TextView) v.findViewById(R.id.txtUser_Menu);
        txtperfMenu = (TextView) v.findViewById(R.id.txtPerfil_Menu);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            txtuserMenu.setText(usuario.getApellido()+" "+usuario.getNombre());
            txtperfMenu.setText(usuario.getPerfil());
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

        if (id == R.id.nav_main) {

        } else if (id == R.id.nav_download) {

        } else if (id == R.id.nav_upload) {

        } else if (id == R.id.nav_work) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        if(v == fab){
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    private void mostrarAvatar(String urlImagen) throws IOException {
        backgroundImage bI = new backgroundImage(this);
        bI.execute(urlImagen);
        /*try{
            ContextWrapper cw = new ContextWrapper(this);
            File dirImages = cw.getDir("Imagenes", this.MODE_PRIVATE);
            File myPath = new File(dirImages,urlImagen);
            avatar = BitmapFactory.decodeFile(myPath.getAbsolutePath());
            imgAvatar.setImageBitmap(avatar);
        } catch (Exception ex){
            Log.e("-->No encontrado:",ex.toString());
        }*/
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
