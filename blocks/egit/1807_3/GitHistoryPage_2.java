			try {
				job.join();
			} catch (InterruptedException e) {
				cancelRefreshJob();
				return;
			}
