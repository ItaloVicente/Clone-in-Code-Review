		d.asyncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog.openError(null, Messages.errorDialogTitle, message);
			}
		});
