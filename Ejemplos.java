

public class Ejemplos {

	public void AlgO1(int n) {
		n++;
	}

	public void AlgOn(int n) {
		int a = 0;
		for (int i = 0; i < n; i++) {
			a++;
		}
	}

	public void AlgOlogn(int n) {
		int a = 0;
		for (int j = 0; j < n; j = j * 2) {
			a++;
		}
	}

	public void AlgOnlogn(int n) {
		int a = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j = j * 2) {
				a++;
			}
		}
	}

	public void AlgOn2(int n) {
		int a = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a++;
			}
		}
	}

	public void AlgOn3(int n) {
		int a = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int j2 = 0; j2 < n; j2++) {
					a++;
				}
			}
		}
	}

	public int AlgOnf(int n) { // algoritmo de fibonacci clásico
		// n >=0
		if (n == 0)
			return 0;
		if (n == 1)
			return 1;
		else
			return AlgOnf(n - 1) + AlgOnf(n - 2);
	}

}
