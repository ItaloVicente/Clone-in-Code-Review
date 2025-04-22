	public AdaptableList(IAdaptable[] newChildren) {
		this(newChildren.length);
		for (IAdaptable adaptable : newChildren) {
			children.add(adaptable);
		}
	}
