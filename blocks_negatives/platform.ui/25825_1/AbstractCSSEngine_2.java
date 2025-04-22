	/**
	 * Allow the CSS engine to skip particular elements if they are not visible.
	 * Elements need to be restyled when they become visible.
	 *
	 * @param elt
	 * @return true if the element is visible, false if not visible.
	 */
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

