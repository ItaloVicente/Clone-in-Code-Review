	public void doesNotRegistersSchemesInOperatingSystemOnApplyWhenLoading() throws Exception {
		this.page.createContents(this.page.getShell());

		page.performOk();

		OperatingSystemRegistrationMock mock = (OperatingSystemRegistrationMock) page.operatingSystemRegistration;
		assertEquals(0, mock.addedSchemes.size());

		assertEquals(0, mock.removedSchemes.size());
		waitForJob();
	}

	@Test
	public void showsErrorOnOperatingSystemRegistrationReadError() throws Exception {
