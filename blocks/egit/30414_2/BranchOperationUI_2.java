			final Boolean[] dialogResult = new Boolean[1];
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					dialogResult[0] = showContinueDialogInUI(store,
							launchConfiguration);
				}
			});
			return dialogResult[0].booleanValue();
