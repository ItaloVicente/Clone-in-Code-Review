	@Override
	public MElementContainer<? extends MUIElement> getParentForPart(MPart part) {
		Objects.requireNonNull(part);
		MUIElement element = part;
		if (element.getCurSharedRef()!=null) {
			element = element.getCurSharedRef();
		}
		return element.getParent();
	}

