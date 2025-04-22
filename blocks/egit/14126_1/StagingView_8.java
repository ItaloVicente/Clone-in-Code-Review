		MenuManager presentationMenu = new MenuManager(
				UIText.StagingView_Presentation);
		flatAction = new Action(UIText.StagingView_Flat,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				presentation = PRESENTATION_FLAT;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION,
						PRESENTATION_FLAT);
				treeAction.setChecked(false);
				compressedAction.setChecked(false);
				unstagedExpandAllAction.setEnabled(false);
				unstagedCollapseAllAction.setEnabled(false);
				stagedExpandAllAction.setEnabled(false);
				stagedCollapseAllAction.setEnabled(false);
				refreshViewers();
			}
		};
		flatAction.setImageDescriptor(UIIcons.FLAT);
		presentationMenu.add(flatAction);

		treeAction = new Action(UIText.StagingView_Tree,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				presentation = PRESENTATION_TREE;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION,
						PRESENTATION_TREE);
				flatAction.setChecked(false);
				compressedAction.setChecked(false);
				unstagedExpandAllAction.setEnabled(true);
				unstagedCollapseAllAction.setEnabled(true);
				stagedExpandAllAction.setEnabled(true);
				stagedCollapseAllAction.setEnabled(true);
				refreshViewers();
			}
		};
		treeAction.setImageDescriptor(UIIcons.HIERARCHY);
		presentationMenu.add(treeAction);

		compressedAction = new Action(UIText.StagingView_Compressed,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				presentation = PRESENTATION_COMPRESSED_FOLDERS;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION,
						PRESENTATION_COMPRESSED_FOLDERS);
				flatAction.setChecked(false);
				treeAction.setChecked(false);
				unstagedExpandAllAction.setEnabled(true);
				unstagedCollapseAllAction.setEnabled(true);
				stagedExpandAllAction.setEnabled(true);
				stagedCollapseAllAction.setEnabled(true);
				refreshViewers();
			}
		};
		compressedAction.setImageDescriptor(UIIcons.COMPRESSED);
		presentationMenu.add(compressedAction);

		presentation = getPreferenceStore().getInt(
				UIPreferences.STAGING_VIEW_PRESENTATION);
		switch (presentation) {
		case PRESENTATION_COMPRESSED_FOLDERS:
			compressedAction.setChecked(true);
			break;
		case PRESENTATION_FLAT:
			flatAction.setChecked(true);
			unstagedExpandAllAction.setEnabled(false);
			unstagedCollapseAllAction.setEnabled(false);
			stagedExpandAllAction.setEnabled(false);
			stagedCollapseAllAction.setEnabled(false);
			break;
		case PRESENTATION_TREE:
			treeAction.setChecked(true);
			break;
		default:
			break;
		}
		dropdownMenu.add(presentationMenu);
		dropdownMenu.add(new Separator());
