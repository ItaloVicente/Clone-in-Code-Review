								getShell().getDisplay().asyncExec(
										new Runnable() {
											@Override
											public void run() {
												updateUi();
											}
										});
								if (monitor.isCanceled())
