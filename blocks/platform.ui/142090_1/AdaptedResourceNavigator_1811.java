				super.updateNavigationButtons();
				updateTitle();
			}
		};
		drillDownAdapter.addNavigationActions(getViewSite().getActionBars()
				.getToolBarManager());
	}

	protected boolean isLinkingEnabled() {
		IPreferenceStore store = getPlugin().getPreferenceStore();
		return store.getBoolean(LINK_NAVIGATOR_TO_EDITOR);
	}

	protected void linkToEditor(IStructuredSelection selection) {
		if (!isLinkingEnabled()) {
