	static MApplicationElement getParent(MApplicationElement element) {
		if ( (element instanceof MUIElement) && ((MUIElement)element).getCurSharedRef() != null) {
			return ((MUIElement)element).getCurSharedRef().getParent();
		} else if (element.getTransientData().get(CONTAINING_PARENT) instanceof MApplicationElement) {
			return (MApplicationElement) element.getTransientData().get(CONTAINING_PARENT);
		} else if (element instanceof EObject) {
			return (MApplicationElement) ((EObject) element).eContainer();
		}
		return null;
	}
	
