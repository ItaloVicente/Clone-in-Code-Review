
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
