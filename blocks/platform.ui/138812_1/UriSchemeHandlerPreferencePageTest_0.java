		assertHandlerTextForSelection(page, 2, OTHER_ECLIPSE_HANDLER_LOCATION);
	}

	@Test
	public void checkOtherAppSchemeOnWindowsIsAllowed() {
		this.page.createContents(this.page.getShell());
		operatingSystemRegistration.canOverwriteOtherApplicationsRegistration = true;

		clickTableViewerCheckbox(2, true);

		MessageDialogWrapperSpy spy = (MessageDialogWrapperSpy) page.messageDialogWrapper;

		assertNull(spy.title);
		assertNull(spy.message);

		assertScheme(getTableItem(2), true, otherAppSchemeInfo);
		assertHandlerTextForSelection(page, 2, THIS_ECLIPSE_HANDLER_LOCATION);
