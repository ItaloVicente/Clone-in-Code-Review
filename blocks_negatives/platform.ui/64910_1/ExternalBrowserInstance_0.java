		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					process.waitFor();
					if (process.exitValue() != 0) {
						Trace.trace(Trace.SEVERE, "Could not launch external browser"); //$NON-NLS-1$
						WebBrowserUtil.openError(NLS.bind(Messages.errorCouldNotLaunchWebBrowser, urlText));
					}
					DefaultBrowserSupport.getInstance().removeBrowser(
							ExternalBrowserInstance.this);
				} catch (Exception e) {
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
