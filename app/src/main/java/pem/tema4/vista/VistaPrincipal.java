package pem.tema4.vista;

import pem.tema4.AppMediador;
import pem.tema4.modelo.Ejercicio_rutina;
import pem.tema4.presentador.IPresentadorPrincipal;

import android.content.DialogInterface;
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
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class VistaPrincipal extends AppCompatActivity implements IVistaPrincipal,
		FragmentoMaestro.EscuchaFragmento, FragmentoDetalle.EscuchaFragmento, NavigationView.OnNavigationItemSelectedListener {
	
	private AppMediador appMediador;
	private IPresentadorPrincipal presentadorPrincipal;
	private FragmentoMaestro fragmentoMaestro;
	private FragmentoDetalle fragmentoDetalle;
	private FragmentoDetalleEjercicio fragmentoDetalleEjercicio;
    // TODO Declarar un objeto llamado fab, que corresponda con un bot�n flotante
	private FloatingActionButton fab;
	private FloatingActionButton fab_detalle;
	private int posicionRutina;
	private ArrayList<Ejercicio_rutina> ejercicios_rutina = new ArrayList<>();


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
		// Se comprueba si la actividad est� usando una versi�n de layout con un contenedor de fragmentos
		// de tipo FrameLayout (si es as�, es un smartphone y no permite m�s de un fragmento en pantalla),
		// por tanto, s�lo se a�ade el primero
		if (findViewById(R.id.contenedorDeFragmentos) != null) {
			// se crea el fragmento maestro y se a�ade al contenedor de fragmentos
			fragmentoMaestro = new FragmentoMaestro();
			getSupportFragmentManager().beginTransaction()
				.add(R.id.contenedorDeFragmentos, fragmentoMaestro)
				.commit();
		} else {
			// Si el layout no es de panel �nico (es una tableta) se permiten m�s de un fragmento
			// por tanto, se crean los dos fragmentos y se a�aden a sus layouts seg�n el xml sw600dp
			fragmentoMaestro = new FragmentoMaestro();
			getSupportFragmentManager().beginTransaction()
				.add(R.id.contenedor_lista, fragmentoMaestro)
				.commit();
			
			fragmentoDetalle = new FragmentoDetalle();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedor_detalle, fragmentoDetalle)
                    .commit();
		}
		// TODO Crear un bot�n flotante y, cuando se seleccione, solicitar al presentador principal que trate 
		// la opci�n de agregar una nueva receta
		fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				//TODO Solicitar al presentador para que agregue una nueva receta
				presentarAlertaAgregar();
			}
		});

		fab_detalle = (FloatingActionButton) findViewById(R.id.fab_detalle);
		fab_detalle.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				//TODO Solicitar al presentador para que agregue una nueva receta
				presentarAlertaDetalle();
			}
		});

		fab_detalle.setVisibility(View.GONE);

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
		this.posicionRutina = posicion;
        // Si no hay fragmento detalle, se crea la vista detalle (esto ocurre si es panel �nico)
        if (fragmentoDetalle == null)
            fragmentoDetalle = new FragmentoDetalle();

        if (findViewById(R.id.contenedorDeFragmentos) != null) {
            // si es de panel �nico, se reemplaza, en el contenedor de fragmentos
            // el fragmento que est� visible por el de la vista detalle
            FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
            transaccion.replace(R.id.contenedorDeFragmentos, fragmentoDetalle);
            transaccion.addToBackStack(null);
            transaccion.commit();
            // TODO Quitar la visibilidad al bot�n flotante (para que no aparezca en el detalle)
			fab.setVisibility(View.GONE);
            // realiza la transacci�n
            getSupportFragmentManager().executePendingTransactions();
        }else if(fragmentoDetalleEjercicio != null){
			FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
			transaccion.replace(R.id.contenedor_detalle, fragmentoDetalle);
			transaccion.addToBackStack(null);
			transaccion.commit();
		}
        // TODO Solicitar al presentador que trate el item seleccionado.
		fab_detalle.setVisibility(View.VISIBLE);
		presentadorPrincipal.obtenerEjercicios(posicion);
    }

	@Override
	public void alSeleccionarItemDetalle(int posicion) {
		// Si no hay fragmento detalle, se crea la vista detalle (esto ocurre si es panel �nico)
		if (fragmentoDetalleEjercicio == null)
			fragmentoDetalleEjercicio = new FragmentoDetalleEjercicio();
		// TODO Solicitar al presentador que trate el item seleccionado.
		//presentadorPrincipal.obtenerDetallesEjercicio(posicionRutina);
		if (findViewById(R.id.contenedorDeFragmentos) != null) {
			// si es de panel �nico, se reemplaza, en el contenedor de fragmentos
			// el fragmento que est� visible por el de la vista detalle
			FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
			transaccion.replace(R.id.contenedorDeFragmentos, fragmentoDetalleEjercicio);
			transaccion.addToBackStack(null);
			transaccion.commit();

		}else{
			FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
			transaccion.replace(R.id.contenedor_detalle, fragmentoDetalleEjercicio);
			transaccion.addToBackStack(null);
			transaccion.commit();

		}
		fab_detalle.setVisibility(View.GONE);
		// realiza la transacci�n
		getSupportFragmentManager().executePendingTransactions();
		String[] detalles = presentadorPrincipal.obtenerDetallesEjercicio(ejercicios_rutina.get(posicion).getId());

		fragmentoDetalleEjercicio.actualizarNombreEjercicio(detalles[0]);
		fragmentoDetalleEjercicio.actualizarDescripcionEjercicio(detalles[1]);
		fragmentoDetalleEjercicio.actualizarImagenEjercicio(detalles[2]);
	}




    // TODO Redefinir el m�todo onBackPressed para que si se tiene un dispositivo de panel �nico, y el bot�n
    // flotante no est� visible (est� el fragmento detalle en pantalla), reemplace el fragmento detalle por el
    // fragmento maestro. En cualquier otro caso, la actividad debe finalizar (porque se quiere salir de ella)
	@Override
	public void onBackPressed(){
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (findViewById(R.id.contenedorDeFragmentos) != null){
			//Es panel �nico
			if(fab.getVisibility() != View.VISIBLE && fab_detalle.getVisibility() != View.VISIBLE){
				FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
				transaccion.replace(R.id.contenedorDeFragmentos, fragmentoDetalle);
				transaccion.addToBackStack(null);
				transaccion.commit();
				fab_detalle.setVisibility(View.VISIBLE);
				presentadorPrincipal.obtenerEjercicios(posicionRutina);
			}else if(fab.getVisibility() != View.VISIBLE){
				//Est� en la vista del detalle
				FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
				transaccion.replace(R.id.contenedorDeFragmentos, fragmentoMaestro);
				transaccion.addToBackStack(null);
				transaccion.commit();
				fab.setVisibility(View.VISIBLE);
				fab_detalle.setVisibility(View.GONE);
				presentadorPrincipal.obtenerRutinas();
			}
			else{
				//No est� en la vista del detalle
				finish();
			}
		} else {
			//No es panel �nico
			if(fab_detalle.getVisibility() != View.VISIBLE){
				FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
				transaccion.replace(R.id.contenedor_detalle, fragmentoDetalle);
				transaccion.addToBackStack(null);
				transaccion.commit();
				fab_detalle.setVisibility(View.VISIBLE);
				presentadorPrincipal.obtenerEjercicios(posicionRutina);
			}else {
				finish();
			}
		}

		/*if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}*/
	}

    // TODO A�adir el m�todo actualizarMaestro(Object[] datos) que actualiza la lista maestro con los datos
	// recibidos por par�metros. En cada entrada del vector, est� el nombre de una receta.	
	@Override
	public void actualizarMaestro(Object[] datos) {
		// TODO Dentro del m�todo actualizarMaestro(Object[] datos), crear la lista maestro con los nombres 
		// de las recetas que entran por par�metros.
		fragmentoMaestro.crearLista((String[]) datos);
		// TODO Dentro del m�todo actualizarMaestro(Object[] datos), si es una pantalla multi-panel, presentar 
		// el detalle de la primera receta.
		if (findViewById(R.id.contenedorDeFragmentos) == null){
			presentadorPrincipal.obtenerEjercicios(0);
			fab_detalle.setVisibility(View.VISIBLE);
		}
	}


	// TODO A�adir el m�todo actualizarDetalle(Object[] datos) que actualiza los valores del detalle, 
	// teniendo en cuenta que en la posici�n 0 del vector est� el nombre de la receta y en qu� se usa 
	// para realizarla, en la posici�n 1 del vector est� la imagen como un Bitmap y en en la posici�n 3 
	// del vector est� la descripci�n de la receta.
	@Override
	public void actualizarDetalle(ArrayList<Ejercicio_rutina> datos) {
		ejercicios_rutina = datos;
		fragmentoDetalle.crearLista(datos);
	}

	@Override
	public void presentarAlerta(final int posicion) {
		AlertDialog.Builder alerta = new AlertDialog.Builder(this);
		alerta.setTitle("Aviso");
		alerta.setMessage("�Seguro que desea eliminar esta rutina de forma permanente?");
		alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				presentadorPrincipal.eliminarRutina(posicion);
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

	@Override
	public void presentarAlertaDetalle(final int posicion) {
		AlertDialog.Builder alerta = new AlertDialog.Builder(this);
		alerta.setTitle("Aviso");
		alerta.setMessage("�Seguro que desea eliminar este ejercicio de la rutina de forma permanente?");
		alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				presentadorPrincipal.eliminarEjercicioRutina(ejercicios_rutina.get(posicion).getId());
				presentadorPrincipal.obtenerEjercicios(posicionRutina);
			}
		});
		alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alerta.show();
	}

	public void presentarAlertaAgregar() {
		AlertDialog.Builder alerta = new AlertDialog.Builder(this);
		alerta.setTitle("A�ade una nueva rutina");
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		input.setHint("Nombre");
		alerta.setView(input);
		alerta.setPositiveButton("A�adir", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String inputText = input.getText().toString();
				presentadorPrincipal.agregarRutina(inputText);
			}
		});
		alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				presentadorPrincipal.obtenerRutinas();
				presentadorPrincipal.obtenerEjercicios(posicionRutina);
			}
		});
		alerta.show();
	}

	public void presentarAlertaDetalle(){
		final Integer[] idSelected = new Integer[1];
		final ArrayList<Object[]> ejercicios = presentadorPrincipal.getEjercicios();
		CharSequence[] items = new CharSequence[ejercicios.size()];
		for (int i = 0; i<ejercicios.size(); i++){
			items[i] = (String)ejercicios.get(i)[1];
		}
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Elige un ejercicio")
				.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						idSelected[0] = which;
					}
				})
				.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK, so save the mSelectedItems results somewhere
						// or return them to the component that opened the dialog
						int idEjercicio = (Integer)ejercicios.get(idSelected[0])[0];
						presentarAlertaDetalle2(idEjercicio);

					}
				})
				.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		AlertDialog alerta = builder.create();
		alerta.show();

		/*AlertDialog.Builder alerta = new AlertDialog.Builder(this);
		CharSequence[] items={"Item 1","Item 2","Item 3"};
		alerta.setTitle("Aviso");
		alerta.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		final EditText inputSets = new EditText(this);
		inputSets.setInputType(InputType.TYPE_CLASS_NUMBER);
		final EditText inputReps = new EditText(this);
		inputReps.setInputType(InputType.TYPE_CLASS_NUMBER);
		alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String inputSetsText = inputSets.getText().toString();
				String inputRepsText = inputReps.getText().toString();
				//presentadorPrincipal.agregarEjercicioRutina(idEjercicio, Integer.parseInt(inputSetsText), Integer.parseInt(inputRepsText));
			}
		});
		alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		AlertDialog dialog = alerta.create();
		dialog.show();*/
	}

	public void presentarAlertaDetalle2(final int idEjercicio){
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setTitle("A�ade las series y repeticiones");
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		final EditText setsInput = new EditText(this);
		setsInput.setInputType(InputType.TYPE_CLASS_NUMBER);
		setsInput.setHint("Series");
		layout.addView(setsInput);

		final EditText repsInput = new EditText(this);
		repsInput.setInputType(InputType.TYPE_CLASS_NUMBER);
		repsInput.setHint("Repeticiones");
		layout.addView(repsInput);

		dialogo.setView(layout);
		dialogo.setPositiveButton("A�adir", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK, so save the mSelectedItems results somewhere
				// or return them to the component that opened the dialog
				String inputSetsText = setsInput.getText().toString();
				String inputRepsText = repsInput.getText().toString();
				int idRutina = presentadorPrincipal.getIdRutina(posicionRutina);
				presentadorPrincipal.agregarEjercicioRutina(idRutina,idEjercicio, Integer.parseInt(inputSetsText), Integer.parseInt(inputRepsText));
				presentadorPrincipal.obtenerEjercicios(posicionRutina);
			}
		})
				.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		AlertDialog alerta2 = dialogo.create();
		alerta2.show();
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
