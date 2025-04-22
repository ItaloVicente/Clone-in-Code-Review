	@Test
	public void loadingSchemesShownAfterPageOpened() throws Exception {
		this.page.createContents(this.page.getShell());
		assertLoadingScheme(getTableItem(0), noAppSchemeInfo);
		assertLoadingScheme(getTableItem(1), thisAppSchemeInfo);
		assertLoadingScheme(getTableItem(2), otherAppSchemeInfo);
		waitForJob();
	}

	private void waitForJob() throws InterruptedException {
		this.page.osRegistrationReadingJob.join();
		Display display = Display.getCurrent();
		while (display.readAndDispatch()) {
		}
	}

