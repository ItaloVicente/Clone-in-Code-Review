			if ("".equals(name)) { //$NON-NLS-1$
				MessageDialog.openInformation(shell, WorkbenchMessages.PropertyDialog_messageTitle,
						WorkbenchMessages.PropertyDialog_noPropertyMessageForUnknow);
			} else {
				MessageDialog.openInformation(shell, WorkbenchMessages.PropertyDialog_messageTitle,
						NLS.bind(WorkbenchMessages.PropertyDialog_noPropertyMessage, name));
			}
