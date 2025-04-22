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
				boolean globalFirstParentPreferenceChanged = UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY
						.equals(event.getProperty());
				boolean repoSpecificFirstParentPreferencesChanged = (repo == null
						? false
						: getRepositorySpecificFirstParentPreferenceString(repo)
								.equals(event.getProperty()));
				boolean repoChanged = P_REPOSITORY.equals(event.getProperty());
				if (globalFirstParentPreferenceChanged
						|| repoSpecificFirstParentPreferencesChanged
						|| repoChanged) {
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

