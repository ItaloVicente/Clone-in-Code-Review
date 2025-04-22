				Runnable runnable = () -> {
					if (dialog == null || dialog.getShell().isDisposed()) {
						dialog = new SafeRunnableDialog(status);
						dialog.create();
						dialog.getShell().addDisposeListener(
								e -> dialog = null);
						dialog.open();
					} else {
						dialog.addStatus(status);
						dialog.refresh();
