		@Deactivate
		void stop() {
			cancel();
			try {
				join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
