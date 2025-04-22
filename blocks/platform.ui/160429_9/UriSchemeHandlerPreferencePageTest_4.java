	private void assertLoadingScheme(TableItem tableItem, ISchemeInformation information) {
		UiSchemeInformation uiInformation = (UiSchemeInformation) tableItem.getData();
		assertFalse(uiInformation.checked);
		assertEquals(information.getName(), uiInformation.getName());
		assertEquals(information.getDescription(), uiInformation.getDescription());
		assertEquals(IDEWorkbenchMessages.UrlHandlerPreferencePage_LoadingText,
				uiInformation.getHandlerInstanceLocation());

		assertFalse(tableItem.getChecked());
		assertEquals(information.getName(), tableItem.getText(0));
		assertEquals(information.getDescription(), tableItem.getText(1));
		assertEquals(IDEWorkbenchMessages.UrlHandlerPreferencePage_LoadingText, tableItem.getText(2));
