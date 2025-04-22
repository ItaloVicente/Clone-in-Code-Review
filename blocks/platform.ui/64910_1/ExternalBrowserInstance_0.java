
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						process.waitFor();
						if (reportNonZeroExitValue[0] && process.exitValue() != 0) {
							Trace.trace(Trace.SEVERE,
									"External browser returned non-zero status: " + process.exitValue()); //$NON-NLS-1$
							WebBrowserUtil.openError(NLS.bind(Messages.errorCouldNotLaunchWebBrowser, urlText));
						}
						DefaultBrowserSupport.getInstance().removeBrowser(ExternalBrowserInstance.this);
					} catch (Exception e) {
					}
				}
			};
			thread.setDaemon(true);
			thread.start();
