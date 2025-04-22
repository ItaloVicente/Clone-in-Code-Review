	@Test
	public void doesNothingIfEclipseLauncherPathCannotBeDetermined() {
		operatingSystemRegistration.launcherPath = null;

		this.page.init(null);

		this.page.createContents(this.page.getShell());

		assertFalse(page.tableViewer.getControl().getEnabled());
		assertNotNull(page.getErrorMessage());

		assertTrue(page.performOk());
	}

