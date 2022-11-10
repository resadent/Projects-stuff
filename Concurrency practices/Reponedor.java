package bandeja;

public class Reponedor extends Thread {
	private Bandeja bandeja;

	public Reponedor(Bandeja bandeja) {
		this.bandeja = bandeja;
	}

	public void run() {
		while (true) {
			try {
				bandeja.reponer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
