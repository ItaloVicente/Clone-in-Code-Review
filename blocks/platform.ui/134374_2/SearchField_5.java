				} else if (e.keyCode == SWT.F2) {
					e.doit = false;
					activated = false;
					QuickAccessDialog dialog = new QuickAccessDialog(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
							eCommandService.createCommand(QUICK_ACCESS_COMMAND_ID, null).getCommand(),
							txtQuickAccess.getText());
					dialog.open();
