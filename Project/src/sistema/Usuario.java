package sistema;

public class Usuario {
	private String nombreUsuario;
	private String clave;
	

	public Usuario(String nombreUsuario, String clave) {
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	

}
