	@Override
	public boolean match(Element e, Node[] hiearchy, int parentIndex, String pseudoE) {
		Node n = e;
		if (!((ExtendedSelector) getSiblingSelector()).match(e, hiearchy, parentIndex, pseudoE)) {
			return false;
		}

		while ((n = n.getPreviousSibling()) != null && n.getNodeType() != Node.ELEMENT_NODE) {
			;
		}

		if (n == null) {
			return false;
		}

		return ((ExtendedSelector) getSelector()).match((Element) n, hiearchy, parentIndex, null);
	}

