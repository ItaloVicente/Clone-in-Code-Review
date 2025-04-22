	@Override
	public boolean match(Element e, Node[] hierarchy, int parentIndex, String pseudoE) {
		return ((ExtendedSelector)getSimpleSelector()).match(e, hierarchy, parentIndex, pseudoE) &&
				((ExtendedCondition)getCondition()).match(e, pseudoE);
	}
	
