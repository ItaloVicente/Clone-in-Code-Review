		});
		job.setUser(false);
		job.setSystem(true);
		loader = job;
		loader.schedule();
		return new Object[0];
	}

	private void cancel() {
		if (loader != null) {
			loader.cancel();
			loader = null;
		}
