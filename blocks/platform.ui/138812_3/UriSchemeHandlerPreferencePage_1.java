
		default boolean openQuestion(Shell parent, String title, String message) {
			MessageDialog dlg = new MessageDialog(parent, title, null, message, MessageDialog.CONFIRM,
					0, UriHandlerPreferencePage_Confirm_Handle, IDialogConstants.CANCEL_LABEL);
			dlg.open();
			if (dlg.getReturnCode() != IDialogConstants.OK_ID) {
				return false;
			}
			return true;
		}
