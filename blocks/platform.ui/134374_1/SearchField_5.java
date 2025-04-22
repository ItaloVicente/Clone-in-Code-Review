				} else if (e.keyCode == SWT.F2) {
					e.doit = false;
					activated = false;
					QuickAccessDialog dialog = new QuickAccessDialog(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow(), null, txtQuickAccess.getText());
					dialog.open();
