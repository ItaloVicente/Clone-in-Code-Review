				if (operatingSystemRegistration.canOverwriteOtherApplicationsRegistration()) {
					boolean answer = messageDialogWrapper.openQuestion(getShell(),
							UriHandlerPreferencePage_Warning_OtherApp_Confirmation,
							NLS.bind(UriHandlerPreferencePage_Warning_OtherApp_Confirmation_Description,
							schemeInformation.information.getHandlerInstanceLocation(),
							schemeInformation.information.getName()));
					if (answer == false) {
						schemeInformation.checked = false;
						tableViewer.setChecked(schemeInformation, schemeInformation.checked);
						return;
					}
				} else {
					schemeInformation.checked = false;
					tableViewer.setChecked(schemeInformation, schemeInformation.checked);

					messageDialogWrapper.openWarning(getShell(), UriHandlerPreferencePage_Warning_OtherApp,
							NLS.bind(UriHandlerPreferencePage_Warning_OtherApp_Description,
									schemeInformation.information.getHandlerInstanceLocation(),
									schemeInformation.information.getName()));

					return;
				}

