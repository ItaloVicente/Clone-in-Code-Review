		this.files = files;
		this.type = ReplaceType.INDEX;
		schedulingRule = calcRefreshRule(files);
	}

	public DiscardChangesOperation(ReplaceType type, IResource[] files) {
		this.type = type;
