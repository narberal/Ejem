package POJO;
import java.io.Serializable;

//Classe responsável pelo transporte dos dados entre a
//interface(tela) e Banco de Dados
public class Eventos implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String nombre;
    private String lugar;
    private String fecha;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String value) {
        this.nombre = value;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String value) {
        this.lugar = value;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String value) {
        this.fecha = value;
    }
    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return nombre;
    }
}