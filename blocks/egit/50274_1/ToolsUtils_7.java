	public static int informUserAboutError(String textHeader, String message) {
		MessageBox mbox = new MessageBox(Display.getCurrent().getActiveShell(),
				SWT.ICON_ERROR | SWT.OK);
		mbox.setText(textHeader);
		mbox.setMessage(message);
		return mbox.open();
	}

