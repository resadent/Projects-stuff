package bandeja;

public class Principal {

	public static void main(String[] args) {
		Bandeja bandeja = new Bandeja();
		Cliente[] clientes = new Cliente[10];
		Reponedor reponedor = new Reponedor(bandeja);
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new Cliente(bandeja, i);
		}
		reponedor.start();
		for (int i = 0; i < clientes.length; i++) {
			clientes[i].start();
		}
	}

}
