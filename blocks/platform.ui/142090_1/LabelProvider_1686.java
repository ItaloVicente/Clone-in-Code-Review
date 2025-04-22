	private boolean areDifferentTypes(IStructuredSelection structuredSelection) {
		if (structuredSelection.size() == 1) {
			return false;
		}
		Iterator i = structuredSelection.iterator();
		Element element = (Element) ((TreeNode) i.next()).getValue();
		for (; i.hasNext();) {
			Element next = (Element) ((TreeNode) i.next()).getValue();
			if (next.getClass() != element.getClass()) {
				return true;
			}
		}
