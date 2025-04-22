		private void handleCheckbox(Event event) {
			TableItem tableItem = (TableItem) event.item;
			if (tableItem != null && tableItem.getData() instanceof UiSchemeInformation) {
				UiSchemeInformation schemeInformation = (UiSchemeInformation) tableItem.getData();

				if (tableItem.getChecked() && schemeIsHandledByOther(schemeInformation.information)) {
					event.doit = false;
					tableItem.setChecked(false);
					MessageDialog.openWarning(getShell(),
							IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp,
							NLS.bind(IDEWorkbenchMessages.UriHandlerPreferencePage_Warning_OtherApp_Description,
									schemeInformation.information.getHandlerInstanceLocation(),
									schemeInformation.information.getName()));
					return;
				}
				schemeInformation.checked = tableItem.getChecked();
				setSchemeDetails(schemeInformation);
