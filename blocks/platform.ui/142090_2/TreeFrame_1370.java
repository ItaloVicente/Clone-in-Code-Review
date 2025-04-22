	public TreeFrame(AbstractTreeViewer viewer, Object input) {
		this(viewer);
		setInput(input);
		ILabelProvider provider = (ILabelProvider) viewer.getLabelProvider();
		String name = provider.getText(input);
		if(name == null) {
