		Callable<Integer> packRefs = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				syncPoint.await();
				try {
					gc.packRefs();
					return valueOf(0);
				} catch (IOException e) {
					return valueOf(1);
				}
