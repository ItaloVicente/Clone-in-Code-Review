
	public static long delay(long last
		long r = Math.max(0
		if (r > 0) {
			int c = (int) Math.min(r + 1
			r = RNG.nextInt(c);
		}
		return Math.max(Math.min(min + r
	}
