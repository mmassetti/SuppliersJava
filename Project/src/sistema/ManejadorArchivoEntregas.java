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

public class ManejadorArchivoEntregas extends Manejador {
	private Element fecha, total;
	private String nombreProveedor;
	private Date fechaOperacion;
	private float totalEntrega;
	private ArrayList<Producto> listaProductos;

	public ManejadorArchivoEntregas() {
		rutaArchivo = "./lista_entregas.xml";
		archivo = new File(rutaArchivo);

	}

	public void escribirArchivo(Sistema sistema) {
		try {
			crearDoc();

			for (Entrega e : sistema.getMisEntregas()) {
				/** Obtiene atributos de la entrega */
				nombreProveedor = e.getProveedor();
				fechaOperacion = e.getFecha();
				totalEntrega = e.getTotal();

				/** Crear elemento proveedor */
				proveedor = new Element("proveedor");

				/** Nombre del proveedor */
				nombre = new Element("nombre");
				nombre.addContent(new Text(nombreProveedor));

				/** Fecha de la operacion */
				fecha = new Element("fechaOperacion");

				String f = sdf.format(fechaOperacion);
				fecha.addContent(new Text(f));

				/** Crea elemento productos */
				productos = new Element("productos");

				listaProductos = (ArrayList<Producto>) e.getListaProductos();
				for (Producto prod : listaProductos) {

					/** Producto */
					producto = new Element("producto");

					// Nombre
					productoEspecifico = new Element("nombre");
					productoEspecifico.addContent(new Text(prod.getNombre()));
					producto.addContent(productoEspecifico);

					// Precio unitario
					productoEspecifico = new Element("precioUnitario");
					String s = Float.toString(prod.getPrecioUnitario());
					productoEspecifico.addContent(new Text(s));
					producto.addContent(productoEspecifico);

					// Cantidad
					productoEspecifico = new Element("cantidad");
					s = Integer.toString(prod.getCantidadEntrega());
					productoEspecifico.addContent(new Text(s));
					producto.addContent(productoEspecifico);

					// Subtotal
					productoEspecifico = new Element("subtotal");
					s = Float.toString(prod.getPrecioUnitario() * prod.getCantidadEntrega());
					productoEspecifico.addContent(new Text(s));
					producto.addContent(productoEspecifico);

					// Se agrega a la lista de productos
					productos.addContent(producto);

				}

				// Agrega atributos al proveedor
				proveedor.addContent(nombre);
				proveedor.addContent(fecha);
				proveedor.addContent(productos);

				// Agrega total de los productos de la entrega
				total = new Element("total");
				total.addContent(new Text(String.valueOf(e.getTotal())));

				// Agrega el total al proveedor
				proveedor.addContent(total);

				// Agrega el proveedor a la lista de entregas
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

	public List<Entrega> leerArchivo() {
		ArrayList<Entrega> listaEntregas = new ArrayList<Entrega>();
		SAXBuilder builder = new SAXBuilder();

		try {
			listaProductos = new ArrayList<Producto>();

			// Parsea el archivo dado en un documento JDOM
			Document readDoc = builder.build(new File(rutaArchivo));

			// Retorna la raiz del documento
			Element root = readDoc.getRootElement();

			// Obtiene cantidad de proveedores
			int cantidadProveedores = root.getChildren().size();

			for (int i = 0; i < cantidadProveedores; i++) {
				listaProductos = new ArrayList<Producto>();
				Element proveedor = root.getChildren().get(i);
				String nombreProveedor = proveedor.getChildText("nombre");
				String fechaOperacion = proveedor.getChildText("fechaOperacion");

				// Agrega los productos del proveedor

				Element productos = proveedor.getChildren().get(2);
				int cantidadProductos = productos.getChildren().size();

				totalEntrega = 0;
				for (int j = 0; j < cantidadProductos; j++) {
					String nombreProducto = productos.getChildren().get(j).getChildText("nombre");
					String precioUnitario = productos.getChildren().get(j).getChildText("precioUnitario");
					String cantidad = productos.getChildren().get(j).getChildText("cantidad");
					totalEntrega += Float.valueOf(precioUnitario) * Integer.valueOf(cantidad);

					Producto producto = new Producto(nombreProducto, Float.parseFloat(precioUnitario));
					producto.setCantidadEntrega(Integer.parseInt(cantidad));

					listaProductos.add(producto);
				}

				// Crea Entrega
				Date f = null;
				try {
					f = sdf.parse(fechaOperacion);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				Entrega e = new Entrega(nombreProveedor, listaProductos, f);
				e.setTotal(totalEntrega);

				// Agrega la entrega a la lista de entregas
				listaEntregas.add(e);

			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listaEntregas;

	}

	private void crearDoc() {
		super.crearDoc("entregas");
	}

	

}
