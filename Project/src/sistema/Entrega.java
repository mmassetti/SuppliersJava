package sistema;

import java.util.Date;
import java.util.List;

public class Entrega {
	private String proveedor;
	private Date fecha;
	private float total;
	private List<Producto> listaProductos;

	public Entrega(String p, List<Producto> l, Date f) {
		proveedor = p;
		listaProductos = l;
		fecha = f;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

}
