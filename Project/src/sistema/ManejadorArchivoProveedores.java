package sistema;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ManejadorArchivoProveedores extends Manejador {

	private Element cuit, fechaInicio;
	private ArrayList<Producto> listaProd;
	private String nomProv, nroCuit;
	private Date fecha;


	public ManejadorArchivoProveedores() {
		rutaArchivo = "./lista_proveedores.xml";
		archivo = new File(rutaArchivo);
	}

	public void escribirArchivo(Sistema sistema) {
		try {
			crearDoc();
			for (Proveedor p : sistema.getProveedores()) {

				/** Obtiene atributos del proveedor */
				nomProv = p.getNombre();
				nroCuit = p.getCuit();
				fecha = p.getFechaInicioContrato();

				/** Crear proveedor */
				proveedor = new Element("proveedor");

				/** Nombre del proveedor */
				nombre = new Element("nombre");
				nombre.addContent(new Text(nomProv));

				/** Cuit del proveedor */
				cuit = new Element("cuit");
				cuit.addContent(new Text(nroCuit));

				/** Fecha de inicio del contrato */
				fechaInicio = new Element("fechaInicioContrato");
				String f = sdf.format(fecha);
				fechaInicio.addContent(new Text(f));

				/** Crea etiqueta productos */
				productos = new Element("productos");

				listaProd = (ArrayList<Producto>) p.getMisProductos();
				for (Producto prod : listaProd) {

					/** Producto */
					producto = new Element("producto");

					// Nombre
					productoEspecifico = new Element("nombre");
					productoEspecifico.addContent(new Text((prod.getNombre())));
					producto.addContent(productoEspecifico);

					// Precio unitario
					productoEspecifico = new Element("precioUnitario");
					String s = Float.toString(prod.getPrecioUnitario());
					productoEspecifico.addContent(new Text(s));
					producto.addContent(productoEspecifico);

					// Se agrega a la lista de productos
					productos.addContent(producto);

				}

				// Agrega atributos al proveedor
				proveedor.addContent(nombre);
				proveedor.addContent(cuit);
				proveedor.addContent(fechaInicio);
				proveedor.addContent(productos);

				// Agrega el proveedor a la lista de proveedores
				raiz.addContent(proveedor);

			}

			// Indentancion del archivo
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());

			// Crea el archivo XML y escribe en el
			xmlOutput.output(doc, new FileOutputStream(archivo));

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Proveedor> leerArchivo() {
		ArrayList<Proveedor> listaProveedores = new ArrayList<Proveedor>();

		SAXBuilder builder = new SAXBuilder();
		try {
			// Parsea el archivo dado en un documento JDOM
			Document readDoc = builder.build(new File(rutaArchivo));

			// Retorna la raiz del documento
			Element root = readDoc.getRootElement();

			// Obtiene cantidad de proveedores
			int cantidadProveedores = root.getChildren().size();

			for (int i = 0; i < cantidadProveedores; i++) {
				Element proveedor = root.getChildren().get(i);
				String nombre = proveedor.getChildText("nombre");
				String cuit = proveedor.getChildText("cuit");
				String fecha = proveedor.getChildText("fechaInicioContrato");

				// Crea el proveedor
				Date f;
				Proveedor p = null;
				try {
					f = sdf.parse(fecha);
					p = new Proveedor(nombre, cuit, f);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				// Agrega los productos del proveedor
				Element productos = proveedor.getChildren().get(3);
				int cantidadProductos = productos.getChildren().size();

				for (int j = 0; j < cantidadProductos; j++) {
					String nombreProducto = productos.getChildren().get(j).getChildText("nombre");
					String precioUnitario = productos.getChildren().get(j).getChildText("precioUnitario");
					Producto prod = new Producto(nombreProducto, Float.parseFloat(precioUnitario));
					p.agregarProducto(prod);
				}

				// Agrega el proveedor a la lista de proveedores
				listaProveedores.add(p);

			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaProveedores;
	}

	private void crearDoc() {
		super.crearDoc("proveedores");
	}

}
