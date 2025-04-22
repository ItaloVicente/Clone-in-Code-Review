	public List<IPreferenceNode> getElements(int order) {
		Assert.isTrue(order == PRE_ORDER || order == POST_ORDER,
				"invalid traversal order");//$NON-NLS-1$
		ArrayList<IPreferenceNode> sequence = new ArrayList<>();
		IPreferenceNode[] subnodes = getRoot().getSubNodes();
		for (IPreferenceNode subnode : subnodes) {
