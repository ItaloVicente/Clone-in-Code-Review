							Display.getDefault().syncExec(new Runnable() {
								@Override
								public void run() {
									setErrorMessage(UIText.GitProjectsImportPage_NoProjectsMessage);
								}
							});

