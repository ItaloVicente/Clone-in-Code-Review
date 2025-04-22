		private class ShowFirstParentOnlyPrefAction extends BooleanPrefAction {

			ShowFirstParentOnlyPrefAction() {
				super(UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY,
						UIText.GitHistoryPage_ShowFirstParentOnlyMenuLabel);
				historyPage.addPropertyChangeListener(this);
			}

			@Override
			void apply(boolean value) {
				historyPage.store.setValue(
						getRepositorySpecificFirstParentPreferenceString(
								historyPage.getCurrentRepo()),
						value);
				historyPage.refresh();
			}

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY
						.equals(event.getProperty())
						|| getRepositorySpecificFirstParentPreferenceString(
								historyPage.getCurrentRepo())
										.equals(event.getProperty())
						|| P_REPOSITORY.equals(event.getProperty())) {
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
		}

		private void createShowFirstParentOnlyAction() {
			showFirstParentOnlyAction = new ShowFirstParentOnlyPrefAction();

			showFirstParentOnlyAction
					.setImageDescriptor(UIIcons.FIRST_PARENT_ONLY);
			showFirstParentOnlyAction
					.setToolTipText(UIText.GitHistoryPage_showFirstParentOnly);
			actionsToDispose.add(showFirstParentOnlyAction);
		}

