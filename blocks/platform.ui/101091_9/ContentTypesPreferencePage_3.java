
	@Override
	public void dispose() {
		super.dispose();
		this.disposableEditorIcons.forEach(Image::dispose);
	}
