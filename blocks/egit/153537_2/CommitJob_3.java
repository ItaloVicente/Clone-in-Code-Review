						.getDisplay().getActiveShell(),
						createTitle(),
						createMessage());
			}
		});
	}

        private void showHookOutput(String hookOutput) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				MessageDialog.openInformation(
						PlatformUI.getWorkbench().getDisplay().getActiveShell(),
						UIText.CommitJob_HookOutput,
						hookOutput);
