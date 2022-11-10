
public class AlgN2 {
	public static synchronized void f(long n) {
		int a = 0;
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				a++;
			}
		}
	}
}
