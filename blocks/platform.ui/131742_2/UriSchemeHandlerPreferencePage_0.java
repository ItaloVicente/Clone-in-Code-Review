	private void setSchemeDetails(UiSchemeInformation schemeInfo) {
		if (schemeInfo.checked) {
			handlerLocation.setText(NLS.bind(
					IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Current_Application, currentLocation));

		} else if (schemeIsHandledByOther(schemeInfo.information)) {
			handlerLocation
					.setText(NLS.bind(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_Other_Application,
							schemeInfo.information.getHandlerInstanceLocation()));
		} else {
			handlerLocation.setText(IDEWorkbenchMessages.UrlHandlerPreferencePage_Label_Message_No_Application);
