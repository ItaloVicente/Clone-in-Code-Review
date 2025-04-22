	@Override
	public List<MPartSashContainerElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<MPartSashContainerElement>(MPartSashContainerElement.class, this, AdvancedPackageImpl.PERSPECTIVE__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return MUIElement.class; } };
		}
		return children;
	}

	@Override
	public void setSelectedElement(MPartSashContainerElement newSelectedElement) {
		super.setSelectedElement(newSelectedElement);
	}

