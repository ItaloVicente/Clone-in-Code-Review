	private IAction createSelectAction(final IStructuredSelection selection) {
		return new Action(UIText.CommitDialog_SelectForCommit) {
			@Override
			public void run() {
				for (Object item : selection.toList())
					filesViewer.setChecked(item, true);
			}
		};
	}

