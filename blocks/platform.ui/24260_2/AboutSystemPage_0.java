			public IStatus run(IProgressMonitor monitor) {
				final String info = ConfigurationInfo.getSystemSummary();
				if (!text.isDisposed()) {
					text.getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (!text.isDisposed()) {
								text.setText(info);
							}
						}
					});
				}
