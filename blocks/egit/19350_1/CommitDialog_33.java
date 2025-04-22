
				manager.add(createAddAction(selection));
			}
		};
	}

	private Action createCompareAction(final CommitItem commitItem) {
		return new Action(UIText.CommitDialog_CompareWithHeadRevision) {
			@Override
			public void run() {
				compare(commitItem);
			}
		};
	}

	private Action createAddAction(final IStructuredSelection selection) {
		return new Action(UIText.CommitDialog_AddFileOnDiskToIndex) {
			public void run() {
