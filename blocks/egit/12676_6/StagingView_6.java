		site.setSelectionProvider(unstagedViewer);
	}

	private void createUnstagedToolBarComposite() {
		Composite unstagedToolbarComposite = toolkit
				.createComposite(unstagedSection);
		unstagedToolbarComposite.setBackground(null);
		RowLayout unstagedLayout = new RowLayout();
		unstagedLayout.marginHeight = 0;
		unstagedLayout.marginWidth = 0;
		unstagedLayout.marginTop = 0;
		unstagedLayout.marginBottom = 0;
		unstagedLayout.marginLeft = 0;
		unstagedLayout.marginRight = 0;
		unstagedToolbarComposite.setLayout(unstagedLayout);
		unstagedSection.setTextClient(unstagedToolbarComposite);
		unstagedExpandAllAction = new Action(UIText.UIUtils_ExpandAll,
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				unstagedViewer.expandAll();
			}
		};
		unstagedExpandAllAction.setImageDescriptor(UIIcons.EXPAND_ALL);

		unstagedCollapseAllAction = new Action(UIText.UIUtils_CollapseAll,
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				unstagedViewer.collapseAll();
			}
		};
		unstagedCollapseAllAction.setImageDescriptor(UIIcons.COLLAPSEALL);

		unstagedFlatAction = new Action(UIText.StagingView_Flat,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				unstagedPresentation = PRESENTATION_FLAT;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION_UNSTAGED,
						PRESENTATION_FLAT);
				unstagedTreeAction.setChecked(false);
				unstagedCompressedAction.setChecked(false);
				unstagedExpandAllAction.setEnabled(false);
				unstagedCollapseAllAction.setEnabled(false);
				refreshViewers();
			}
		};
		unstagedFlatAction.setImageDescriptor(UIIcons.FLAT);

		unstagedTreeAction = new Action(UIText.StagingView_Tree,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				unstagedPresentation = PRESENTATION_TREE;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION_UNSTAGED,
						PRESENTATION_TREE);
				unstagedFlatAction.setChecked(false);
				unstagedCompressedAction.setChecked(false);
				unstagedExpandAllAction.setEnabled(true);
				unstagedCollapseAllAction.setEnabled(true);
				refreshViewers();
			}
		};
		unstagedTreeAction.setImageDescriptor(UIIcons.HIERARCHY);

		unstagedCompressedAction = new Action(UIText.StagingView_Compressed,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				unstagedPresentation = PRESENTATION_COMPRESSED_FOLDERS;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION_UNSTAGED,
						PRESENTATION_COMPRESSED_FOLDERS);
				unstagedFlatAction.setChecked(false);
				unstagedTreeAction.setChecked(false);
				unstagedExpandAllAction.setEnabled(true);
				unstagedCollapseAllAction.setEnabled(true);
				refreshViewers();
			}
		};
		unstagedCompressedAction.setImageDescriptor(UIIcons.COMPRESSED);

		unstagedPresentation = getPreferenceStore().getInt(
				UIPreferences.STAGING_VIEW_PRESENTATION_UNSTAGED);
		switch (unstagedPresentation) {
		case PRESENTATION_COMPRESSED_FOLDERS:
			unstagedCompressedAction.setChecked(true);
			break;
		case PRESENTATION_FLAT:
			unstagedFlatAction.setChecked(true);
			unstagedExpandAllAction.setEnabled(false);
			unstagedCollapseAllAction.setEnabled(false);
			break;
		case PRESENTATION_TREE:
			unstagedTreeAction.setChecked(true);
			break;
		default:
			break;
		}

		ToolBarManager unstagedToolBarManager = new ToolBarManager(SWT.FLAT
				| SWT.HORIZONTAL);

		unstagedToolBarManager.add(unstagedExpandAllAction);
		unstagedToolBarManager.add(unstagedCollapseAllAction);
		unstagedToolBarManager.add(new Separator());
		unstagedToolBarManager.add(unstagedFlatAction);
		unstagedToolBarManager.add(unstagedTreeAction);
		unstagedToolBarManager.add(unstagedCompressedAction);

		unstagedToolBarManager.update(true);
		unstagedToolBarManager.createControl(unstagedToolbarComposite);
	}

	private void createStagedToolBarComposite() {
		Composite stagedToolbarComposite = toolkit
				.createComposite(stagedSection);
		stagedToolbarComposite.setBackground(null);
		RowLayout stagedLayout = new RowLayout();
		stagedLayout.marginHeight = 0;
		stagedLayout.marginWidth = 0;
		stagedLayout.marginTop = 0;
		stagedLayout.marginBottom = 0;
		stagedLayout.marginLeft = 0;
		stagedLayout.marginRight = 0;
		stagedToolbarComposite.setLayout(stagedLayout);
		stagedSection.setTextClient(stagedToolbarComposite);
		stagedExpandAllAction = new Action(UIText.UIUtils_ExpandAll,
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				stagedViewer.expandAll();
			}
		};
		stagedExpandAllAction.setImageDescriptor(UIIcons.EXPAND_ALL);

		stagedCollapseAllAction = new Action(UIText.UIUtils_CollapseAll,
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				stagedViewer.collapseAll();
			}
		};
		stagedCollapseAllAction.setImageDescriptor(UIIcons.COLLAPSEALL);

		stagedFlatAction = new Action(UIText.StagingView_Flat,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				stagedPresentation = PRESENTATION_FLAT;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION_STAGED,
						PRESENTATION_FLAT);
				stagedTreeAction.setChecked(false);
				stagedCompressedAction.setChecked(false);
				stagedExpandAllAction.setEnabled(false);
				stagedCollapseAllAction.setEnabled(false);
				refreshViewers();
			}
		};
		stagedFlatAction.setImageDescriptor(UIIcons.FLAT);

		stagedTreeAction = new Action(UIText.StagingView_Tree,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				stagedPresentation = PRESENTATION_TREE;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION_STAGED,
						PRESENTATION_TREE);
				stagedFlatAction.setChecked(false);
				stagedCompressedAction.setChecked(false);
				stagedExpandAllAction.setEnabled(true);
				stagedCollapseAllAction.setEnabled(true);
				refreshViewers();
			}
		};
		stagedTreeAction.setImageDescriptor(UIIcons.HIERARCHY);

		stagedCompressedAction = new Action(UIText.StagingView_Compressed,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				stagedPresentation = PRESENTATION_COMPRESSED_FOLDERS;
				getPreferenceStore().setValue(
						UIPreferences.STAGING_VIEW_PRESENTATION_STAGED,
						PRESENTATION_COMPRESSED_FOLDERS);
				stagedFlatAction.setChecked(false);
				stagedTreeAction.setChecked(false);
				stagedExpandAllAction.setEnabled(true);
				stagedCollapseAllAction.setEnabled(true);
				refreshViewers();
			}
		};
		stagedCompressedAction.setImageDescriptor(UIIcons.COMPRESSED);

		stagedPresentation = getPreferenceStore().getInt(
				UIPreferences.STAGING_VIEW_PRESENTATION_STAGED);
		switch (stagedPresentation) {
		case PRESENTATION_COMPRESSED_FOLDERS:
			stagedCompressedAction.setChecked(true);
			break;
		case PRESENTATION_FLAT:
			stagedFlatAction.setChecked(true);
			stagedExpandAllAction.setEnabled(false);
			stagedCollapseAllAction.setEnabled(false);
			break;
		case PRESENTATION_TREE:
			stagedTreeAction.setChecked(true);
			break;
		default:
			break;
		}

		ToolBarManager stagedToolBarManager = new ToolBarManager(SWT.FLAT
				| SWT.HORIZONTAL);

		stagedToolBarManager.add(stagedExpandAllAction);
		stagedToolBarManager.add(stagedCollapseAllAction);
		stagedToolBarManager.add(new Separator());
		stagedToolBarManager.add(stagedFlatAction);
		stagedToolBarManager.add(stagedTreeAction);
		stagedToolBarManager.add(stagedCompressedAction);

		stagedToolBarManager.update(true);
		stagedToolBarManager.createControl(stagedToolbarComposite);
	}

	public Repository getCurrentRepository() {
		return currentRepository;
