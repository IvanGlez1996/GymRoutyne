package pem.tema4.vista;

import pem.tema4.AppMediador;
import pem.tema4.presentador.IPresentadorPrincipal;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class VistaPrincipal extends AppCompatActivity implements IVistaPrincipal,
		FragmentoMaestro.EscuchaFragmento, NavigationView.OnNavigationItemSelectedListener {
	
	private AppMediador appMediador;
	private IPresentadorPrincipal presentadorPrincipal;
	private FragmentoMaestro fragmentoMaestro;
	private FragmentoDetalle fragmentoDetalle;
    // TODO Declarar un objeto llamado fab, que corresponda con un botón flotante
	private FloatingActionButton fab;

	/*@Override
	protected  void onResume(){
		super.onResume();
		presentadorPrincipal.obtenerRutinas();
	}*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routines);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		appMediador = (AppMediador)this.getApplication();
		presentadorPrincipal = appMediador.getPresentadorPrincipal();
		appMediador.setVistaPrincipal(this);
		// Se comprueba si la actividad está usando una versión de layout con un contenedor de fragmentos
		// de tipo FrameLayout (si es así, es un smartphone y no permite más de un fragmento en pantalla),
		// por tanto, sólo se añade el primero
		if (findViewById(R.id.contenedorDeFragmentos) != null) {
			// se crea el fragmento maestro y se añade al contenedor de fragmentos
			fragmentoMaestro = new FragmentoMaestro();
			getSupportFragmentManager().beginTransaction()
				.add(R.id.contenedorDeFragmentos, fragmentoMaestro)
				.commit();
		} else {
			// Si el layout no es de panel único (es una tableta) se permiten más de un fragmento
			// por tanto, se crean los dos fragmentos y se añaden a sus layouts según el xml sw600dp
			fragmentoMaestro = new FragmentoMaestro();
			getSupportFragmentManager().beginTransaction()
				.add(R.id.contenedor_lista, fragmentoMaestro)
				.commit();
			
			fragmentoDetalle = new FragmentoDetalle();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedor_detalle, fragmentoDetalle)
                    .commit();
		}
		// TODO Crear un botón flotante y, cuando se seleccione, solicitar al presentador principal que trate 
		// la opción de agregar una nueva receta
		fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				//TODO Solicitar al presentador para que agregue una nueva receta
				presentadorPrincipal.tratarAgregar();
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

	}

	
	@Override
	protected void onStart() {
		super.onStart();
		// TODO Solicitar al presentador que recupere los datos desde el modelo.
		presentadorPrincipal.obtenerRutinas();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		appMediador.removePresentadorPrincipal();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.vista_principal, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_salir:
		    int id = android.os.Process.myPid();
		    android.os.Process.killProcess(id);
		}
	    return super.onOptionsItemSelected(item);
	}

    @Override
    public void alSeleccionarItem(int posicion) {
        // Si no hay fragmento detalle, se crea la vista detalle (esto ocurre si es panel único)
        if (fragmentoDetalle == null)
            fragmentoDetalle = new FragmentoDetalle();

        if (findViewById(R.id.contenedorDeFragmentos) != null) {
            // si es de panel único, se reemplaza, en el contenedor de fragmentos
            // el fragmento que está visible por el de la vista detalle
            FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
            transaccion.replace(R.id.contenedorDeFragmentos, fragmentoDetalle);
            transaccion.addToBackStack(null);
            transaccion.commit();
            // TODO Quitar la visibilidad al botón flotante (para que no aparezca en el detalle)
			fab.setVisibility(View.GONE);
            // realiza la transacción
            getSupportFragmentManager().executePendingTransactions();
        }
        // TODO Solicitar al presentador que trate el item seleccionado.
		presentadorPrincipal.obtenerEjercicios(posicion);
    }




    // TODO Redefinir el método onBackPressed para que si se tiene un dispositivo de panel único, y el botón
    // flotante no está visible (está el fragmento detalle en pantalla), reemplace el fragmento detalle por el
    // fragmento maestro. En cualquier otro caso, la actividad debe finalizar (porque se quiere salir de ella)
	@Override
	public void onBackPressed(){
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (findViewById(R.id.contenedorDeFragmentos) != null){
			//Es panel único
			if(fab.getVisibility() != View.VISIBLE){
				//Está en la vista del detalle
				FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
				transaccion.replace(R.id.contenedorDeFragmentos, fragmentoMaestro);
				transaccion.addToBackStack(null);
				transaccion.commit();
				fab.setVisibility(View.VISIBLE);
				presentadorPrincipal.obtenerRutinas();
			} else{
				//No está en la vista del detalle
				finish();
			}
		} else {
			//No es panel único
			finish();
		}

		/*if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}*/
	}

    // TODO Añadir el método actualizarMaestro(Object[] datos) que actualiza la lista maestro con los datos
	// recibidos por parámetros. En cada entrada del vector, está el nombre de una receta.	
	@Override
	public void actualizarMaestro(Object[] datos) {
		// TODO Dentro del método actualizarMaestro(Object[] datos), crear la lista maestro con los nombres 
		// de las recetas que entran por parámetros.
		fragmentoMaestro.crearLista((String[]) datos);
		// TODO Dentro del método actualizarMaestro(Object[] datos), si es una pantalla multi-panel, presentar 
		// el detalle de la primera receta.
		if (findViewById(R.id.contenedorDeFragmentos) == null){
			presentadorPrincipal.obtenerEjercicios(0);
		}
	}


	// TODO Añadir el método actualizarDetalle(Object[] datos) que actualiza los valores del detalle, 
	// teniendo en cuenta que en la posición 0 del vector está el nombre de la receta y en qué se usa 
	// para realizarla, en la posición 1 del vector está la imagen como un Bitmap y en en la posición 3 
	// del vector está la descripción de la receta.
	@Override
	public void actualizarDetalle(Object[] datos) {
		fragmentoDetalle.actualizarNombreReceta((String) datos[0]);
		fragmentoDetalle.actualizarDescripcion((String) datos[1]);
	}

	@Override
	public void presentarAlerta(final int posicion) {
		AlertDialog.Builder alerta = new AlertDialog.Builder(this);
		alerta.setTitle("Aviso");
		alerta.setMessage("¿De verdad quiere eliminar esta receta?");
		alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				presentadorPrincipal.eliminarReceta(posicion);
			}
		});
		alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				presentadorPrincipal.obtenerRutinas();
			}
		});
		alerta.show();
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_camera) {
			// Handle the camera action
		} else if (id == R.id.nav_gallery) {

		} else if (id == R.id.nav_slideshow) {

		} else if (id == R.id.nav_manage) {

		} else if (id == R.id.nav_share) {

		} else if (id == R.id.nav_send) {

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}


}
