	@Override
	public boolean match(Element e, Node[] hierarchy, int index, String pseudoE) {
		if (hierarchy == null) {
			return false;
		}

		Node n = hierarchy[index];
		if (n != null && n.getNodeType() == Node.ELEMENT_NODE) {
			return ((ExtendedSelector) getAncestorSelector()).match((Element) n, hierarchy, index + 1, null)
					&& ((ExtendedSelector) getSimpleSelector()).match(e, hierarchy, index + 1, pseudoE);
		}
		return false;
	}

