	private void waitForListenerNotCalled() throws InterruptedException {
		long time = 0;
		while (!listenerCalled.get() && time < 5000) {
			Thread.sleep(100);
			time += 100;
		}
		assertTrue("indexDiffChanged was called where it shouldn't have been",
				!listenerCalled.get());
	}

