	@Override
	protected void inputBuilt(DiffContainer root) {
		super.inputBuilt(root);
		customLabels.forEach((node, name) -> setLeftLabel(node, name, false));
		customLabels.clear();
	}

	private DiffContainer buildDiffContainer(Repository repository,
