	@Override
	public List<MTrimElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<MTrimElement>(MTrimElement.class, this, MenuPackageImpl.TRIM_CONTRIBUTION__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return MUIElement.class; } };
		}
		return children;
	}

	@Override
	public void setSelectedElement(MTrimElement newSelectedElement) {
		super.setSelectedElement(newSelectedElement);
	}

