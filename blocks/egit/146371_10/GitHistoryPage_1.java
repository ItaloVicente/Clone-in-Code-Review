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
				final String prefKey = UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY_DEFAULT;
				Repository repo = historyPage.getCurrentRepo();
				if (repo != null) {
					String repoSepcificKey = getRepoSpecificKey(repo, prefKey);
					boolean newBoolean = isChecked();
					if (newBoolean == historyPage.store.getBoolean(prefKey)) {
						historyPage.store.setToDefault(repoSepcificKey);
					} else {
						String newValue = newBoolean ? IPreferenceStore.TRUE
								: IPreferenceStore.FALSE;
						historyPage.store.putValue(repoSepcificKey, newValue);
					}

					historyPage.saveStoreIfNeeded();
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

				final String prefKey = UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY_DEFAULT;

				if (repo == null) {
					if (prefKey.equals(event.getProperty())) {
						applyNewState(historyPage.store.getBoolean(prefKey));
					}
					return;
				}

				String repoSpecificKey = getRepoSpecificKey(repo, prefKey);

				if (prefKey.equals(event.getProperty())) {
					if (!historyPage.store.contains(repoSpecificKey)) {
						applyNewState(historyPage.store.getBoolean(prefKey));
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

