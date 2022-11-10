package bandeja;

import java.util.concurrent.*;

public class Bandeja {

	/*
	 * Supongamos una pastelería que celebra su aniversario ofreciendo
	 * pastelitos gratis a sus clientes en una bandeja a la entrada del local.
	 * En la bandeja caben 8 pastelitos y un reponedor (hebra) que se encarga de
	 * recargar los pastelitos. Existen pastelitos de dos tipos, los normales y
	 * los Premium y en la bandeja siempre hay como máximo 4 pasteles de cada
	 * tipo. Inicialmente la bandeja está llena. Por otro lado, hay N clientes
	 * (hebras) de dos tipos, normales y premium, de tal manera que, cuando los
	 * clientes (N=10, 5 premium y 5 normales) entran en la pastelería, pueden
	 * tomar un pastel de su tipo. En el caso de que no hubiera pasteles de su
	 * tipo se esperarán a que el reponedor los reponga porque quieren
	 * aprovechar el regalo. Una vez que lo consigue se va de la pastelería, se
	 * duerme un rato y vuelve a entrar otra vez a tomar otro. La bandeja es
	 * común por lo que mientras un cliente está tomando uno de los pasteles
	 * cualquier otro cliente debe esperar a que termine de cogerlo. Además,
	 * mientras el reponedor está reponiendo, tampoco se pueden coger
	 * pastelitos. El reponedor solo repone cuando se han terminado todos los
	 * pasteles de un tipo. Este comportamiento se repite indefinidamente porque
	 * los clientes nunca se sacian y la pastelería tiene materia prima para
	 * seguir haciendo pastelitos
	 */
	private Semaphore mutex = new Semaphore(1, true), reponerPasteles = new Semaphore(0, true),
			hayPastelesP = new Semaphore(1, true), hayPastelesN = new Semaphore(1, true);
	private final int maximoTipo = 4;
	private int pastelesP = 4, pastelesN = 4, colaPremium = 0, colaNormal = 0;

	/**
	 * el reponedor espera hasta que se termina de un tipo de pasteles. Rellena
	 * pastelitos en la bandeja y *los pone todos de una vez
	 * 
	 * @throws InterruptedException
	 */
	public void reponer() throws InterruptedException {
		reponerPasteles.acquire();
		mutex.acquire();
		pastelesP = maximoTipo;
		pastelesN = maximoTipo;
		while (colaNormal != 0) {
			hayPastelesN.release();
			colaNormal--;
		}
		while (colaPremium != 0) {
			hayPastelesP.release();
			colaPremium--;
		}
		System.out.println("Se han repuesto los pasteles.");
		mutex.release();
	}

	/**
	 * el cliente premium quiere su pastel
	 * 
	 * @throws InterruptedException
	 */

	public void qPremium(int id) throws InterruptedException {
		mutex.acquire();
		if (pastelesP > 0) {
			pastelesP--;
		} else {
			colaPremium++;
			reponerPasteles.release();
			mutex.release();
			System.out.println("El cliente " + id + " llama al reponedor.");
			reponerPasteles.release();
			hayPastelesP.acquire();
			mutex.acquire();
			pastelesP--;
		}
		System.out.println("El cliente p " + id + " ha consumido un pastel.");
		mutex.release();
		Thread.sleep(500);
	}

	/**
	 * el cliente normal quiere su pastel
	 * 
	 * @throws InterruptedException
	 */

	public void qNormal(int id) throws InterruptedException {
		mutex.acquire();
		if (pastelesN > 0) {
			pastelesN--;
		} else {
			colaNormal++;
			reponerPasteles.release();
			mutex.release();
			System.out.println("El cliente " + id + " llama al reponedor.");
			hayPastelesN.acquire();
			mutex.acquire();
			pastelesN--;
		}
		System.out.println("El cliente n " + id + " ha consumido un pastel.");
		mutex.release();
		Thread.sleep(500);
	}

}
