			Runnable safeRunnable = new Runnable() {
				@Override
				public void run() {
					safeRun(runnable);
				}
			};
