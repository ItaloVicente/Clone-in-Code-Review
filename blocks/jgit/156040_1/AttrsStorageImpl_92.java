	final Properties content = new Properties();
	final Map<String
	final Map<Class<?>

	@Override
	public AttrsStorage getAttrStorage() {
		return this;
	}

	@Override
	public <V extends AttributeView> void addAttrView(final V view) {
		viewsNameIndex.put(view.name()
		if (view instanceof ExtendedAttributeView) {
			final ExtendedAttributeView extendedView = (ExtendedAttributeView) view;
			for (Class<? extends BasicFileAttributeView> type : extendedView.viewTypes()) {
				viewsTypeIndex.put(type
			}
		} else {
			viewsTypeIndex.put(view.getClass()
		}
	}

	@Override
	public <V extends AttributeView> V getAttrView(final Class<V> type) {
		return (V) viewsTypeIndex.get(type);
	}

	@Override
	public <V extends AttributeView> V getAttrView(final String name) {
		return (V) viewsNameIndex.get(name);
	}

	@Override
	public void clear() {
		viewsNameIndex.clear();
		viewsTypeIndex.clear();
		content.clear();
	}
