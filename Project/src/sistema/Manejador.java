package sistema;


import java.io.File;
import java.text.SimpleDateFormat;

import org.jdom2.Document;
import org.jdom2.Element;

public abstract class Manejador {
	protected Document doc;
	protected Element raiz,nombre,proveedor, productos, producto, productoEspecifico;
	protected File archivo;
	protected SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	protected String rutaArchivo; 
	
	public boolean existeArchivo() {
		File f = new File(rutaArchivo);
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}

	protected void crearDoc(String elemento) {
		
		// Crea un documento JDOM

		doc = new Document();

		/** Crea un elemento llamado proveedores y lo hace raiz */

		raiz = new Element(elemento);
		doc.setRootElement(raiz);

	}


}
