		private class ShowFirstParentOnlyPrefAction extends Action
				implements IPropertyChangeListener, IWorkbenchAction {

			ShowFirstParentOnlyPrefAction() {
				super(UIText.GitHistoryPage_ShowFirstParentOnlyMenuLabel);
				historyPage.addPropertyChangeListener(this);
				historyPage.store.addPropertyChangeListener(this);
				setChecked(historyPage.isShowFirstParentOnly());
			}

			@Override
			public void run() {
				Repository repo = historyPage.getCurrentRepo();
				if (repo != null) {
					String repoSepcificKey = getRepositorySpecificFirstParentPreferenceKey(
							repo);
					String newValue = isChecked() ? IPreferenceStore.TRUE : IPreferenceStore.FALSE;
					historyPage.store.putValue(repoSepcificKey, newValue);
					if (historyPage.store.needsSaving())
						try {
							historyPage.store.save();
						} catch (IOException e) {
							Activator.handleError(e.getMessage(), e, false);
						}
				}
				historyPage.refresh();
			}

			private void applyNewState(boolean newState) {

				Control control = historyPage.getControl();
				if (control != null && !control.isDisposed()) {
					control.getDisplay().asyncExec(() -> {
						if (!control.isDisposed()) {
							setChecked(newState);
						}
					});
				}
				historyPage.refresh();
			}

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				Repository repo = historyPage.getCurrentRepo();

				if (repo == null) {
					if (UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY_DEFAULT
							.equals(event.getProperty())) {
						applyNewState(historyPage.store.getBoolean(
								UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY_DEFAULT));
					}
					return;
				}

				String repoSepcificKey = getRepositorySpecificFirstParentPreferenceKey(
						repo);

				if (UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY_DEFAULT
						.equals(event.getProperty())) {
					if (!historyPage.store.contains(repoSepcificKey)) {
						applyNewState(historyPage.store.getBoolean(
								UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY_DEFAULT));
					}
				}

				if (P_REPOSITORY.equals(event.getProperty())) {
					applyNewState(historyPage.isShowFirstParentOnly());
				}
			}

			@Override
			public void dispose() {
				historyPage.removePropertyChangeListener(this);
				historyPage.store.removePropertyChangeListener(this);
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

