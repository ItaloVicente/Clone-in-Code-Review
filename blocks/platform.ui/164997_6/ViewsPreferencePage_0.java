
	private class NotificationPopUp extends AbstractNotificationPopup {

		public NotificationPopUp(Display display) {
			super(display);
			setDelayClose(0);
			setParentShell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		}

		@Override
		protected String getPopupShellTitle() {
			return WorkbenchMessages.ThemeChangeWarningTitle;
		}


		@Override
		protected void createContentArea(Composite parent) {
			parent.setLayout(new RowLayout());

			Label label = new Label(parent, SWT.WRAP);
			label.setText(WorkbenchMessages.ThemeChangeWarningText);
		}
	}

