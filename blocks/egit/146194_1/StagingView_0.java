	private void createPresentationActions() {
		listPresentationAction = new Action(UIText.StagingView_List,
				IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				if (!isChecked()) {
					return;
				}
				switchToListMode();
				refreshViewers();
			}
		};
		listPresentationAction.setImageDescriptor(UIIcons.FLAT);

		treePresentationAction = new Action(UIText.StagingView_Tree,
				IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				if (!isChecked()) {
					return;
				}
				presentation = Presentation.TREE;
				setPresentation(presentation, false);
				listPresentationAction.setChecked(false);
				compactTreePresentationAction.setChecked(false);
				setExpandCollapseActionsVisible(false, isExpandAllowed(false),
						true);
				setExpandCollapseActionsVisible(true, isExpandAllowed(true),
						true);
				refreshViewers();
			}
		};
		treePresentationAction.setImageDescriptor(UIIcons.HIERARCHY);

		compactTreePresentationAction = new Action(
				UIText.StagingView_CompactTree, IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				if (!isChecked()) {
					return;
				}
				switchToCompactModeInternal(false);
				refreshViewers();
			}

		};
		compactTreePresentationAction.setImageDescriptor(UIIcons.COMPACT);

		presentationAction = new PresentationAction(getPreferenceStore(),
				listPresentationAction, treePresentationAction,
				compactTreePresentationAction);
		presentationAction.setImageDescriptor(UIIcons.FLAT);
	}

	private void initPresentation() {
		presentation = readPresentation(UIPreferences.STAGING_VIEW_PRESENTATION,
				Presentation.LIST);
		switch (presentation) {
		case LIST:
			presentationAction.setImageDescriptor(UIIcons.FLAT);
			listPresentationAction.setChecked(true);
			setExpandCollapseActionsVisible(false, false, false);
			setExpandCollapseActionsVisible(true, false, false);
			break;
		case TREE:
			presentationAction.setImageDescriptor(UIIcons.HIERARCHY);
			treePresentationAction.setChecked(true);
			break;
		case COMPACT_TREE:
			presentationAction.setImageDescriptor(UIIcons.COMPACT);
			compactTreePresentationAction.setChecked(true);
			break;
		default:
			break;
		}
		presentationAction.update();
	}

