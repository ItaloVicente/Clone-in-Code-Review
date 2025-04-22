	protected List<IAdaptable> children = new ArrayList<>();

	public AdaptableList() {
	}

	public AdaptableList(IAdaptable[] newChildren) {
		for (int i = 0; i < newChildren.length; i++) {
			children.add(newChildren[i]);
		}
	}

	public AdaptableList add(Iterator<IAdaptable> iterator) {
		while (iterator.hasNext()) {
			add(iterator.next());
		}
		return this;
	}

	public AdaptableList add(IAdaptable adaptable) {
		children.add(adaptable);
		return this;
	}

	@SuppressWarnings("unchecked")
