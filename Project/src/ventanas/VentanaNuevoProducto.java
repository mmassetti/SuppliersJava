package ventanas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sistema.Producto;
import sistema.Proveedor;

public class VentanaNuevoProducto extends JFrame {

	private static final long serialVersionUID = 1L;
	private VentanaNuevoProveedor principal1; // Esta ventana es llamada desde
												// Ventana Nuevo Proveedor
	private VentanaEditarProveedor principal2; // Esta ventana es llamada desde
												// VentanaEditarProveedor
	private JPanel contentPane;
	private JTextField nombre;
	private JTextField precioUnitario;
	private JButton btnOk;
	private JButton btnCancelar;
	private Proveedor miProveedor;
	private boolean existeProveedor;

	public VentanaNuevoProducto(JFrame gui) {
		iniciarGui();
		setListeners(gui);
	}

	public void setProveedor(Proveedor p) {
		miProveedor = p;
	}

	public void setExisteProveedor(boolean condicion) {
		existeProveedor = condicion;
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

		// Nombre del producto - JLabel y JTextField
		JLabel lblNombre = new JLabel("Nombre del producto");
		lblNombre.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblNombre.setBounds(99, 89, 150, 28);
		contentPane.add(lblNombre);

		nombre = new JTextField();
		nombre.setBounds(269, 88, 188, 28);
		contentPane.add(nombre);
		nombre.setColumns(10);

		// Precio unitario - JLabel y JTextField
		JLabel lblPrecioUnitario = new JLabel("Precio unitario ($)");
		lblPrecioUnitario.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblPrecioUnitario.setBounds(99, 147, 144, 23);
		contentPane.add(lblPrecioUnitario);

		precioUnitario = new JTextField();
		precioUnitario.setBounds(269, 147, 188, 28);
		contentPane.add(precioUnitario);
		precioUnitario.setColumns(10);

		// Boton Ok
		btnOk = new JButton("Ok");
		btnOk.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnOk.setBounds(154, 214, 89, 36);
		contentPane.add(btnOk);

		// Boton Cancelar
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnCancelar.setBounds(269, 214, 116, 36);
		contentPane.add(btnCancelar);

	}

	private void setListeners(JFrame gui) {

		// Listener Boton Ok
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Controlar que el precio unitario sea un numero valido
				try {
					Float.parseFloat(precioUnitario.getText());
					Producto p = new Producto(nombre.getText(), Float.parseFloat(precioUnitario.getText()));
					miProveedor.agregarProducto(p);
					nombre.setText("");
					precioUnitario.setText("");
					dispose();
					if (existeProveedor) {
						principal2 = (VentanaEditarProveedor) gui;
						principal2.setMiProveedor(miProveedor);
						principal2.setVisible(true);
					} else {
						principal1 = (VentanaNuevoProveedor) gui;
						principal1.setProveedor(miProveedor);
						principal1.setVisible(true);
					}
				} catch (NumberFormatException exception) {
					precioUnitario.requestFocusInWindow();
					JOptionPane.showMessageDialog(contentPane, "Precio unitario invalido", "Error",
							JOptionPane.ERROR_MESSAGE);

				}

			}
		});

		// Listener Boton Cancelar
		btnCancelar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
				if (existeProveedor) {
					principal2 = (VentanaEditarProveedor) gui;
					principal2.setVisible(true);
				} else {
					principal1 = (VentanaNuevoProveedor) gui;
					principal1.setVisible(true);
				}

			}
		});
	}

}
