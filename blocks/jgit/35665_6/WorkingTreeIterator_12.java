	void setTreeWalk(TreeWalk treeWalk) {
		this.treeWalk = treeWalk;
	}

	protected Set<Attribute> getEntryAttributes(OperationType type) {
		if (treeWalk != null && matches == treeWalk.currentHead) {
			return treeWalk.getAttributes(type);
		}
		return Collections.emptySet();
	}

