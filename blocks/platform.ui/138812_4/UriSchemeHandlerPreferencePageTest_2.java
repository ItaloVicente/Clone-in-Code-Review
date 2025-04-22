		assertHandlerTextForSelection(page, 2, OTHER_ECLIPSE_HANDLER_LOCATION);
	}

	@Test
	public void checkOtherAppSchemeOnWindowsIsAllowed() {
		this.page.createContents(this.page.getShell());
		operatingSystemRegistration.canOverwriteOtherApplicationsRegistration = true;
		messageDialogSpy.actualAnswer = true;

		clickTableViewerCheckbox(2, true);

		MessageDialogWrapperSpy spy = (MessageDialogWrapperSpy) page.messageDialogWrapper;

		assertEquals(IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp_Confirmation, spy.title);
		String expected = NLS.bind(
				IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp_Confirmation_Description,
				OTHER_ECLIPSE_HANDLER_LOCATION, "hello2");
		assertEquals(expected, spy.message);

		assertScheme(getTableItem(2), true, otherAppSchemeInfo);
		assertHandlerTextForSelection(page, 2, THIS_ECLIPSE_HANDLER_LOCATION);
	}

	@Test
	public void checkOtherAppSchemeOnWindowsIsAllowedButNothingChangesWhenUserSaysNo() {
		this.page.createContents(this.page.getShell());
		operatingSystemRegistration.canOverwriteOtherApplicationsRegistration = true;
		messageDialogSpy.actualAnswer = false;

		clickTableViewerCheckbox(2, true);

		MessageDialogWrapperSpy spy = (MessageDialogWrapperSpy) page.messageDialogWrapper;

		assertEquals(IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp_Confirmation, spy.title);
		String expected = NLS.bind(
				IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp_Confirmation_Description,
				OTHER_ECLIPSE_HANDLER_LOCATION, "hello2");
		assertEquals(expected, spy.message);

		assertScheme(getTableItem(2), false, otherAppSchemeInfo);
		assertHandlerTextForSelection(page, 2, OTHER_ECLIPSE_HANDLER_LOCATION);
