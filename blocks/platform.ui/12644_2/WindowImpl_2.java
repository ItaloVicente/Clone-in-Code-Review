	@Override
	public List<MWindowElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<MWindowElement>(MWindowElement.class, this, BasicPackageImpl.WINDOW__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return MUIElement.class; } };
		}
		return children;
	}

	@Override
	public void setSelectedElement(MWindowElement newSelectedElement) {
		super.setSelectedElement(newSelectedElement);
	}

