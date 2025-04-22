
	@Override
	public void addValueChangeListener(
			final CommitMessageChangeListener iCommitMessageChangeListener) {

		this.getDocument().addDocumentListener(new IDocumentListener() {
			@Override
			public void documentChanged(DocumentEvent event) {
				iCommitMessageChangeListener.commitMessageChanged();
			}

			@Override
			public void documentAboutToBeChanged(DocumentEvent event) {
			}
		});
	}

	@Override
	public int getMinHeight() {
		return getTextWidget().getLineHeight() * 3;
	}

	@Override
	public void addVerifyKeyListener(VerifyKeyListener listener) {
		getTextWidget().addVerifyKeyListener(listener);
	}

	@Override
	public Point getSize() {
		return super.getSize();
	}

	@Override
	public Control getCommentWidget() {
		return getTextWidget();
	}

	@Override
	public Control getEditorWidget() {
		return this;
	}

