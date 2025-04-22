	private void gcWithTtl() throws InterruptedException, IOException {
		long start = System.currentTimeMillis();
		do {
			Thread.sleep(10);
		} while (System.currentTimeMillis() <= start);

