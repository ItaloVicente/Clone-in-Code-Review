	@Override
	protected Control createContents(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		parent.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				CommitMessageEditorDialog.this.commitMessage = messageArea
						.getCommitMessage();
				toolkit.dispose();
			}
		});
		return super.createContents(parent);
	}

