	}

	private void assertLoadingScheme(TableItem tableItem, ISchemeInformation information) {
		UiSchemeInformation uiInformation = (UiSchemeInformation) tableItem.getData();
		assertFalse(uiInformation.checked);
		assertEquals(information.getName(), uiInformation.getName());
		assertEquals(information.getDescription(), uiInformation.getDescription());
		assertEquals(IDEWorkbenchMessages.UrlHandlerPreferencePage_LoadingText,
				uiInformation.getHandlerInstanceLocation());
