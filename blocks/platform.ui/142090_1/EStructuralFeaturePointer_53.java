	protected int propertyIndex = UNSPECIFIED_PROPERTY;

	protected Object bean;

	public EStructuralFeaturePointer(NodePointer parent) {
		super(parent);
	}

	public int getPropertyIndex() {
		return propertyIndex;
	}

	public void setPropertyIndex(int index) {
		if (propertyIndex != index) {
			propertyIndex = index;
			setIndex(WHOLE_COLLECTION);
		}
	}

	public EObject getBean() {
		if (bean == null) {
			bean = getImmediateParentPointer().getNode();
		}
		return (EObject) bean;
	}

	@Override
