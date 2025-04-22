		toolbarManager.add(toggleTreeModeAction);
	}

	private void updateOutlineTreeMode(boolean doToggle, Action toggleAction) {
		IPreferenceStore preference = Activator.getDefault()
				.getPreferenceStore();
		String prefID = "DiffEditorOutline.compactTree"; //$NON-NLS-1$
		boolean compact = preference.getBoolean(prefID);
		if (doToggle) {
			compact = !compact;
		}
		((DiffContentProvider) getTreeViewer().getContentProvider())
				.setCompactTree(compact);
		toggleAction
				.setImageDescriptor(compact ? UIIcons.COMPACT : UIIcons.FLAT);
		preference.setValue(prefID, compact);
		getTreeViewer().setInput(getTreeViewer().getInput());
