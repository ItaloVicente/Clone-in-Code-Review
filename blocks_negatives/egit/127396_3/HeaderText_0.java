	private static void copyToClipboard(String str, Shell shell) {
		Clipboard clipboard= new Clipboard(shell.getDisplay());
		try {
			clipboard.setContents(new String[] { str },	new Transfer[] { TextTransfer.getInstance() });
		} catch (SWTError ex) {
			if (ex.code != DND.ERROR_CANNOT_SET_CLIPBOARD)
				throw ex;
			String title= UIText.Header_copy_SHA1_error_title;
			String message= UIText.Header_copy_SHA1_error_message;
			if (MessageDialog.openQuestion(shell, title, message))
				copyToClipboard(str, shell);
		} finally {
			clipboard.dispose();
		}
	}

