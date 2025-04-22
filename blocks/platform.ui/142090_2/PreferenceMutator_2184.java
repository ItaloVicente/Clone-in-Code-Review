	static final void setKeyBinding(String commandId, String keySequenceText)
			throws CoreException, FileNotFoundException, IOException {
		Properties preferences = new Properties();
		String key = "org.eclipse.ui.workbench/org.eclipse.ui.commands"; //$NON-NLS-1$
		String value = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<org.eclipse.ui.commands><activeKeyConfiguration/><keyBinding commandId=\"" + commandId + "\" keySequence=\"" + keySequenceText + "\"/></org.eclipse.ui.commands>"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		preferences.put(key, value);
