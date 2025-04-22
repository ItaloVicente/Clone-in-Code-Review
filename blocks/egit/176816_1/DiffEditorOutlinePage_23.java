		togglePresentationAction.setImageDescriptor(UIIcons.COMPACT);
		togglePresentationAction.setToolTipText(
				UIText.DiffEditor_OutlineShowCompactTreeTooltip);
		boolean compact = preferences
				.getBoolean(UIPreferences.DIFF_OUTLINE_PRESENTATION);
		togglePresentationAction.setChecked(compact);
		updateToolbarActions();
