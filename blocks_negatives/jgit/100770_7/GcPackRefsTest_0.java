		Callable<Integer> packRefs = new Callable<Integer>() {

			/** @return 0 for success, 1 in case of error when writing pack */
			@Override
			public Integer call() throws Exception {
				syncPoint.await();
				try {
					gc.packRefs();
					return valueOf(0);
				} catch (IOException e) {
					return valueOf(1);
				}
