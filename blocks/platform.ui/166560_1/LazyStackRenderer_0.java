	@Override
	public void childRendered(MElementContainer<MUIElement> parentElement, MUIElement element) {
		super.childRendered(parentElement, element);

		if (parentElement.getSelectedElement() != element) {
			hideElementRecursive(element);
		}
	}

