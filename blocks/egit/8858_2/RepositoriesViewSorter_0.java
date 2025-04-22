	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof StashedCommitNode && e2 instanceof StashedCommitNode) {
			return ((StashedCommitNode)e1).getIndex() - ((StashedCommitNode)e1).getIndex();
		} else
			return super.compare(viewer, e1, e2);
	}
