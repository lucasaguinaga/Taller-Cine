import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.ArrayList;

public class Ventana {
    private JTabbedPane tabbedPane1;
    private JPanel Ventana;
    private JTextField txtCodigo;
    private JPanel lblTitulo;
    private JTextField txtTitulo;
    private JLabel lblDirector;
    private JLabel lblCodigo;
    private JTextField txtDirector;
    private JComboBox cmbGenero;
    private JLabel lblGenero;
    private JLabel lblAnio;
    private JSpinner spnAnio;
    private JButton btnRegistrar;
    private JLabel lbltitulo;
    private JTextArea txtTotal;
    private JLabel lblTotal;
    private JButton btnCD;
    private JLabel lblBusquedaCodigo;
    private JTextField txtBCoincidencias;
    private JButton btnBC;
    private JLabel lblBT;
    private JList list1;
    private JPanel jltResultados;
    private JLabel lblVerCodigo;
    private JTextPane textPane1;
    private JTextField txtCTitulo;
    private JLabel lblCambioTitulo;
    private JTextField txtCDirector;
    private JLabel lblCambioDirector;
    private JComboBox cbxCGenero;
    private JLabel lblCambioGenero;
    private JSpinner spnCAnio;
    private JLabel lblCambioAnio;
    private JTextPane txtMegusta;
    private JButton btnGuardarCambios;
    private JButton btnAgregar1MeGusta;
    private JLabel lblMegusta;
    private JList lstRanking;
    private JButton btnActualizar;
    private JTextField txtBCodigo;


    private CatalogoPeliculas catalogo = new CatalogoPeliculas();

    private DefaultListModel<Pelicula> modeloResultados = new DefaultListModel<>();
    private DefaultListModel<Pelicula> modeloRanking = new DefaultListModel<>();

    private Pelicula seleccionada = null;

    private String ultimoTexto = null;

    public Ventana() {
        configurarComponentes();

        btnRegistrar.addActionListener(e -> registrarPelicula());
        btnCD.addActionListener(e -> buscarPorCodigo());
        btnBC.addActionListener(e -> buscarPorTexto());
        btnGuardarCambios.addActionListener(e -> guardarCambios());
        btnAgregar1MeGusta.addActionListener(e -> darMeGusta());
        btnActualizar.addActionListener(e -> actualizarRanking());

        tabbedPane1.addChangeListener(e -> {
            if (tabbedPane1.getSelectedIndex() == 2) {
                actualizarRanking();
            }
        });

        list1.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Pelicula p = (Pelicula) list1.getSelectedValue();
                if (p != null) {
                    seleccionada = p;
                    mostrarEnFormulario(p);
                }
            }
        });
    }
    private void configurarComponentes() {
        int anioActual = Year.now().getValue();

        cmbGenero.removeAllItems();
        cbxCGenero.removeAllItems();
        for (String genero : CatalogoPeliculas.GENEROS) {
            cmbGenero.addItem(genero);
            cbxCGenero.addItem(genero);
        }
        cbxCGenero.setSelectedIndex(-1); // sin selección al inicio

        spnAnio.setModel(new SpinnerNumberModel(anioActual, 1, anioActual, 1));
        spnAnio.setEditor(new JSpinner.NumberEditor(spnAnio, "#"));
        spnCAnio.setModel(new SpinnerNumberModel(anioActual, 1, anioActual, 1));
        spnCAnio.setEditor(new JSpinner.NumberEditor(spnCAnio, "#"));

        txtTotal.setEditable(false);
        textPane1.setEditable(false);
        txtMegusta.setEditable(false);

        list1.setModel(modeloResultados);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstRanking.setModel(modeloRanking);

        actualizarTotal();
        actualizarRanking();
    }

    private void registrarPelicula() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String titulo = txtTitulo.getText().trim();
            String director = txtDirector.getText().trim();
            String genero = (String) cmbGenero.getSelectedItem();
            int anio = (int) spnAnio.getValue();

            Pelicula nueva = new Pelicula(codigo, titulo, director, genero, anio, 0);
            catalogo.registrar(nueva);

            actualizarTotal();
            actualizarRanking();
            limpiarFormularioRegistro();
            mostrarInfo("Película registrada correctamente.");
        } catch (NumberFormatException ex) {
            mostrarError("El código es obligatorio y debe ser un número entero.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }


    private void buscarPorCodigo() {
        limpiarSeleccion(); // evita editar o dar me gusta a otra película
        try {
            int codigo = Integer.parseInt(txtBCodigo.getText().trim());
            Pelicula p = catalogo.obtenerPorCodigo(codigo);
            ultimoTexto = null;

            if (p == null) {
                mostrarAviso("No se encontró ninguna película con el código " + codigo + ".");
            } else {
                modeloResultados.clear();
                modeloResultados.addElement(p);
                list1.setSelectedIndex(0); // el listener carga el formulario
            }
        } catch (NumberFormatException ex) {
            mostrarError("El código debe ser un número entero.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void buscarPorTexto() {
        limpiarSeleccion();
        try {
            ArrayList<Pelicula> resultados =
                    catalogo.buscarCoincidencias(txtBCoincidencias.getText());
            ultimoTexto = txtBCoincidencias.getText();
            cargarResultados(resultados);

            if (resultados.isEmpty()) {
                mostrarAviso("No se encontraron coincidencias.");
            } else {
                mostrarInfo("Coincidencias encontradas: " + resultados.size());
            }
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void guardarCambios() {
        if (seleccionada == null) {
            mostrarAviso("Primero busque y seleccione una película.");
            return;
        }
        try {
            catalogo.editarPelicula(
                    seleccionada.getCodigo(),
                    txtCTitulo.getText(),
                    txtCDirector.getText(),
                    (String) cbxCGenero.getSelectedItem(),
                    (int) spnCAnio.getValue());

            refrescarResultados();
            if (seleccionada != null) {
                mostrarEnFormulario(seleccionada);
            }
            actualizarRanking();
            mostrarInfo("Cambios guardados.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }


    private void darMeGusta() {
        if (seleccionada == null) {
            mostrarAviso("Primero busque y seleccione una película.");
            return;
        }
        try {
            catalogo.darMeGusta(seleccionada.getCodigo());

            refrescarResultados();
            if (seleccionada != null) {
                mostrarEnFormulario(seleccionada);
            }
            actualizarRanking();
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }
    private void actualizarRanking() {
        modeloRanking.clear();
        for (Pelicula p : catalogo.obtenerRanking()) {
            modeloRanking.addElement(p);
        }
    }

    private void actualizarTotal() {
        txtTotal.setText(String.valueOf(catalogo.getTotal()));
    }

    private void cargarResultados(ArrayList<Pelicula> lista) {
        modeloResultados.clear();
        for (Pelicula p : lista) {
            modeloResultados.addElement(p);
        }
    }

    private void mostrarEnFormulario(Pelicula p) {
        textPane1.setText(String.valueOf(p.getCodigo()));
        txtCTitulo.setText(p.getTitulo());
        txtCDirector.setText(p.getDirector());
        cbxCGenero.setSelectedItem(p.getGenero());
        spnCAnio.setValue(p.getAnio());
        txtMegusta.setText(String.valueOf(p.getMeGusta()));
    }

    private void limpiarSeleccion() {
        seleccionada = null;
        list1.clearSelection();
        textPane1.setText("");
        txtCTitulo.setText("");
        txtCDirector.setText("");
        cbxCGenero.setSelectedIndex(-1);
        spnCAnio.setValue(Year.now().getValue());
        txtMegusta.setText("");
    }

    private void limpiarFormularioRegistro() {
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtDirector.setText("");
        cmbGenero.setSelectedIndex(0);
        spnAnio.setValue(Year.now().getValue());
    }

    private void refrescarResultados() {
        if (ultimoTexto != null) {
            ArrayList<Pelicula> nuevos = catalogo.buscarCoincidencias(ultimoTexto);
            cargarResultados(nuevos);
            if (seleccionada != null && nuevos.contains(seleccionada)) {
                list1.setSelectedValue(seleccionada, true);
            } else {
                limpiarSeleccion();
            }
        } else {
            list1.repaint(); // el texto de la lista cambió, solo hay que redibujar
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(Ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(Ventana, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(Ventana, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Taller CineLikes");
        frame.setContentPane(new Ventana().Ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

