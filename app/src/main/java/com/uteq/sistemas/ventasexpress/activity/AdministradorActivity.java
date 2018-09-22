package com.uteq.sistemas.ventasexpress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.fragment.AdminRutaListaEmpl;
import com.uteq.sistemas.ventasexpress.fragment.AdministradorCategoriasFragment;
import com.uteq.sistemas.ventasexpress.fragment.AdministradorEmpleadosBonificacionFragment;
import com.uteq.sistemas.ventasexpress.fragment.AdministradorEmpleadosFragment;
import com.uteq.sistemas.ventasexpress.fragment.AdministradorProductosFragment;
import com.uteq.sistemas.ventasexpress.fragment.AdministradorTasaBonificacion;
import com.uteq.sistemas.ventasexpress.fragment.VendedorAFragment;
import com.uteq.sistemas.ventasexpress.utils.Constants;

import java.util.List;

public class AdministradorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Constants.fragmentManager = getSupportFragmentManager();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new VendedorAFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.administrador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragment = new AdministradorCategoriasFragment();
            fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_gallery) {
            fragment = new AdministradorProductosFragment();
            fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_slideshow) { // empleado
            fragment = new AdministradorEmpleadosFragment();
            fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_manage) {//bonificaciones
            fragment = new AdministradorEmpleadosBonificacionFragment();
            fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_tasa) {//bonificacionesTasa
            fragment = new AdministradorTasaBonificacion();
            fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_rutas) {
            fragment = new AdminRutaListaEmpl();
            fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();

        }  else if (id == R.id.nav_cerrar) {
            FragmentManager fm = getSupportFragmentManager();
            List<Fragment> al = fm.getFragments();
            if (al != null) {
                for (Fragment frag : al) {
                    if (frag != null) {
                        getSupportFragmentManager().beginTransaction().remove(frag).commit();
                        fm.popBackStack();
                    }
                }
            }
            Intent intent = new Intent(AdministradorActivity.this, LoginActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_salir) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
