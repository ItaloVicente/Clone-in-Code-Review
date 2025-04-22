			getContainer().getShell().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					runCloneOperation(getContainer(), repositoryInfo);
					cloneDestination.saveSettingsForClonedRepo();
					importRootPage.setInitialImportRoot(
							getCloneDestinationPage().getDestinationFile());
				}
