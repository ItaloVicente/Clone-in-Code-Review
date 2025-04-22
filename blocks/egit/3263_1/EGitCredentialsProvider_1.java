	private void getCertPasswordFromUser(final CertPassword certPasswordItem) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
				CertPasswordDialog dialog = new CertPasswordDialog(shell,
						certPasswordItem.getPromptText());
				if (dialog.open() == Window.OK) {
					String certPassword = dialog.getCertPassword();
					if (certPassword != null)
						certPasswordItem.setValue(certPassword.toCharArray());
				}
			}
		});
	}

