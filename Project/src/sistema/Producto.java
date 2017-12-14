package sistema;

public class Producto {
	private String nombre;
	private float precioUnitario;
	private int cantidadEntrega;

	public Producto(String n, float p) {
		nombre = n;
		precioUnitario = p;
	}

	public int getCantidadEntrega() {
		return cantidadEntrega;
	}

	public void setCantidadEntrega(int cantidadEntrega) {
		this.cantidadEntrega = cantidadEntrega;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

}
