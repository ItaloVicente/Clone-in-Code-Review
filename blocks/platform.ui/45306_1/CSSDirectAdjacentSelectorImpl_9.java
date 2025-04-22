		Node n = e;
		if (!((ExtendedSelector)getSiblingSelector()).match(e, pseudoE)) {
			return false;
		}
		while ((n = n.getPreviousSibling()) != null && n.getNodeType() != Node.ELEMENT_NODE) {
			;
		}

		if (n == null) {
			return false;
		}

		return ((ExtendedSelector)getSelector()).match((Element)n, null);
	}

	@Override
	public void fillAttributeSet(Set<String> attrSet) {
		((ExtendedSelector)getSelector()).fillAttributeSet(attrSet);
		((ExtendedSelector)getSiblingSelector()).fillAttributeSet(attrSet);
	}

	@Override
