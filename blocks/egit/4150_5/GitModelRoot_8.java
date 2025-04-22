		if (children == null)
			children = getChildrenImpl();

		return children;
	}

	public void dispose() {
		for (GitModelObject child : children)
			child.dispose();

		gsds.dispose();
	}

	private GitModelObject[] getChildrenImpl() {
