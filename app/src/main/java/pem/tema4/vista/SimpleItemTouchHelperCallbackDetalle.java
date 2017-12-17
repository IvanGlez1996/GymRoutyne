package pem.tema4.vista;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import pem.tema4.AppMediador;

/**
 * Created by emartel on 09/11/2017.
 */

public class SimpleItemTouchHelperCallbackDetalle extends ItemTouchHelper.Callback {

    private final IItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallbackDetalle(IItemTouchHelperAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
       int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
       int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
       return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return  false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //TODO presentar alerta de la vista principal
       mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
       int posicion = viewHolder.getLayoutPosition();
       AppMediador.getInstance().getVistaPrincipal().presentarAlertaDetalle(posicion);
    }
}
