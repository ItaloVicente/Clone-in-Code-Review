
		private class ConfigureFilterAction extends Action
				implements IWorkbenchAction, IPropertyChangeListener {

			private GitHistoryRefFilterConfigurationDialog dialog;

			ConfigureFilterAction() {
				super(UIText.GitHistoryPage_configureFilters);
				historyPage.addPropertyChangeListener(this);
			}

			@Override
			public void run() {
				if (historyPage.getCurrentRepo() == null) {
					return;
				}

				dialog = new GitHistoryRefFilterConfigurationDialog(
						historyPage.getSite().getWorkbenchWindow().getShell(),
						historyPage.getCurrentRepo(), historyPage.helper);
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
					if (historyPage.getCurrentRepo() == null) {
						this.setEnabled(false);
					} else {
						this.setEnabled(true);
					}
				}

			}

		}

		private void createConfigureFiltersAction() {
			configureFiltersAction = new ConfigureFilterAction();
			actionsToDispose.add(configureFiltersAction);
		}
