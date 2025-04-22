	@Override
	public boolean match(Element e, Node[] hierarchy, int parentIndex, String pseudoE) {
		if (hierarchy == null || parentIndex >= hierarchy.length) {
			return false;
		}

		Node n = hierarchy[parentIndex];
		if (n != null && n.getNodeType() == Node.ELEMENT_NODE) {
			return ((ExtendedSelector) getAncestorSelector()).match((Element) n, hierarchy, parentIndex + 1, null)
					&& ((ExtendedSelector) getSimpleSelector()).match(e, hierarchy, parentIndex + 1, pseudoE);
		}
		return false;
	}

