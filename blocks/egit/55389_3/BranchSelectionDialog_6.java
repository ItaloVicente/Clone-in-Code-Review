		if (mergeStrategyOption) {
			helper = new MergeStrategyDialogHelper();
		}
	}

	public BranchSelectionDialog(Shell parentShell, List<T> nodes,
			String title, String message, int style) {
		this(parentShell, nodes, title, message, style, false);
