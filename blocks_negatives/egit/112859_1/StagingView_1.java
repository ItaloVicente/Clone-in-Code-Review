						asyncExec(new Runnable() {
							@Override
							public void run() {
								updateIgnoreErrorsButtonVisibility();
								updateMessage();
								updateCommitButtons();
							}
