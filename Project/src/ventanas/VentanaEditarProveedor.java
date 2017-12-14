package ventanas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import sistema.Producto;
import sistema.Proveedor;
import sistema.Sistema;

public class VentanaEditarProveedor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaProveedores principal;
	private VentanaNuevoProducto ventanaNuevoProducto;
	private JButton btnEliminarProducto;
	private JButton btnGuardar;
	private JButton btnNuevoProducto;
	private JTextField textNombreProveedor;
	private JTextField textCuit;
	private JLabel lblNombreProveedor;
	private JLabel lblFechaInicioContrato;
	private JLabel lblProductosDisponibles;

	private Sistema miSistema;
	private Proveedor miProveedor;
	private JList<String> jListProductos;
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private int indiceSeleccionado;
	private String productoSeleccionado;
	private JScrollPane scrollPanel;
	private JDateChooser dateChooser;

	public VentanaEditarProveedor(JFrame gui) {
		principal = (VentanaProveedores) gui;
		miSistema = principal.getSistema();
		ventanaNuevoProducto = new VentanaNuevoProducto(this);
		iniciarGui();
		setearListeners();
	}

	public Proveedor getMiProveedor() {
		return miProveedor;
	}

	public void setMiProveedor(Proveedor miProveedor) {
		model.clear();
		this.miProveedor = miProveedor;
		
		//Setea nombre y cuit
		textNombreProveedor.setText(miProveedor.getNombre());
		textCuit.setText(miProveedor.getCuit());

		// Fecha
		dateChooser.setDate(miProveedor.getFechaInicioContrato());

		//Agrega productos al JList
		for (Producto p : miProveedor.getMisProductos()) {

			String precioUnitario = String.format("%.2f", p.getPrecioUnitario());
			model.addElement(p.getNombre() + " - Precio unitario: " + precioUnitario);
		}
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

		// Nombre proveedor - JLabel y JTextField
		lblNombreProveedor = new JLabel("Nombre del proveedor");
		lblNombreProveedor.setBounds(66, 13, 170, 20);
		lblNombreProveedor.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		contentPane.add(lblNombreProveedor);

		textNombreProveedor = new JTextField();
		textNombreProveedor.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		textNombreProveedor.setBounds(294, 10, 194, 20);
		textNombreProveedor.setEditable(false);
		textNombreProveedor.setColumns(10);
		contentPane.add(textNombreProveedor);

		// CUIT - JLabel y JTextField
		JLabel lblCuit = new JLabel("CUIT");
		lblCuit.setBounds(66, 44, 46, 14);
		lblCuit.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		contentPane.add(lblCuit);

		textCuit = new JTextField();
		textCuit.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		textCuit.setBounds(294, 41, 194, 20);
		textCuit.setColumns(10);
		contentPane.add(textCuit);

		// Fecha inicio contrato - JLabel y JTextField
		dateChooser = new JDateChooser();
		dateChooser.setBounds(294, 72, 194, 20);
		contentPane.add(dateChooser);

		lblFechaInicioContrato = new JLabel("Fecha inicio contrato");
		lblFechaInicioContrato.setBounds(66, 73, 158, 14);
		lblFechaInicioContrato.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		contentPane.add(lblFechaInicioContrato);

		// Productos disponibles - JLabel , JScrollPane y JList
		lblProductosDisponibles = new JLabel("Productos disponibles");
		lblProductosDisponibles.setBounds(179, 112, 170, 14);
		lblProductosDisponibles.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		contentPane.add(lblProductosDisponibles);

		scrollPanel = new JScrollPane();
		scrollPanel.setBounds(34, 137, 469, 147);
		contentPane.add(scrollPanel);

		jListProductos = new JList<String>(model);
		jListProductos.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		scrollPanel.setViewportView(jListProductos);
		jListProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jListProductos.setBackground(SystemColor.control);

		// Boton Guardar
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(34, 295, 115, 23);
		btnGuardar.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		contentPane.add(btnGuardar);

		// Boton Nuevo producto
		btnNuevoProducto = new JButton("Nuevo producto");
		btnNuevoProducto.setBounds(159, 295, 158, 23);
		btnNuevoProducto.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		contentPane.add(btnNuevoProducto);

		btnEliminarProducto = new JButton("Eliminar producto");
		btnEliminarProducto.setBounds(327, 295, 177, 23);
		btnEliminarProducto.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnEliminarProducto.setEnabled(false);
		contentPane.add(btnEliminarProducto);

	}

	private void setearListeners() {

		// Listener jListProductos
		jListProductos.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
				JList<String> listaAux = (JList<String>) evt.getSource();
				if (evt.getClickCount() == 2) {
					// Double-click detected

					Rectangle r = listaAux.getCellBounds(0, listaAux.getLastVisibleIndex());
					// Si selecciona un espacio en blanco, por defecto se elige
					// el ultimo elemento, con este control ese bug se evita
					if (r != null && r.contains(evt.getPoint())) {
						indiceSeleccionado = listaAux.locationToIndex(evt.getPoint());
						productoSeleccionado = model.getElementAt(indiceSeleccionado);

						// Activa boton eliminar producto
						btnEliminarProducto.setEnabled(true);

					}

				}
			}
		});

		// Listener Boton Guardar
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miProveedor.setCuit(textCuit.getText());
				miProveedor.setFechaInicioContrato(dateChooser.getDate());
				miSistema = principal.getSistema();
				miSistema.actualizarProveedores();
				dispose();
				principal.setVisible(true);
			}
		});

		// Listener Boton Nuevo

		btnNuevoProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ventanaNuevoProducto.setExisteProveedor(true);
				ventanaNuevoProducto.setProveedor(miProveedor);
				ventanaNuevoProducto.setVisible(true);

			}
		});

		// Listener Boton Eliminar producto
		btnEliminarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Separo el string del productoSeleccionado
				String[] splited = productoSeleccionado.split("-");

				// Obtengo el nombre del producto a eliminar
				String nombreProducto = splited[0];

				// Elimino el producto
				miProveedor.eliminarProducto(nombreProducto.trim());
				// Repaint
				setMiProveedor(miProveedor);
				principal.repaint();
				btnEliminarProducto.setEnabled(false);
			}
		});

	}
}
