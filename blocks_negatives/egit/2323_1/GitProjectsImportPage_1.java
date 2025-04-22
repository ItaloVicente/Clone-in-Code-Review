
						if (files.isEmpty())
							Display.getDefault().syncExec(new Runnable() {
								public void run() {
									setErrorMessage(UIText.GitProjectsImportPage_NoProjectsMessage);
								}
							});
					} else {
						monitor.worked(60);
