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
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import sistema.Proveedor;
import sistema.Sistema;

public class VentanaProveedores extends JFrame {

	private static final long serialVersionUID = 1L;
	private VentanaNuevoProveedor ventanaNuevoProveedor;
	private VentanaEditarProveedor ventanaEditarProveedor;
	private VentanaPrincipal principal;
	private JPanel contentPane;
	private Sistema miSistema;
	private ArrayList<String> listaProveedores = new ArrayList<String>();
	private JButton btnNuevo;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnVolver;
	private JLabel lblListaDeProveedores;
	private JList<String> jListProveedores;
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private int indiceSeleccionado;
	private String proveedorSeleccionado;
	private JScrollPane scrollPane;

	public VentanaProveedores(JFrame gui) {
		principal = (VentanaPrincipal) gui;
		ventanaNuevoProveedor = new VentanaNuevoProveedor((JFrame) this);
		ventanaEditarProveedor = new VentanaEditarProveedor((JFrame) this);
		miSistema = principal.getSistema();
		iniciarGui();
		setListeners();

	}

	public void iniciarGui() {

		// Propiedades de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 356);
		setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Boton Nuevo
		btnNuevo = new JButton("Nuevo proveedor");
		btnNuevo.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnNuevo.setBounds(20, 45, 178, 45);
		contentPane.add(btnNuevo);

		// Boton Volver
		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnVolver.setBounds(471, 283, 89, 23);
		contentPane.add(btnVolver);

		// Boton Editar
		btnEditar = new JButton("Editar proveedor");
		btnEditar.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnEditar.setEnabled(false);
		btnEditar.setBounds(20, 106, 178, 45);
		contentPane.add(btnEditar);

		// Boton Eliminar
		btnEliminar = new JButton("Eliminar proveedor");
		btnEliminar.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(20, 170, 178, 45);
		contentPane.add(btnEliminar);

		// Lista de Proveedores - JLabel, ScrollPanel y JList

		lblListaDeProveedores = new JLabel("Lista de proveedores");
		lblListaDeProveedores.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblListaDeProveedores.setBounds(306, 22, 161, 23);
		contentPane.add(lblListaDeProveedores);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(231, 46, 329, 169);
		contentPane.add(scrollPane);

		jListProveedores = new JList<String>(model);
		scrollPane.setViewportView(jListProveedores);
		jListProveedores.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		jListProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jListProveedores.setBackground(SystemColor.control);

		DefaultListCellRenderer renderer = (DefaultListCellRenderer) jListProveedores.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);

	}

	public void setListeners() {

		// Boton Nuevo
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditar.setEnabled(false);
				btnEliminar.setEnabled(false);
				dispose();
				ventanaNuevoProveedor.setVisible(true);
			}
		});

		// Boton Volver
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnEditar.setEnabled(false);
				btnEliminar.setEnabled(false);
				dispose();
				principal.setVisible(true);
			}
		});

		// Boton Editar
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Proveedor p = miSistema.getProveedor(proveedorSeleccionado);

				dispose();
				ventanaEditarProveedor.setVisible(true);
				ventanaEditarProveedor.setMiProveedor(p);
				ventanaEditarProveedor.repaint();

				// Vuelve a desactivar botones Eliminar y Editar
				btnEliminar.setEnabled(false);
				btnEditar.setEnabled(false);

				repaint();

			}
		});

		// Boton Eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miSistema.eliminarProveedor(proveedorSeleccionado);

				// Vuelve a desactivar botones Eliminar y Editar
				btnEliminar.setEnabled(false);
				btnEditar.setEnabled(false);

				repaint();

			}
		});

		// JList de Proveedores
		jListProveedores.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent evt) {
				JList<String> listaAux = (JList<String>) evt.getSource();
				if (evt.getClickCount() == 1) {
					// Cuando se hace click
					
					Rectangle r = listaAux.getCellBounds(0, listaAux.getLastVisibleIndex());
					// Si selecciona un espacio en blanco, por defecto se elige
					// el ultimo elemento, con este control se evita dicho bug
					if (r != null && r.contains(evt.getPoint())) {
						indiceSeleccionado = listaAux.locationToIndex(evt.getPoint());
						proveedorSeleccionado = model.getElementAt(indiceSeleccionado);

						// Activa botones editar y eliminar
						btnEditar.setEnabled(true);
						btnEliminar.setEnabled(true);

					}

				}
			}
		});
	}

	public Sistema getSistema() {
		return miSistema;
	}

	public void setSistema(Sistema s) {
		miSistema = s;
	}

	private void cargarListaProveedores() {
		listaProveedores = new ArrayList<String>();
		for (Proveedor p : miSistema.getProveedores()) {
			listaProveedores.add(p.getNombre());
		}
	}

	public void repaint() {
		model.clear();
		miSistema = principal.getSistema();
		cargarListaProveedores();
		for (String s : listaProveedores) {
			model.addElement(s);
		}
	}

}
