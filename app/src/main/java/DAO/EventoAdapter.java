package DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.eventosfia.gerardo.ejem.R;
import java.util.List;
import POJO.Eventos;

/**
 * Created by gerardo on 13/05/2016.
 */
public class EventoAdapter extends BaseAdapter  {
    private Context context;

    private List<Eventos> lstEventos;
    private LayoutInflater inflater;

    public EventoAdapter(Context context, List<Eventos> listEventos) {
        this.context = context;
        this.lstEventos = listEventos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //ListView actualización de acuerdo con el último contacto
    @Override
    public void notifyDataSetChanged() {
        try{
            super.notifyDataSetChanged();
        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public int getCount() {
        return lstEventos.size();
    }

    //Quitar elemento de la lista
    public void remove(final Eventos item) {
        this.lstEventos.remove(item);
    }

    //Añadir elemento de la lista
    public void add(final Eventos item) {
        this.lstEventos.add(item);
    }

    public Object getItem(int position) {
        return lstEventos.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        try
        {

            Eventos contato = lstEventos.get(position);

            //El ViewHolder salvará las instancias estatales de objetos de fila
            ViewHolder holder;

            // Cuando el objeto convertView no es nulo no inflamos
            // los objetos XML, será nula cuando la primera vez que se carga
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.contato_row,null);

                // Y Crear Viewholder guardarlos ejecuta objetos de ellas
                holder = new ViewHolder();
                holder.tvNombre = (TextView) convertView.findViewById(R.id.txtNombre);
                holder.tvLugar = (TextView) convertView.findViewById(R.id.txtLugar);
                holder.tvFecha = (TextView) convertView.findViewById(R.id.txtFecha);

                convertView.setTag(holder);
            } else {
                // Obtener el ViewHolder para tener un acceso rápido a los objetos XML
                // Siempre va a pasar por aquí cuando, por ejemplo, está hecho un desplazamiento en la pantalla
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvNombre.setText(contato.getNombre());
            holder.tvLugar.setText(contato.getLugar());
            holder.tvFecha.setText(contato.getFecha());

            return convertView;

        }catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
        return convertView;
    }


    public void toast (String msg)
    {
        Toast.makeText (context, msg, Toast.LENGTH_SHORT).show ();
    }

    private void trace (String msg)
    {
        toast (msg);
    }

    //Establecer esta clase estática para almacenar la referencia a los objetos por debajo
    static class ViewHolder {
        public TextView tvNombre;
        public TextView tvLugar;
        public TextView tvFecha;
    }
}
