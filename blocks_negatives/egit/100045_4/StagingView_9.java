					listPresentationAction.setEnabled(true);
					boolean changed = getPreferenceStore().getBoolean(
							UIPreferences.STAGING_VIEW_PRESENTATION_CHANGED);
					if (changed) {
						listPresentationAction.setChecked(true);
						switchToListMode();
					} else if (presentation != Presentation.LIST) {
						setExpandCollapseActionsVisible(false, true, true);
						setExpandCollapseActionsVisible(true, true, true);
					}
