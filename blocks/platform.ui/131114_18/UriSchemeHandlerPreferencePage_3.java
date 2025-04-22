		private void handleCheckbox(CheckStateChangedEvent event) {
			UiSchemeInformation schemeInformation = (UiSchemeInformation) event.getElement();

			if (event.getChecked() && schemeIsHandledByOther(schemeInformation.information)) {
				schemeInformation.checked = false;
				tableViewer.setChecked(schemeInformation, schemeInformation.checked);

				messageDialogWrapper.openWarning(getShell(),
						IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp,
						NLS.bind(IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp_Description,
								schemeInformation.information.getHandlerInstanceLocation(),
								schemeInformation.information.getName()));

				return;
