		long r = fsTimestampResolution.toMillis();
		long elapsed = read - lastModified;
		if (r < 20) {
			return elapsed > (r * 3) / 2 + 5;
		} else if (r < 120) {
			return elapsed > (r * 4) / 3 + 10;
		} else {
			return elapsed > r + 50;
		}
