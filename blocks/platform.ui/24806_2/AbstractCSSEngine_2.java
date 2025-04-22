	protected boolean isVisible(Element elt) {
		Node parentNode = elt.getParentNode();
		if (parentNode instanceof ChildVisibilityAwareElement) {
			NodeList l = ((ChildVisibilityAwareElement) parentNode)
					.getVisibleChildNodes();
			if (l != null) {
				for (int i = 0; i < l.getLength(); i++) {
					if (l.item(i) == elt) {
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

