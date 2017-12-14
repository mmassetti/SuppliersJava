package sistema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sistema {

	private List<Proveedor> misProveedores;
	private List<Entrega> misEntregas;
	private ManejadorArchivoProveedores miManejadorProveedores;
	private ManejadorArchivoEntregas miManejadorEntregas;

	public Sistema() {
		misProveedores = new ArrayList<Proveedor>();
		misEntregas = new ArrayList<Entrega>();

		miManejadorProveedores = new ManejadorArchivoProveedores();
		if (miManejadorProveedores.existeArchivo() == true)
			misProveedores = cargarProveedores();

		miManejadorEntregas = new ManejadorArchivoEntregas();
		if (miManejadorEntregas.existeArchivo() == true)
			misEntregas = cargarEntregas();
	}

	public void agregarProveedor(Proveedor p) {
		misProveedores.add(p);
		miManejadorProveedores.escribirArchivo(this);
	}

	public void agregarEntrega(Entrega e) {
		if (misEntregas.size() > 0) {
			Entrega ent = getEntrega(e.getProveedor());
			if (ent == null) { // El proveedor no tiene ninguna entrega cargada,
								// se agrega y listo
			} else { // El proveedor tenia una entrega, se elimina y se mantiene
						// solo la ultima en la lista
				// Elimino entrega previa
				misEntregas.remove(ent);

			}
		}
		misEntregas.add(e);
		miManejadorEntregas.escribirArchivo(this);

	}

	public void actualizarProveedores() {
		miManejadorProveedores.escribirArchivo(this);
	}

	public void eliminarProveedor(String nombre) {
		Iterator<Proveedor> it = getProveedores().iterator();
		boolean encontre = false;
		while (it.hasNext() && !encontre) {
			Proveedor prov = it.next();
			if (prov.getNombre().equals(nombre)) {
				it.remove();
				encontre = true;
			}
		}

		miManejadorProveedores.escribirArchivo(this);
	}

	public List<Proveedor> getProveedores() {
		return misProveedores;
	}

	public List<Entrega> getMisEntregas() {
		return misEntregas;
	}

	public void setMisEntregas(List<Entrega> misEntregas) {
		this.misEntregas = misEntregas;
	}

	public Proveedor getProveedor(int pos) {
		return misProveedores.get(pos);
	}

	public Proveedor getProveedor(String nombre) {
		Proveedor p = null;
		boolean encontre = false;
		int i = 0;
		while (!encontre) {
			if (getProveedor(i).getNombre().equals(nombre)) {
				encontre = true;
				p = getProveedor(i);
			} else {
				i++;
			}
		}
		return p;
	}

	public Entrega getEntrega(int pos) {
		return misEntregas.get(pos);
	}

	public Entrega getEntrega(String nombre) {
		String nombreFinal = nombre.trim();
		Entrega e = null;
		boolean encontre = false;
		int i = 0;
		while (i < misEntregas.size() && !encontre) {
			if (getEntrega(i).getProveedor().equals(nombreFinal)) {
				e = getEntrega(i);
				encontre = true;
			} else {
				i++;
			}
		}
		return e;
	}

	public List<Proveedor> cargarProveedores() {
		ArrayList<Proveedor> listaProv = new ArrayList<Proveedor>();
		listaProv = (ArrayList<Proveedor>) miManejadorProveedores.leerArchivo();
		return listaProv;
	}

	public List<Entrega> cargarEntregas() {
		ArrayList<Entrega> listaEntregas = new ArrayList<Entrega>();
		listaEntregas = (ArrayList<Entrega>) miManejadorEntregas.leerArchivo();
		return listaEntregas;
	}

}
