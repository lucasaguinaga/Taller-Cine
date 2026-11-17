import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


    public Ventana() {
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().Ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
