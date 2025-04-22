				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						if (dialog == null || dialog.getShell().isDisposed()) {
							dialog = new SafeRunnableDialog(status);
							dialog.create();
							dialog.getShell().addDisposeListener(
									new DisposeListener() {
										@Override
										public void widgetDisposed(
												DisposeEvent e) {
											dialog = null;
										}
									});
							dialog.open();
						} else {
							dialog.addStatus(status);
							dialog.refresh();
						}
