		Callable<Integer> packRefs = () -> {
			syncPoint.await();
			try {
				gc.packRefs();
				return 0;
			} catch (IOException e) {
				return 1;
