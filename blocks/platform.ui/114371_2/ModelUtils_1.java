		Collection<MApplicationElement> result = findElementsById(element, id);
		return result.size() == 1 ? result.iterator().next() : null;
	}

	public static MApplicationElement findElementByTypeAndId(MApplicationElement root, EClass type, String id) {
		if (type == null)
			throw new NullPointerException("Type argument should not be null");
		Collection<MApplicationElement> result = findElementsById(root, id);
		for (Iterator<MApplicationElement> i = result.iterator(); i.hasNext();) {
			MApplicationElement element = i.next();
			if (element instanceof EObject) {
				EObject eObject = (EObject) element;
				if (type.isSuperTypeOf(eObject.eClass())) {
					continue;
				}
			}
			i.remove();
		}
		return result.size() == 1 ? result.iterator().next() : null;
	}

	private static void findElementsById(MApplicationElement element, String id,
			Collection<MApplicationElement> output) {
