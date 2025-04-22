				presentation = Presentation.COMPACT_TREE;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION,
						Presentation.COMPACT_TREE.name());
				listPresentationAction.setChecked(false);
				treePresentationAction.setChecked(false);
				setExpandCollapseActionsVisible(true);
