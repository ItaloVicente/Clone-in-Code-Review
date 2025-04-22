	@Override
	public List<MMenuElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<MMenuElement>(MMenuElement.class, this, MenuPackageImpl.MENU_CONTRIBUTION__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return MUIElement.class; } };
		}
		return children;
	}

	@Override
	public void setSelectedElement(MMenuElement newSelectedElement) {
		super.setSelectedElement(newSelectedElement);
	}

