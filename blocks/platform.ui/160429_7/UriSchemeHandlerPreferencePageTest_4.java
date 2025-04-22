	private void waitForJob() throws InterruptedException {
		this.page.osRegistrationReadingJob.join();
		Display display = Display.getCurrent();
		while (display.readAndDispatch()) {
		}
	}

