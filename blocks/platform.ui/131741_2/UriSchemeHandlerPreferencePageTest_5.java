		if (checked) {
			assertEquals(IDEWorkbenchMessages.UrlHandlerPreferencePage_Column_Handler_Text_Current_Application,
					tableItem.getText(2));
		} else if (!information.isHandled() && information.getHandlerInstanceLocation() != null) {
			assertEquals(IDEWorkbenchMessages.UrlHandlerPreferencePage_Column_Handler_Text_Other_Application,
					tableItem.getText(2));
		} else {
			assertEquals("", tableItem.getText(2));
		}

