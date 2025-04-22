			quickAccessButton.setToolTipText(QuickAccessMessages.QuickAccess_TooltipDescription_Empty);
		}

		quickAccessButton.requestLayout();

	}

	public void activate(final Control previousFocusControl) {
		final QuickAccessDialog quickAccessDialog = new QuickAccessDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null);
		quickAccessDialog.getShell().addDisposeListener(e -> {
			if (previousFocusControl != null && !previousFocusControl.isDisposed()) {
				previousFocusControl.setFocus();
			} else {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (window != null) {
					window.getShell().setFocus();
