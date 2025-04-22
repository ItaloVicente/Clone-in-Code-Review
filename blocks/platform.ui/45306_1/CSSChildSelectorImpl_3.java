		Node n = e.getParentNode();
		if (n != null && n.getNodeType() == Node.ELEMENT_NODE) {
			return ((ExtendedSelector)getAncestorSelector()).match((Element)n,
					null) &&
					((ExtendedSelector)getSimpleSelector()).match(e, pseudoE);
		}
		return false;
	}
