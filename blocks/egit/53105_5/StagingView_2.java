
				int elementsCount = updateAutoExpand(unstagedViewer,
						getUnstaged(indexDiff));
				elementsCount += updateAutoExpand(stagedViewer,
						getStaged(indexDiff));

				if (elementsCount > getMaxLimitForListMode()) {
					listPresentationAction.setEnabled(false);
					if (presentation == Presentation.LIST) {
						compactTreePresentationAction.setChecked(true);
						switchToCompactModeInternal(true);
					} else {
						setExpandCollapseActionsVisible(false);
					}
				} else {
					listPresentationAction.setEnabled(true);
					boolean changed = getPreferenceStore().getBoolean(
							UIPreferences.STAGING_VIEW_PRESENTATION_CHANGED);
					if (changed) {
						listPresentationAction.setChecked(true);
						listPresentationAction.run();
					} else if (presentation != Presentation.LIST) {
						setExpandCollapseActionsVisible(true);
					}
				}

