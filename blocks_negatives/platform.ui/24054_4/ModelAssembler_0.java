	private static MApplicationElement findElementById(MApplicationElement element, String id) {
		if (id == null || id.length() == 0)
			return null;
		if (id.equals(element.getElementId()))
			return element;
		EList<EObject> elements = ((EObject) element).eContents();
		for (EObject childElement : elements) {
			if (!(childElement instanceof MApplicationElement))
				continue;
			MApplicationElement result = findElementById((MApplicationElement) childElement, id);
			if (result != null)
				return result;
		}
		return null;
	}
