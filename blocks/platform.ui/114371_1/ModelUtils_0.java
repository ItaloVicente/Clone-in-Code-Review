	public static Collection<MApplicationElement> findElementsById(MApplicationElement element, String id) {
		ArrayList<MApplicationElement> result = new ArrayList<MApplicationElement>();
		findElementsById(element, id, result);
		return result;
	}

