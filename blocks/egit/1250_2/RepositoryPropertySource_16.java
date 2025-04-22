		@Override
		protected Control createDialogArea(Composite parent) {
			Composite main = (Composite) super.createDialogArea(parent);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true,
					true).applyTo(main);
			Control result = new ConfigurationEditorComponent(main, myConfig,
					true) {
				@Override
				protected void setErrorMessage(String message) {
					EditDialog.this.setErrorMessage(message);
				}
			}.createContents();
			applyDialogFont(main);
			return result;
