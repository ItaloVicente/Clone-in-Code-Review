
		private void createFilterBranchActions() {
			filterBranchActions = new ArrayList<>();

			filterBranchActions.add(new BooleanPrefAction(
					"resourcehistory_filter_branch_current", "currentBranch", //$NON-NLS-1$ //$NON-NLS-2$
					"filter_branch_default_current") { //$NON-NLS-1$

				@Override
				void apply(boolean value) {
				}
			});

			filterBranchActions.add(new BooleanPrefAction(
					"resourcehistory_filter_branch_all", "all", //$NON-NLS-1$ //$NON-NLS-2$
					"filter_branch_default_all") { //$NON-NLS-1$

				@Override
				void apply(boolean value) {
				}
			});

			filterBranchActions.add(new BooleanPrefAction(
					"resourcehistory_filter_branch_test1", "feature/*") { //$NON-NLS-1$ //$NON-NLS-2$

				@Override
				void apply(boolean value) {
				}
			});
			filterBranchActions.add(new BooleanPrefAction(
					"resourcehistory_filter_branch_test2", "bugfix/*") { //$NON-NLS-1$ //$NON-NLS-2$

				@Override
				void apply(boolean value) {
				}
			});
			filterBranchActions.forEach(actionsToDispose::add);
		}

		private class ConfigureFilterAction extends Action
				implements IWorkbenchAction, IPropertyChangeListener {

			private GitHistoryRefFilterConfigurationDialog dialog;

			ConfigureFilterAction() {
				super(UIText.GitHistoryPage_configureFilters);
				historyPage.addPropertyChangeListener(this);
			}

			@Override
			public void run() {
				if (historyPage.getCurrentRepo() == null)
					return;

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
				if (dialog != null)
					dialog.close();
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
