	@Override
	public Viewer createDiffViewer(Composite parent) {
		GitDiffTreeViewer viewer = new GitDiffTreeViewer(parent, getContainer(),
				getCompareConfiguration());
		viewer.setComparator(new ViewerComparator(CMP) {

			@Override
			public int category(Object element) {
				if (element instanceof FolderNode) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		IAction compareAction = new CompareWithEachOtherAction(viewer,
				UIText.GitMergeEditorInput_CompareWithEachOtherMenuLabel,
				UIIcons.ELCL16_COMPARE_VIEW);
		viewer.setActions(Collections.singleton(compareAction));
		registerAction(compareAction, CompareWithEachOtherAction.COMMAND_ID);
		return viewer;
	}

