	private final static class EditDialog extends TitleAreaDialog {
		private final FileBasedConfig myConfig;

		private final String myTitle;

		public EditDialog(Shell shell, FileBasedConfig config, String title) {
			super(shell);
			myConfig = config;
			setShellStyle(getShellStyle() | SWT.SHELL_TRIM);
			myTitle = title;
