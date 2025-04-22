		MenuManager presentationMenu = new MenuManager(
				UIText.StagingView_Presentation);
		listPresentationAction = new Action(UIText.StagingView_List,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				presentation = Presentation.LIST;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION,
						Presentation.LIST.name());
				treePresentationAction.setChecked(false);
				compactTreePresentationAction.setChecked(false);
				setExpandCollapseActionsVisible(false);
				refreshViewers();
			}
		};
		listPresentationAction.setImageDescriptor(UIIcons.FLAT);
		presentationMenu.add(listPresentationAction);

		treePresentationAction = new Action(UIText.StagingView_Tree,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				presentation = Presentation.TREE;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION,
						Presentation.TREE.name());
				listPresentationAction.setChecked(false);
				compactTreePresentationAction.setChecked(false);
				setExpandCollapseActionsVisible(true);
				refreshViewers();
			}
		};
		treePresentationAction.setImageDescriptor(UIIcons.HIERARCHY);
		presentationMenu.add(treePresentationAction);

		compactTreePresentationAction = new Action(UIText.StagingView_CompactTree,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				presentation = Presentation.COMPACT_TREE;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION,
						Presentation.COMPACT_TREE.name());
				listPresentationAction.setChecked(false);
				treePresentationAction.setChecked(false);
				setExpandCollapseActionsVisible(true);
				refreshViewers();
			}
		};
		compactTreePresentationAction.setImageDescriptor(UIIcons.COMPACT);
		presentationMenu.add(compactTreePresentationAction);

		String presentationString = getPreferenceStore().getString(
				UIPreferences.STAGING_VIEW_PRESENTATION);
		if (presentationString.length() > 0) {
			try {
				presentation = Presentation.valueOf(presentationString);
			} catch (IllegalArgumentException e) {
			}
		}
		switch (presentation) {
		case LIST:
			listPresentationAction.setChecked(true);
			setExpandCollapseActionsVisible(false);
			break;
		case TREE:
			treePresentationAction.setChecked(true);
			break;
		case COMPACT_TREE:
			compactTreePresentationAction.setChecked(true);
			break;
		default:
			break;
		}
		dropdownMenu.add(presentationMenu);
		dropdownMenu.add(new Separator());
