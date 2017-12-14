package ventanas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import sistema.Proveedor;

public class VentanaNuevoProveedor extends JFrame {

	private static final long serialVersionUID = 1L;
	private VentanaProveedores principal;
	private VentanaNuevoProducto ventanaNuevoProducto;
	private JPanel contentPane;
	private JTextField nombre;
	private JTextField cuit;
	private Proveedor p;
	private boolean existeProveedor;
	private JLabel lblNombre;
	private JLabel lblCuit;
	private JLabel lblFechaInicioContrato;
	private JButton btnGuardar;
	private JButton btnAgregarProducto;
	private JButton btnVolver;
	private JDateChooser dateChooser;

	public VentanaNuevoProveedor(JFrame gui) {
		principal = (VentanaProveedores) gui;
		ventanaNuevoProducto = new VentanaNuevoProducto((JFrame) this);
		iniciarGui();
		setListeners();

	}

	public void setProveedor(Proveedor p) {
		this.p = p;
		existeProveedor = true;
	}

	public void iniciarGui() {

		// Propiedades de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 368);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Nombre del proveedor - Jlabel y JTextField
		lblNombre = new JLabel("Nombre del proveedor");
		lblNombre.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblNombre.setBounds(110, 67, 170, 18);
		contentPane.add(lblNombre);

		nombre = new JTextField();
		nombre.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		nombre.setBounds(338, 67, 142, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);

		// CUIT- JLabel y JTextField
		lblCuit = new JLabel("CUIT");
		lblCuit.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblCuit.setBounds(110, 123, 46, 20);
		contentPane.add(lblCuit);

		cuit = new JTextField();
		cuit.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		cuit.setBounds(338, 124, 142, 20);
		contentPane.add(cuit);
		cuit.setColumns(10);

		// Fecha inicio contrato - JLabel y JTextField
		lblFechaInicioContrato = new JLabel("Fecha inicio contrato");
		lblFechaInicioContrato.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblFechaInicioContrato.setBounds(110, 180, 158, 18);
		contentPane.add(lblFechaInicioContrato);

		// Fecha - JCalendar
		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		dateChooser.setBounds(340, 180, 142, 20);
		contentPane.add(dateChooser);

		// Boton Guardar
		btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnGuardar.setEnabled(false);
		btnGuardar.setBounds(71, 278, 100, 23);
		contentPane.add(btnGuardar);

		// Boton Agregar Producto
		btnAgregarProducto = new JButton("Agregar producto");
		btnAgregarProducto.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnAgregarProducto.setBounds(209, 278, 170, 23);
		contentPane.add(btnAgregarProducto);

		// Boton Volver
		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnVolver.setBounds(389, 278, 120, 23);
		contentPane.add(btnVolver);

	}

	public void setListeners() {

		// Boton Guardar
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGuardar.setEnabled(false);
				nombre.setEditable(true);
				cuit.setEditable(true);
				nombre.setText("");
				cuit.setText("");
				existeProveedor = false;
				dateChooser.setDate(null);

				principal.getSistema().agregarProveedor(p);

				dispose();
				principal.setVisible(true);
				principal.repaint();

			}
		});

		// Boton Agregar Producto
		btnAgregarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGuardar.setEnabled(true);
				nombre.setEditable(false);
				cuit.setEditable(false);
				dateChooser.setEnabled(false);

				if (!existeProveedor) // El proveedor aun no existe
					p = new Proveedor(nombre.getText(), cuit.getText(), dateChooser.getDate());
				else {
					// El proveedor p ya fue seteado por la VentanaNuevoProducto
				}
				ventanaNuevoProducto.setProveedor(p);
				dispose();
				ventanaNuevoProducto.setVisible(true);

			}
		});

		// Boton Volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				principal.setVisible(true);
				nombre.setText("");
				nombre.setEditable(true);
				cuit.setEditable(true);
				cuit.setText("");
				dateChooser.setEnabled(true);

			}
		});
	}
}
