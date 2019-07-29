package com.organizate.Actividades;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.organizate.Dialogos.DialogoConfirmacion;
import com.organizate.Dialogos.DialogoNTag;
import com.organizate.Dialogos.DialogoNTarea;
import com.organizate.Entidades.VOActividad;
import com.organizate.Entidades.VOTag;
import com.organizate.Fragmentos.EtiquetasFragment;
import com.organizate.Fragmentos.HechoFragment;
import com.organizate.Fragmentos.TareasFragment;
import com.organizate.Modelos.DAOActividad;
import com.organizate.Modelos.DAOEtiquetas;
import com.organizate.Modelos.DAOTareas;
import com.organizate.Notificaciones.DialogoAlerta;
import com.organizate.R;
import com.organizate.Notificaciones.CentralizarToasts;
import com.organizate.Utils.FrasesActividad;
import com.organizate.Utils.Utils;
import com.organizate.Entidades.VOTarea;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EtiquetasFragment.OnFragmentInteractionListener,
        HechoFragment.OnFragmentInteractionListener, TareasFragment.OnFragmentInteractionListener,
        DialogoNTarea.DialogoNTareaListener, DialogoNTag.DialogoNTagListener, DialogoConfirmacion.MiDialogListener {

    //Controles
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    FloatingActionButton fab;

    //Instancias de los fragmentos
    private TareasFragment tareasFragment;
    private HechoFragment hechoFragment;
    private EtiquetasFragment etiquetasFragment;

    //Getters fragmentos
    public TareasFragment getTareasFragment() {
        return tareasFragment;
    }
    public HechoFragment getHechoFragment() {
        return hechoFragment;
    }
    public EtiquetasFragment getEtiquetasFragment() {
        return etiquetasFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //PageAdapter
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Floating button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0: {
                        int numTags = new DAOEtiquetas(getApplicationContext()).sacarNumEtiquetas();
                        if (numTags > 0) {
                            new DialogoNTarea(MainActivity.this, MainActivity.this);
                        }
                        else {
                            DialogoAlerta.mostrarDialogo(getSupportFragmentManager(),
                                    getString(R.string.no_tags), getString(R.string.añadir_tags));
                        }
                    }
                    break;
                    case 2: new DialogoNTag(MainActivity.this, MainActivity.this);
                    break;
                }
            }
        });

        //Tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tasks);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_done);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tag);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1: fab.hide();
                    break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1: fab.show();
                    break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    //Añadimos los fragmentos
    private void anadirFragmento(Fragment fragment, String titulo) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(fragment, titulo);
            transaction.commit();
        }
        catch (Exception err) {
            DialogoAlerta.mostrarDialogo(getSupportFragmentManager(), "Error", err.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSalir: {
                DialogoConfirmacion dialogoConfirmacion = new DialogoConfirmacion();
                Bundle bundle = new Bundle();

                bundle.putString("TITULO", getString(R.string.confirmación));
                bundle.putString("MENSAJE", getString(R.string.deseas_salir));
                dialogoConfirmacion.setArguments(bundle);

                dialogoConfirmacion.show(getSupportFragmentManager(), Utils.CERRAR);
            }
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    //INTERFAZ PARA QUE LOS FRAGMENTOS SE COMUNIQUEN CON LA ACTIVIDAD QUE LOS CONTIENE
    @Override
    public void onFragmentInteraction(String tarea) {

    }

    //Aceptamos la tarea devolviendo el nombre y la etiqueta
    @Override
    public void AceptarTarea(String nTarea, String nTag) {
        try {
            VOTarea tarea = new VOTarea();

            //Datos de la insercción
            tarea.setNombre(nTarea);
            tarea.settEtiqueta(nTag);
            tarea.setF_Creacion(Utils.sacarFechaHoy());

            //Insertamos
            new DAOTareas(getApplicationContext()).insertTarea(tarea);
            CentralizarToasts.centralizarToastsCorto(getApplicationContext(), getString(R.string.tarea_insertada));

            //Añadimos un registro de que se ha insertado
            VOActividad actividad = new VOActividad();
            actividad.setDescripcionActividad(FrasesActividad.CREACION);
            int idTarea = new DAOTareas(getApplicationContext()).sacarIdTarea(tarea.getNombre());
            if (idTarea != -1) {
                actividad.setIdTarea(idTarea);
                new DAOActividad(getApplicationContext()).insertActividad(actividad);
            }
            else {
                throw new Exception(getString(R.string.ocurrir_error));
            }

            //Actualizamos la lista
            tareasFragment.leerTareas();
        }
        catch (Exception err) {
            DialogoAlerta.mostrarDialogo(getSupportFragmentManager(), "Error", err.getMessage());
        }
    }

    //Aceptamos la etiqueta devolviédola
    @Override
    public void AceptarTag(VOTag tag) {
        try {
            new DAOEtiquetas(MainActivity.this).insertarEtiqueta(tag);
            etiquetasFragment.leerEtiquetas();      //Refrescamos la lista de etiquetas
        }
        catch (Exception err) {
            CentralizarToasts.centralizarToastsCorto(getApplicationContext(), err.getMessage());
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (dialog.getTag().equals(Utils.CERRAR)) {
            finish();
        }
    }

    //Retornamos cada fragmento seleccionado
    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        public static Fragment newInstance(int sectionNumber) {
            Fragment fragment = null;

            switch (sectionNumber) {
                case 1: fragment = TareasFragment.newInstance();
                break;
                case 2: fragment = HechoFragment.newInstance();
                break;
                case 3: fragment = EtiquetasFragment.newInstance();
                break;
            }

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    //Clase para el adaptador
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = PlaceholderFragment.newInstance(position + 1);

            //Guardamos la instancia
            switch (position) {
                case 0: {
                    tareasFragment = (TareasFragment) fragment;
                    anadirFragmento(fragment, getString(R.string.por_hacer));
                }
                break;
                case 1: {
                    hechoFragment = (HechoFragment) fragment;
                    anadirFragmento(fragment, getString(R.string.hecho));
                }
                break;
                case 2: {
                    etiquetasFragment = (EtiquetasFragment) fragment;
                    anadirFragmento(fragment, getString(R.string.etiquetas));
                }
                break;
            }

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public CharSequence getPageTitle(int itemPosition) {
            switch (itemPosition) {
                case 0: return getString(R.string.por_hacer);
                case 1: return getString(R.string.hecho);
                case 2: return getString(R.string.etiquetas);
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}