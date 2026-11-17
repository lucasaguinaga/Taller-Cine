import java.time.Year;
import java.util.ArrayList;


public class CatalogoPeliculas {

    // Géneros permitidos (los mismos del JComboBox)
    public static final String[] GENEROS = {
            "Ciencia ficción", "Drama", "Aventura", "Comedia",
            "Animación", "Suspenso", "Documental"
    };

    // Lista principal: SIEMPRE ordenada por código ascendente
    private ArrayList<Pelicula> peliculas;


    // Constructor: se carga una sola vez y se ordena por código

    public CatalogoPeliculas() {
        peliculas = DatosIniciales.cargar();
        ordenarPorCodigoBurbuja();
    }


    // Consultas básicas

    public int getTotal() {
        return peliculas.size();
    }


    public ArrayList<Pelicula> getPeliculas() {
        return new ArrayList<>(peliculas);
    }


    // Ordenamiento burbuja por código (ascendente)

    // Ordenamiento burbuja por código (ascendente)

    public void ordenarPorCodigoBurbuja() {
        Pelicula aux;
        for (int i = 0; i < peliculas.size() - 1; i++) {
            for (int j = 0; j < peliculas.size() - 1 - i; j++) {
                if (peliculas.get(j).getCodigo() > peliculas.get(j + 1).getCodigo()) {
                    aux = peliculas.get(j);
                    peliculas.set(j, peliculas.get(j + 1));
                    peliculas.set(j + 1, aux);
                }
            }
        }
    }
}