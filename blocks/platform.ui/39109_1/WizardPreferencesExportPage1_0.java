
	private void reportException(Exception e) {
		Shell shell = getControl().getShell();
		WorkbenchPlugin.log(e.getMessage(), e);
		MessageDialog.open(MessageDialog.ERROR, shell, new String(), e.getLocalizedMessage(), SWT.SHEET);
	}
