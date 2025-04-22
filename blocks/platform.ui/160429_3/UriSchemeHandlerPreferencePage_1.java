	private void startRegistrationReadingJob() {
		OsRegistrationReadingJob osRegistrationReadingJob = new OsRegistrationReadingJob();
		osRegistrationReadingJob.setSystem(true);
		osRegistrationReadingJob.schedule();
	}

