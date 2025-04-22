	@Test
	public void showsErrorMessageAndHandlesExceptionIfOperationsSystemRegistrationCannotBeLoaded() {
		operatingSystemRegistrationCreationExtension = new CoreException(
				new Status(IStatus.ERROR, getClass(), "simulated exception"));

		this.page.createContents(this.shell);

		assertFalse(page.tableViewer.getControl().getEnabled());
		assertEquals(IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Getting_OS_Registration,
				page.getErrorMessage());
		assertTrue(((Collection<?>) page.tableViewer.getInput()).isEmpty());

		assertErrorStatusRaised(IDEWorkbenchMessages.UrlHandlerPreferencePage_Error_Getting_OS_Registration);

		assertTrue(page.performOk());
	}

