			try {
				latch.await();
				Thread.sleep(100);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				if (op != null) {
					op.cancel();
				}
			}
