			private void updateUiState() {
				Display.getCurrent().asyncExec(new Runnable() {
					public void run() {
						uiIsActive = Display.getCurrent().getActiveShell() != null;
					}
				});
			}

