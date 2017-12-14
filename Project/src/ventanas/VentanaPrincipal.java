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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import sistema.Entrega;
import sistema.ManejadorArchivoEntregas;
import sistema.Producto;
import sistema.Sistema;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private VentanaProveedores ventanaProveedores;
	private VentanaNuevaEntrega ventanaNuevaEntrega;
	private JPanel contentPane;
	private Sistema miSistema;
	private ManejadorArchivoEntregas miManejadorEntregas;
	private JList<String> listaEntregas;
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private DecimalFormat decimalFormat = new DecimalFormat("#,###.#");
	private String entregaSeleccionada;
	private int indiceSeleccionado;
	private JButton btnProveedores;
	private JButton btnNuevaEntrega;
	private JScrollPane scrollPaneL;

	public VentanaPrincipal() {

		// Creacion del sistema
		miSistema = new Sistema();
		ventanaProveedores = new VentanaProveedores((JFrame) this);
		ventanaProveedores.repaint();
		ventanaNuevaEntrega = new VentanaNuevaEntrega((JFrame) this);
		
		repaint();
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

		// Boton Proveedores
		btnProveedores = new JButton("Proveedores");
		btnProveedores.setBounds(22, 105, 155, 47);
		contentPane.add(btnProveedores);

		// Boton Nueva Entrega
		btnNuevaEntrega = new JButton("Nueva entrega");
		btnNuevaEntrega.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnNuevaEntrega.setBounds(22, 177, 155, 47);
		contentPane.add(btnNuevaEntrega);

		JLabel lblUltimasEntregas = new JLabel("Últimas entregas");
		lblUltimasEntregas.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		lblUltimasEntregas.setBounds(330, 11, 174, 30);
		contentPane.add(lblUltimasEntregas);

		scrollPaneL = new JScrollPane();
		scrollPaneL.setBounds(208, 47, 389, 246);
		contentPane.add(scrollPaneL);

		listaEntregas = new JList<String>(model);
		listaEntregas.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		scrollPaneL.setViewportView(listaEntregas);
		listaEntregas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaEntregas.setBackground(SystemColor.control);

		DefaultListCellRenderer renderer = (DefaultListCellRenderer) listaEntregas.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);

	}

	public void setListeners() {

		// Listener Boton Proveedores
		btnProveedores.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnProveedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				ventanaProveedores.setVisible(true);
			}
		});

		// Listener Boton Nueva Entrega
		btnNuevaEntrega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				ventanaNuevaEntrega.setVisible(true);
				ventanaNuevaEntrega.repaint();

			}
		});

		// Listener JList Ultimas Entregas
		listaEntregas.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
				JList<String> listaAux = (JList<String>) evt.getSource();
				if (evt.getClickCount() == 1) {
					// Un click detectado
					Rectangle r = listaAux.getCellBounds(0, listaAux.getLastVisibleIndex());
					// Si selecciona un espacio en blanco, por defecto se elige
					// el ultimo elemento, con este control se evita dicho bug
					if (r != null && r.contains(evt.getPoint())) {
						indiceSeleccionado = listaAux.locationToIndex(evt.getPoint());
						entregaSeleccionada = model.getElementAt(indiceSeleccionado);
						String detalleEntrega = getDetalleEntrega(entregaSeleccionada);
						JOptionPane.showMessageDialog(contentPane, detalleEntrega, "Detalle de la entrega",
								JOptionPane.INFORMATION_MESSAGE);

					}

				}
			}
		});

	}

	public Sistema getSistema() {
		return miSistema;
	}

	public void repaint() {
		miManejadorEntregas = new ManejadorArchivoEntregas();

		if (!miManejadorEntregas.existeArchivo()) {
			// No hay ninguna entrega registrada aun
		} else {
			model.clear();
			for (Entrega e : miSistema.getMisEntregas()) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String fecha = sdf.format(e.getFecha());
				model.addElement(e.getProveedor() + " - " + fecha + " - $" + decimalFormat.format(e.getTotal()));
			}

		}

	}

	public String getDetalleEntrega(String s) {

		// Separo el string pasado por parametro
		String[] splited = s.split("-");

		// Obtengo el nombre del proveedor
		String nombreProveedor = splited[0];

		// Obtengo el resto de la informacion de la entrega
		Entrega e = miSistema.getEntrega(nombreProveedor);
		
		//Fecha
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String fechaEntrega = sdf.format(e.getFecha());
		
		//Total
		String totalEntrega = String.valueOf(e.getTotal());

		// Armo el detalle
		String detalleProductos = "";

		for (Producto prod : e.getListaProductos()) {
			float subtotal = 0;
			subtotal += prod.getPrecioUnitario() * prod.getCantidadEntrega();
			detalleProductos += ("Nombre producto: " + prod.getNombre() + "\n" + " Cantidad: "
					+ prod.getCantidadEntrega() + "\n" + " Precio unitario: " + prod.getPrecioUnitario() + "\n"
					+ " Subtotal: " + String.format("%.2f",subtotal) + "\n\n");
		}

		String detalle = "Nombre proveedor: " + nombreProveedor + "\n" + " Fecha entrega: " + fechaEntrega + "\n\n"
				+ detalleProductos + " Total: " + totalEntrega + "\n";

		return detalle;
	}

}
