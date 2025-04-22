	@Override
	public boolean match(Element e, Node[] hierarchy, int parentIndex, String pseudoE) {
		if (hierarchy == null) {
			return false;
		}

		ExtendedSelector p = (ExtendedSelector) getAncestorSelector();
		if (!((ExtendedSelector) getSimpleSelector()).match(e, hierarchy, parentIndex, pseudoE)) {
			return false;
		}

		Node n;
		int length = hierarchy.length;
		for (int i = parentIndex; i < length; i++) {
			n = hierarchy[i];
			if (n != null && n.getNodeType() == Node.ELEMENT_NODE 
					&& p.match((Element) n, hierarchy, i + 1, null)) {
				return true;
			}
		}
		return false;
	}

