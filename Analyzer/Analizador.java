
import java.util.Arrays;
import java.lang.Math;

public class Analizador {
	/*
	 * public static short NUM_TAMANOS = 10; public static short NUM_PRUEBAS_TAMANO
	 * = 4;
	 */

	public static short NUM_PRUEBAS = 12;
	public static String[] resultados = { "NF", "2N", "N3", "N2", "NLOGN", "N", "LOGN", "1" };
	public static String[] algImplementados = { "2N", "N3", "N2", "NLOGN", "N", "LOGN" };
	public static boolean debug = false;
	public static int NUM = 5;
	public static long[] muestras = { 30, 600, 1 * (int) Math.pow(10, 4), 2 * (int) Math.pow(10, 6),
			5 * (int) Math.pow(10, 7), 6 * (int) Math.pow(10, 13) }; // el primero de esta linea es N
	// muestras para cada uno de los algoritmos a probar

	// las salidas deben ser: 1, logn, nlogn, n2, n3, 2n y nf

	public static void aumentarMuestra(long[] muestras, int indice) {
		muestras[indice] *= 3;
		if (debug)
			System.out.println("Muestra multiplicada ");
	}

	public static double average(double[] array) { // we are going to exclude the first result
		double out = 0.0;
		for (int i = 1; i < array.length; i++) {
			out += array[i];
		}
		return out / array.length;
	}

	public static double probarAlgoritmo(int algAProbar, Temporizador t, long[] tiempos1, long[] tiempos2,
			double[] ratios) {

		double ratio = 0.0;
		long t1 = 0, t2 = 0, minimo1 = Long.MAX_VALUE, minimo2 = Long.MAX_VALUE;

		// cambiarMuestra(muestraParaComparar, NUM, algAProbar);

		if (debug) {
			System.out.println("Se probará el algoritmo " + algImplementados[algAProbar] + " con la muestra "
					+ muestras[algAProbar] + " indice de algoritmo implementado " + algAProbar);
		}

		// boolean tsuficiente = false;
		// int vecesAumentada = 1;

		for (int i = 0; i < NUM_PRUEBAS; i++) { // podemos hacer media o quedarnos con el más pequeño
			/*
			 * puedo hacer que segun el algoritmo a probar la entrada sea una u otra
			 */
			t.iniciar();
			switch (algAProbar) {
			case 0: // 2m
				Alg2N.f(muestras[algAProbar]);
				break;
			case 1: // n3
				AlgN3.f(muestras[algAProbar]);
				break;
			case 2: // n2
				AlgN2.f(muestras[algAProbar]);
				break;
			case 3:
				AlgNLOGN.f(muestras[algAProbar]);
			case 4:
				AlgN.f(muestras[algAProbar]);
			case 5:
				AlgLOGN.f(muestras[algAProbar]);
				break;
			}
			t.parar();
			t1 = t.tiempoPasado();
			tiempos1[i] = t1;
			minimo1 = Math.min(minimo1, t1);
			t.reiniciar();
			t.iniciar();
			Algoritmo.f(muestras[algAProbar]);
			t.parar();
			t2 = t.tiempoPasado();
			tiempos2[i] = t2;
			minimo2 = Math.min(minimo2, t2);
			// en este caso minimo2 es el resultado del programa que nos dan y minimo1 el
			// resultado del programa que nosotros hemos confeccionado
			// ratios[i] = (double) minimo2 / minimo1;
		}
		// if (t1 < 0.5 || t2 < 0.5) {
		// aumentarMuestra(muestras, algAProbar);
		// vecesAumentada++;
		// }

		if (debug) {
			// System.out.println("Tamanos: " + Arrays.toString(muestraParaComparar));
			System.out.println("Tiempos 1: " + Arrays.toString(tiempos1));
			System.out.println("Tiempos 2: " + Arrays.toString(tiempos2));
			// System.out.println("Ratios: " + Arrays.toString(ratios));
		}

		ratio = (double) minimo2 / minimo1;
		return ratio;
	}

	/*
	 * me vale coger ese ínimo o hace falta variar cuántas pruebas hago?
	 * complejidades altas y tamaños altos disparan el tiempo buscar compromiso
	 * 
	 * se pueden (deben) cambiar los algoritmos de referencia hay ejemplos de todos
	 * los órdenes de algoritmos en las transparencias del tema
	 */

	public static void main(String arg[]) {
		/*
		 * Tomar varios ratios con varios tamaños de entrada ¿? El ratio debe ir
		 * creciendo poco a poco Hay alguna solución más elegante que llenar todo de
		 * elifs?
		 * 
		 * hacer un array con las pruebas e ir almacenandolas y sacar la media
		 * 
		 */
		if (debug)
			System.out.println(Arrays.toString(muestras));

		long[] tiempos1;
		long[] tiempos2;
		double[] ratios;
		Temporizador t;
		double ratio = 0, ratioanterior = 0;
		int algoritmoAProbar = 0, algoritmoEncontrado = 0;
		// indice de la prueba que se va a realizar e indice final del algoritmo
		tiempos1 = new long[NUM_PRUEBAS];
		tiempos2 = new long[NUM_PRUEBAS];
		ratios = new double[NUM_PRUEBAS];
		t = new Temporizador();
		boolean encontrado = false;

		while (!encontrado && algoritmoAProbar < algImplementados.length) {
			ratioanterior = ratio;
			ratio = probarAlgoritmo(algoritmoAProbar, t, tiempos1, tiempos2, ratios);
			if (debug)
				System.out.println("Ratio encontrado " + ratio);
			if (algoritmoAProbar == 0) { // si hemos probado el algoritmo 2N
				if (ratio >= 0.3 && ratio <= 3.0) { // es 2N
					encontrado = true;
					algoritmoEncontrado = 1;
				} else if (ratio > 2 && ratio != Double.POSITIVE_INFINITY) { // es NF
					encontrado = true;
					algoritmoEncontrado = 0;
				} else {
					algoritmoAProbar++; // hemos descartado dos algoritmos
				}

			} else { // se da este caso si probamos N3 ó N2
				if (debug)
					System.out.println("entramos en el caso estandar");
				if (ratio >= 0.3 && ratio <= 3.0) {
					encontrado = true;
					algoritmoEncontrado = algoritmoAProbar + 1; // porque antes hemos descartado dos algoritmos
				} else {
					algoritmoAProbar++;
				}
			}
			if (encontrado && debug)
				System.out.println("algoritmo a mostrar " + algoritmoEncontrado);

			/*
			 * if (ratio >= 0.6 && ratio <= 2.0 && algoritmoAProbar != 0 && algoritmoAProbar
			 * != 3) { encontrado = true; algoritmoEncontrado = algoritmoAProbar + 1; //
			 * porque 0 es NF y 1 es 2N } else if (ratio > ratioanterior + 10 &&
			 * algoritmoAProbar != 0 && algoritmoAProbar != 3) { encontrado = true;
			 * algoritmoEncontrado = algoritmoAProbar - 1; } else { algoritmoAProbar++; }
			 */
		}
		if (debug)
			System.out.println("indice de alg " + algoritmoEncontrado);
		if (!encontrado) {
			if (debug) {
				System.out.println("algoritmo no encontrado, asumiendo o(1)");
			}
			algoritmoEncontrado = resultados.length - 1;

		}
		System.out.println(resultados[algoritmoEncontrado]);
	}
}

/*
 * for (int i = 0; i < NUM_TAMANOS; i++) { // podemos hacer media o quedarnos
 * con el más pequeÃ±o for (int j = 0; // j < NUM_PRUEBAS_TAMANO; j++) {
 * t.iniciar(); Algoritmo.f(n1); t.parar(); t1 = t.tiempoPasado(); tiempos1[i] =
 * t1; minimo1 = Math.min(minimo1, t1); t.reiniciar(); t.iniciar();
 * Algoritmo.f(n2); t.parar(); t2 = t.tiempoPasado(); tiempos2[i] = t2; minimo2
 * = Math.min(minimo2, t2); } ratio = (double) minimo2 / minimo1; ratios[i] =
 * ratio; }
 */

/*
 * public static void main(String arg[]) { int[] tamanos = new int[NUM_TAMANOS];
 * for (int i = 1; i < tamanos.length; i++) { tamanos[i] = i * 100; } long[]
 * tiempos1 = new long[NUM_TAMANOS]; long[] tiempos2 = new long[NUM_TAMANOS];
 * double[] ratios = new double[NUM_TAMANOS];
 * 
 * Temporizador t = new Temporizador(); int n1 = 10000, n2 = 20000; long t1 = 0,
 * t2 = 0, minimo1 = Long.MAX_VALUE, minimo2 = Long.MAX_VALUE; double ratio; n1
 * = 10000; n2 = 20000;
 * 
 * for (int i = 0; i < NUM_TAMANOS; i++) { // podemos hacer media o quedarnos
 * con el mÃ¡s pequeÃ±o for (int j = 0; j < NUM_PRUEBAS_TAMANO; j++) {
 * t.iniciar(); Algoritmo.f(n1); t.parar(); t1 = t.tiempoPasado(); tiempos1[i] =
 * t1; minimo1 = Math.min(minimo1, t1); t.reiniciar(); t.iniciar();
 * Algoritmo.f(n2); t.parar(); t2 = t.tiempoPasado(); tiempos2[i] = t2; minimo2
 * = Math.min(minimo2, t2); } ratio = (double) minimo2 / minimo1; ratios[i] =
 * ratio; }
 * 
 * System.out.println("TamaÃ±os: " + Arrays.toString(tamanos));
 * System.out.println("Tiempos 1: " + Arrays.toString(tiempos1));
 * System.out.println("Tiempos 2: " + Arrays.toString(tiempos2));
 * System.out.println("Ratios: " + Arrays.toString(ratios)); }
 */

/*
 * } else if (algoritmoAProbar == 3) { // si hemos probado el algoritmo N if
 * (ratio >= 0.6 && ratio <= 2.0) { // es N encontrado = true;
 * algoritmoEncontrado = 5; // 6 corresponde a N } else if (ratio > 2 && ratio <
 * 20) { // es NLOGN encontrado = true; algoritmoEncontrado = 4; } else if
 * (ratio >= 0.1 && ratio < 0.6) { // es LOGN encontrado = true;
 * algoritmoEncontrado = 6; } else { encontrado = true; algoritmoEncontrado = 7;
 * }
 */
