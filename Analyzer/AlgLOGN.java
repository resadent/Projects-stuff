
public class AlgLOGN {
	public static synchronized void f(long n) {
		long a = 0;
		for (int i = 0; i < Math.log((double) n); i++) {
			a++;
		}
	}
}