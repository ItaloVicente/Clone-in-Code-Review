		final Runnable backgroundIdle = () -> {
			while (true) {
				try {
					backgroundJobsDone.await();
					return;
				} catch (InterruptedException e) {
