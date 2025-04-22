
		job.setSystem(true);
		job.schedule();
		if (listener != null) {
			job.addJobChangeListener(listener);
		}
