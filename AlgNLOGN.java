
public class AlgNLOGN {
	public static synchronized void f(long n) {
		long a = 0;
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < Math.log((double) n); i++) {
				a++;
			}
		}
	}
}
