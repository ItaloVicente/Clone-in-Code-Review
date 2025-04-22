
		private class ConfigureFilterAction extends Action
				implements IWorkbenchAction, IPropertyChangeListener {

			private GitHistoryRefFilterConfigurationDialog dialog;

			private RefFilterHelper helper;

			ConfigureFilterAction() {
				super(UIText.GitHistoryPage_configureFilters);
				historyPage.addPropertyChangeListener(this);
				Repository currentRepo = historyPage.getCurrentRepo();
				if (currentRepo != null) {
					helper = new RefFilterHelper(currentRepo);
				}
			}

			@Override
			public void run() {
				if (historyPage.getCurrentRepo() == null) {
					return;
				}

				dialog = new GitHistoryRefFilterConfigurationDialog(
						historyPage.getSite().getWorkbenchWindow().getShell(),
						historyPage.getCurrentRepo(), helper);
				if (dialog.open() == Window.OK) {
					historyPage.refresh(historyPage.selectedCommit());
				}
			}

			@Override
			public void dispose() {
				historyPage.removePropertyChangeListener(this);
				if (dialog != null) {
					dialog.close();
				}
			}

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (P_REPOSITORY.equals(event.getProperty())) {
					Repository currentRepo = historyPage.getCurrentRepo();
					if (currentRepo == null) {
						this.setEnabled(false);
						helper = null;
					} else {
						this.setEnabled(true);
						helper = new RefFilterHelper(currentRepo);
					}
				}

			}
		}

		private void createConfigureFiltersAction() {
			configureFiltersAction = new ConfigureFilterAction();
			actionsToDispose.add(configureFiltersAction);
		}
