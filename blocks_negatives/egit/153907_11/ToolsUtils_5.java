	public static int informUser(String textHeader, String message) {
		MessageBox mbox = new MessageBox(Display.getCurrent().getActiveShell(),
				SWT.ICON_INFORMATION | SWT.OK);
		mbox.setText(textHeader);
		mbox.setMessage(message);
		return mbox.open();
