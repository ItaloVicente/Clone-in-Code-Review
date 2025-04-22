		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog
						.openError(
								null,
								WorkbenchMessages.ProductInfoDialog_errorTitle,
								WorkbenchMessages.ProductInfoDialog_unableToOpenWebBrowser);
			}
		});
