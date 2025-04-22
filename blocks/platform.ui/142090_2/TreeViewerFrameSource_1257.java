	protected Frame getParentFrame(int flags) {
		Object input = viewer.getInput();
		ITreeContentProvider provider = (ITreeContentProvider) viewer
				.getContentProvider();
		Object parent = provider.getParent(input);
		if (parent == null) {
			return null;
		}
