			BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
				@Override
				public void run() {
					IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					InstallationDialog dialog = new InstallationDialog(getShell(), workbenchWindow);
					dialog.setModalParent(AboutDialog.this);
					dialog.open();
				}
