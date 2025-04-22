	@Override
	public GitModelObject[] getChildren() {
		if (children == null)
			getChildrenImpl();

		return children;
	}

