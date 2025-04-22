		final CommitAction action = new CommitAction() {
			@Override
			protected IStructuredSelection getSelection() {
				return new StructuredSelection(getSyncInfoSet().getSyncInfos());
			}
		};
