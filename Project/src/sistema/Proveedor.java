package sistema;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Proveedor {
	private String nombre;
	private String cuit;
	private Date fechaInicioContrato;
	private List<Producto> misProductos;

	public Proveedor(String n) {
		nombre = n;
	}

	public Proveedor(String n, String c, Date f) {
		nombre = n;
		cuit = c;
		fechaInicioContrato = f;
		misProductos = new ArrayList<Producto>();
	}

	public Proveedor(String n, Date f) {
		nombre = n;
		fechaInicioContrato = f;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public Date getFechaInicioContrato() {
		return fechaInicioContrato;
	}

	public void setFechaInicioContrato(Date fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}

	public List<Producto> getMisProductos() {
		return misProductos;
	}

	public void setMisProductos(List<Producto> misProductos) {
		this.misProductos = misProductos;
	}

	public void agregarProducto(Producto p) {
		misProductos.add(p);
	}

	public Producto getProducto(int pos) {
		return misProductos.get(pos);
	}

	public Producto getProducto(String nombre) {
		Producto p = null;
		Iterator<Producto> it = getMisProductos().iterator();
		boolean encontre = false;
		while (it.hasNext() && !encontre) {
			Producto prod = it.next();
			if (prod.getNombre().equals(nombre)) {
				p = prod;
				encontre = true;
			}
		}
		return p;
	}

	public void eliminarProducto(String nombre) {
		Iterator<Producto> it = getMisProductos().iterator();
		boolean encontre = false;
		while (it.hasNext() && !encontre) {
			Producto prod = it.next();
			if (prod.getNombre().equals(nombre)) {
				it.remove();
				encontre = true;
			}
		}
	}
}
