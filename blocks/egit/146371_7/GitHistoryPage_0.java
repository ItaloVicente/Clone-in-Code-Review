		private class ShowFirstParentOnlyPrefAction extends BooleanPrefAction {

			ShowFirstParentOnlyPrefAction() {
				super(UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY,
						UIText.GitHistoryPage_ShowFirstParentOnlyMenuLabel);
				historyPage.addPropertyChangeListener(this);
			}

			@Override
			public void run() {
				Repository repo = historyPage.getCurrentRepo();
				if (repo != null) {
					historyPage.store.putValue(
							getRepositorySpecificFirstParentPreferenceString(
									repo),
							isChecked() ? IPreferenceStore.TRUE
									: IPreferenceStore.FALSE);
					if (historyPage.store.needsSaving())
						try {
							historyPage.store.save();
						} catch (IOException e) {
							Activator.handleError(e.getMessage(), e, false);
						}
				}
				super.run();
			}

			@Override
			void apply(boolean value) {
				historyPage.refresh();
			}

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				Repository repo = historyPage.getCurrentRepo();
				if (UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY
						.equals(event.getProperty())) {
					Control control = historyPage.getControl();
					if (control != null && !control.isDisposed()) {
						control.getDisplay().asyncExec(() -> {
							if (!control.isDisposed()) {
								setChecked(historyPage.isFirstParent());
								apply(isChecked());
							}
						});
					}
				}

				if (repo == null ? false
						: getRepositorySpecificFirstParentPreferenceString(repo)
								.equals(event.getProperty())) {
					boolean newValue = historyPage.store.getBoolean(
							getRepositorySpecificFirstParentPreferenceString(
									repo));
					historyPage.store.setValue(
							UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY,
							newValue);
				}

				if (P_REPOSITORY.equals(event.getProperty()) && repo != null) {
					boolean newValue = historyPage.store.getBoolean(
							getRepositorySpecificFirstParentPreferenceString(
									repo));
					historyPage.store.setValue(
							UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY,
							newValue);
				}
			}

			@Override
			public void dispose() {
				historyPage.removePropertyChangeListener(this);
				super.dispose();
			}
		}

		private void createShowFirstParentOnlyAction() {
			showFirstParentOnlyAction = new ShowFirstParentOnlyPrefAction();

			showFirstParentOnlyAction
					.setImageDescriptor(UIIcons.FIRST_PARENT_ONLY);
			showFirstParentOnlyAction
					.setToolTipText(UIText.GitHistoryPage_showFirstParentOnly);
			actionsToDispose.add(showFirstParentOnlyAction);
		}

