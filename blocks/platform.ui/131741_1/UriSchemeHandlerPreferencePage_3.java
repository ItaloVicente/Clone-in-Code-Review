				case 2:
					String text = ""; //$NON-NLS-1$
					if (schemeInfo.checked) {
						text = IDEWorkbenchMessages.UrlHandlerPreferencePage_Column_Handler_Text_Current_Application;

					} else if (schemeIsHandledByOther(schemeInfo.information)) {
						text = IDEWorkbenchMessages.UrlHandlerPreferencePage_Column_Handler_Text_Other_Application;
					}
					return text;
