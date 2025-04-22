		final Runnable backgroundIdle = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						backgroundJobsDone.await();
						return;
					} catch (InterruptedException e) {
					}
