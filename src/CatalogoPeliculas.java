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

    public int buscarIndicePorCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("El código debe ser un número positivo.");
        }
        int inicio = 0;
        int fin = peliculas.size() - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            int codigoMedio = peliculas.get(medio).getCodigo();

            if (codigoMedio == codigo) {
                return medio;
            } else if (codigoMedio < codigo) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }


    public Pelicula obtenerPorCodigo(int codigo) {
        int indice = buscarIndicePorCodigo(codigo);
        if (indice == -1) {
            return null;
        }
        return peliculas.get(indice);
    }


    public ArrayList<Pelicula> buscarCoincidencias(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Escriba un texto para buscar.");
        }
        String consulta = texto.trim().toLowerCase();
        ArrayList<Pelicula> resultados = new ArrayList<>();

        for (int i = 0; i < peliculas.size(); i++) {
            Pelicula p = peliculas.get(i);
            boolean enTitulo = p.getTitulo().toLowerCase().contains(consulta);
            boolean enDirector = p.getDirector().toLowerCase().contains(consulta);

            if (enTitulo || enDirector) {
                resultados.add(p);
            }
        }
        return resultados;
    }
}

