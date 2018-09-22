package com.uteq.sistemas.ventasexpress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.fragment.ClientePedidosFragment;
import com.uteq.sistemas.ventasexpress.fragment.ClientePerfilFragment;
import com.uteq.sistemas.ventasexpress.fragment.VendedorPedidosFragment;
import com.uteq.sistemas.ventasexpress.model.Cliente;
import com.uteq.sistemas.ventasexpress.utils.Constants;

import java.util.List;

public class ClienteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Constants.fragmentManager = getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView TxtCliente = (TextView) hView.findViewById(R.id.TxtCliente);
        TextView TxtNombreCLiente = (TextView) hView.findViewById(R.id.TxtNombreCLiente);
        TxtNombreCLiente.setText(Constants.cliente.getNegocio());
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
        getMenuInflater().inflate(R.menu.main, menu);
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
            fragment = new ClientePedidosFragment();
            fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();

        } else if(id == R.id.nav_gallery){

            fragment= new ClientePerfilFragment();
            fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_cerrar) {
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
            Intent intent = new Intent(ClienteActivity.this, LoginActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_salir) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
