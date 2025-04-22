	@Override
	public void hideChild(MElementContainer<MUIElement> parentElement, MUIElement child) {
		super.hideChild(parentElement, child);

		hideElementRecursive(child);
	}

