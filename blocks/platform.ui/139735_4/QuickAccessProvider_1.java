	public QuickAccessElement getElementForId(String id) {
		if (id == null) {
			return null;
		}
		if (sortedElements != null) {
			for (QuickAccessElement element : sortedElements) {
				if (id.equals(element.getId())) {
					return element;
				}
			}
		}
		return null;
	}
