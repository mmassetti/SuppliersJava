package ventanas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import sistema.Entrega;
import sistema.Producto;
import sistema.Proveedor;
import sistema.Sistema;

public class VentanaNuevaEntrega extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaPrincipal principal;
	private JButton btnConfirmarEntrega;
	private JTextField cantidad;
	private JTextField precioUnitario;
	private JLabel lblProducto;
	private JLabel lblCantidad;
	private JLabel lblFecha;
	private JLabel lblPrecioUnitario;
	private JLabel lblEntrega;
	private JButton btnCancelar;
	private JButton btnOk;
	private Entrega entrega;
	private Sistema miSistema;
	private ArrayList<String> listaProveedores = new ArrayList<String>();
	private ArrayList<Producto> listaProductos;
	private ArrayList<String> listaProductosCargados = new ArrayList<String>();
	private JComboBox<String> comboBoxProveedor;
	private JComboBox<String> comboBoxProducto;
	private String nombreProveedor, nombreProducto;
	private JList<String> infoEntrega;
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private float montoEntrega;
	private JScrollPane scrollPanel;
	private JDateChooser dateChooser;

	public VentanaNuevaEntrega(JFrame gui) {
		principal = (VentanaPrincipal) gui;
		miSistema = principal.getSistema();
		listaProductos = new ArrayList<Producto>();
		montoEntrega = 0;
		iniciarGui();
		setListeners();
	}

	private void iniciarGui() {

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

		// Proveedor - JLabel y ComboBox
		JLabel lblProveedor = new JLabel("Proveedor");
		lblProveedor.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblProveedor.setBounds(21, 110, 79, 14);
		contentPane.add(lblProveedor);

		comboBoxProveedor = new JComboBox<String>();
		comboBoxProveedor.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		comboBoxProveedor.setBounds(110, 107, 138, 20);
		contentPane.add(comboBoxProveedor);

		// Producto - JLabel y ComboBox
		lblProducto = new JLabel("Producto");
		lblProducto.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblProducto.setBounds(21, 152, 71, 14);
		contentPane.add(lblProducto);

		comboBoxProducto = new JComboBox<String>();
		comboBoxProducto.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		comboBoxProducto.setEnabled(false);
		comboBoxProducto.setBounds(110, 149, 138, 20);
		contentPane.add(comboBoxProducto);

		// Fecha - JLabel y JDateChooser

		lblFecha = new JLabel("Fecha");
		lblFecha.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblFecha.setBounds(21, 63, 46, 14);
		contentPane.add(lblFecha);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(110, 63, 138, 20);
		contentPane.add(dateChooser);

		// Cantidad - JLabel y JTextField
		lblCantidad = new JLabel("Cantidad");
		lblCantidad.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblCantidad.setBounds(21, 197, 71, 14);
		contentPane.add(lblCantidad);

		cantidad = new JTextField();
		cantidad.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		cantidad.setEditable(false);
		cantidad.setBounds(145, 194, 103, 20);
		contentPane.add(cantidad);
		cantidad.setColumns(10);

		// Precio Unitario - JLabel y JTextField
		lblPrecioUnitario = new JLabel("Precio unitario");
		lblPrecioUnitario.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblPrecioUnitario.setBounds(21, 237, 109, 14);
		contentPane.add(lblPrecioUnitario);

		precioUnitario = new JTextField();
		precioUnitario.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		precioUnitario.setEditable(false);
		precioUnitario.setBounds(145, 234, 103, 20);
		contentPane.add(precioUnitario);
		precioUnitario.setColumns(10);

		// Boton Cancelar
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnCancelar.setBounds(432, 285, 159, 33);
		contentPane.add(btnCancelar);

		// Boton Ok (agrega un producto a la entrega del dia elegido)
		btnOk = new JButton("Ok");
		btnOk.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnOk.setBounds(41, 285, 103, 33);
		contentPane.add(btnOk);

		// Boton Confirmar (Agrega la Entrega de la fecha seleccionada a la
		// lista de entregas del sistema)
		btnConfirmarEntrega = new JButton("Confirmar entrega");
		btnConfirmarEntrega.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnConfirmarEntrega.setEnabled(false);
		btnConfirmarEntrega.setBounds(230, 285, 192, 33);
		contentPane.add(btnConfirmarEntrega);

		// Detalle de la entrega- JLabel, ScrollPane y JList
		lblEntrega = new JLabel("Detalle de la entrega");
		lblEntrega.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblEntrega.setBounds(342, 31, 159, 19);
		contentPane.add(lblEntrega);

		scrollPanel = new JScrollPane();
		scrollPanel.setBounds(258, 61, 332, 193);
		contentPane.add(scrollPanel);

		infoEntrega = new JList<String>(model);
		infoEntrega.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		scrollPanel.setViewportView(infoEntrega);
		infoEntrega.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		infoEntrega.setBackground(SystemColor.control);

		DefaultListCellRenderer renderer = (DefaultListCellRenderer) infoEntrega.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private void setListeners() {

		// Listener ComboBox Proveedor

		comboBoxProveedor.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent event) {
				comboBoxProducto.setEnabled(true);
				JComboBox<String> comboBox = (JComboBox<String>) event.getSource();
				Object selected = comboBox.getSelectedItem();
				if (selected != null) {
					nombreProveedor = selected.toString();
					actualizarProductos(nombreProveedor);
					precioUnitario.setText("");
				}

			}
		});

		// Listener ComboBox Producto
		comboBoxProducto.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> comboBox = (JComboBox<String>) event.getSource();
				Object selected = comboBox.getSelectedItem();
				if (selected != null) { // Si hay un producto seleccionado del
										// combo box
					nombreProducto = selected.toString();
					if (listaProductosCargados.contains(nombreProducto)) {
						// El producto ya se cargo en la entrega actual
						JOptionPane.showMessageDialog(contentPane, "El producto " + nombreProducto + " ya se cargo!",
								"Intente nuevamente", JOptionPane.INFORMATION_MESSAGE);
						cantidad.setEditable(false);
					} else { // Obtiene los datos del producto
						cantidad.setEditable(true);
						Producto p = miSistema.getProveedor(nombreProveedor).getProducto(nombreProducto);
						String precioString = Float.toString(p.getPrecioUnitario());
						precioUnitario.setText(precioString);
						precioUnitario.setEditable(false);

					}

				}

			}
		});

		// Listener Boton Cancelar
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxProveedor.setEnabled(true);
				cantidad.setText("");
				precioUnitario.setText("");
				dateChooser.setDate(null);
				model.clear();
				listaProductosCargados = new ArrayList<String>();
				dispose();
				principal.setVisible(true);

			}
		});

		// Listener Boton Ok
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!esEntero(cantidad.getText()) || cantidad.getText().length() == 0) {
					JOptionPane.showMessageDialog(contentPane, "Ingrese una cantidad valida", "Error",
							JOptionPane.ERROR_MESSAGE);
					cantidad.requestFocusInWindow();
				} else {
					// Obtiene textos para el producto
					int cantidadArticulo = Integer.parseInt(cantidad.getText());
					float precioUnitarioArticulo = Float.parseFloat(precioUnitario.getText());

					// Crea el producto
					Producto p = new Producto(nombreProducto, precioUnitarioArticulo);
					p.setCantidadEntrega(cantidadArticulo);

					// Agrega el producto a la lista de productos y a la lista
					// de productos ya cargados
					listaProductos.add(p);
					listaProductosCargados.add(nombreProducto);

					// Suma subtotal (cantidad*precio unitario ) al total de la
					// Entrega
					montoEntrega += cantidadArticulo * precioUnitarioArticulo;

					// Agrega la informacion del producto a la JList
					if (infoEntrega.getModel().getSize() == 0) {
						model.addElement("Proveedor: " + nombreProveedor);
						model.addElement("----------------------------------------------");
					}

					model.addElement("Producto: " + nombreProducto);
					model.addElement("Cantidad: " + cantidad.getText());
					float precioUnit = Float.valueOf(precioUnitario.getText());
					model.addElement("Precio unitario: " + String.format("%.2f", precioUnit));
					float subTotal = cantidadArticulo * precioUnitarioArticulo;
					model.addElement("Subtotal: " + String.format("%.2f", subTotal));
					model.addElement("----------------------------------------------");
					model.addElement("Total parcial: " + String.format("%.2f", montoEntrega));
					model.addElement("----------------------------------------------");

					// Restablece propiedades
					btnConfirmarEntrega.setEnabled(true);
					dateChooser.setEnabled(false);
					comboBoxProveedor.setEnabled(false);
					comboBoxProducto.setSelectedIndex(-1);
					precioUnitario.setText("");
					cantidad.setText("");
					cantidad.setEditable(false);

				}

			}
		});

		// Listener Boton Confirmar
		btnConfirmarEntrega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Crea entrega y la agrega al sistema
				entrega = new Entrega(nombreProveedor, listaProductos, dateChooser.getDate());
				entrega.setTotal(montoEntrega);

				miSistema.agregarEntrega(entrega);

				dispose();
				principal.setVisible(true);
				principal.repaint();

				// Restableccer campos
				cantidad.setText("");
				precioUnitario.setText("");
				comboBoxProveedor.setEnabled(true);
				comboBoxProducto.setEnabled(false);
				comboBoxProducto.setSelectedIndex(-1);
				dateChooser.setDate(null);
				dateChooser.setEnabled(true);
				btnConfirmarEntrega.setEnabled(false);
				montoEntrega = 0;
				model.clear();
				listaProductos = new ArrayList<Producto>();
				listaProductosCargados = new ArrayList<String>();

			}
		});
	}

	private void cargarListaProveedores() {
		listaProveedores = new ArrayList<String>();
		for (Proveedor p : miSistema.getProveedores()) {
			listaProveedores.add(p.getNombre());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void repaint() {

		// Actualiza el combobox de Proveedores
		miSistema = principal.getSistema();
		cargarListaProveedores();
		if (miSistema.getProveedores().size() > 0) {
			comboBoxProveedor.setSelectedIndex(-1);
			comboBoxProveedor.setModel(new DefaultComboBoxModel(listaProveedores.toArray()));
			comboBoxProveedor.setSelectedIndex(-1);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void actualizarProductos(String nombre) {
		miSistema = principal.getSistema();
		ArrayList<String> nombresProductos = new ArrayList<String>();
		for (Producto prod : miSistema.getProveedor(nombre).getMisProductos()) {
			nombresProductos.add(prod.getNombre());
		}

		if (miSistema.getProveedores().size() > 0) {
			comboBoxProducto.setModel(new DefaultComboBoxModel(nombresProductos.toArray()));
			comboBoxProducto.setSelectedIndex(-1);
		}

	}

	public boolean esEntero(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
