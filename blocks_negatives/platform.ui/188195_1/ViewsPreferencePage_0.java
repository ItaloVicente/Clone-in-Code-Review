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

			Link link = new Link(parent, SWT.WRAP);
			link.setText(WorkbenchMessages.ThemeChangeWarningHyperlinkedText);
			link.addSelectionListener(widgetSelectedAdapter(e -> PlatformUI.getWorkbench().restart(true)));
		}
	}

