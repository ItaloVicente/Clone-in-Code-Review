				schemeInformation.checked = false;
				tableViewer.setChecked(schemeInformation, schemeInformation.checked);

				messageDialogWrapper.openWarning(getShell(), UriHandlerPreferencePage_Warning_OtherApp,
						NLS.bind(UriHandlerPreferencePage_Warning_OtherApp_Description,
								schemeInformation.information.getHandlerInstanceLocation(),
								schemeInformation.information.getName()));

				return;
