	public void activate(final Control previousFocusControl) {
		if (quickAccessDialog == null) {
			quickAccessDialog = new QuickAccessDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
					txtQuickAccess, null);
			quickAccessDialog.getShell().addDisposeListener(e -> {
				quickAccessDialog = null;
				if (previousFocusControl != null && !previousFocusControl.isDisposed()) {
					previousFocusControl.setFocus();
				} else {
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					if (window != null) {
						window.getShell().setFocus();
