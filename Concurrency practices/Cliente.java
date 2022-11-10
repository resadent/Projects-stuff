package bandeja;

public class Cliente extends Thread {
	private int id;
	private Bandeja bandeja;

	public Cliente(Bandeja bandeja, int id) {
		this.bandeja = bandeja;
		this.id = id;
	}

	public void run() {
		while (true) {
			if (id < 5) {
				try {
					bandeja.qPremium(id);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					bandeja.qNormal(id);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
