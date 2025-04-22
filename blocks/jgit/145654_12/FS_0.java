		private static Duration measureClockResolution() {
			Duration clockResolution = Duration.ZERO;
			for (int i = 0; i < 10; i++) {
				Instant t1 = Instant.now();
				Instant t2 = t1;
				while (t2.compareTo(t1) <= 0) {
					t2 = Instant.now();
				}
				Duration r = Duration.between(t1
				if (r.compareTo(clockResolution) > 0) {
					clockResolution = r;
				}
