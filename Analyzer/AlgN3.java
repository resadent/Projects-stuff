
public class AlgN3 {

	public static synchronized void f(long n) {
		long a = 0;
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				for (int k = 0; k < n; k++) {
					a++;
				}
			}
		}
	}

}
