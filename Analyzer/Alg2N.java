
public class Alg2N {

	public static synchronized int f(long n) {
		if (n == 0)
			return 0;
		else if (n == 1)
			return 1;
		else
			return f(n - 2) + f(n - 1);
	}

}
