					dialog.create();
					dialog.getShell().addShellListener(new ShellAdapter() {
						public void shellActivated(org.eclipse.swt.events.ShellEvent e) {
							dialogs.remove(dialog);
							dialogs.add(dialog);
						}
					});
