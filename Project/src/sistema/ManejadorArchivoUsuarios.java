package sistema;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ManejadorArchivoUsuarios extends Manejador {
	// private Document doc;
	private Element usuario, clave;

	public ManejadorArchivoUsuarios() {
		rutaArchivo = "./lista_usuarios.xml";
		archivo = new File(rutaArchivo);
	}

	public void escribirArchivo(List<Usuario> listaUsuarios) {
		try {
			crearDoc();
			for (Usuario u : listaUsuarios) {

				/** Obtiene atributos del usuario */
				String nombreUsuario = u.getNombreUsuario();
				String claveUsuario = u.getClave();

				/** Crear usuario */
				usuario = new Element("usuario");

				/** Nombre del usuario */
				nombre = new Element("nombre");
				nombre.addContent(new Text(nombreUsuario));

				/** Clave del usuario */
				clave = new Element("clave");
				clave.addContent(new Text(claveUsuario));

				// Agrega atributos al usuario
				usuario.addContent(nombre);
				usuario.addContent(clave);

				// Agrega el usuario a la lista de usuarios
				raiz.addContent(usuario);

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

	public List<Usuario> leerArchivo() {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

		SAXBuilder builder = new SAXBuilder();
		try {
			// Parsea el archivo dado en un documento JDOM
			Document readDoc = builder.build(new File(rutaArchivo));

			// Retorna la raiz del documento
			Element root = readDoc.getRootElement();

			// Obtiene cantidad de usuarios
			int cantidadUsuarios = root.getChildren().size();

			for (int i = 0; i < cantidadUsuarios; i++) {
				Element usuario = root.getChildren().get(i);
				String nombre = usuario.getChildText("nombre");
				String clave = usuario.getChildText("clave");

				// Crea el usuario
				Usuario u = new Usuario(nombre, clave);

				// Agrega el proveedor a la lista de proveedores
				listaUsuarios.add(u);

			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaUsuarios;
	}

	private void crearDoc() {
		super.crearDoc("usuarios");

	}

}
