
	private class NotificationPopUp extends AbstractNotificationPopup {

		public NotificationPopUp(Display display) {
			super(display);
		}

		@Override
		protected String getPopupShellTitle() {
			return WorkbenchMessages.ThemeChangeWarningTitle;
		}


		@Override
		protected void createContentArea(Composite parent) {
			Label label = new Label(parent, SWT.WRAP);
			label.setText(WorkbenchMessages.ThemeChangeWarningText);
		}
	}

