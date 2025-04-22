			if (time) {
				long ns = System.nanoTime() - start;
				errw.println(String.format("time %d ms"
						TimeUnit.NANOSECONDS.toMillis(ns)));
				errw.flush();
			}
