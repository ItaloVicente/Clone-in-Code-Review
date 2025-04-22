		return ((ExtendedSelector)getSimpleSelector()).match(e, pseudoE) &&
				((ExtendedCondition)getCondition()).match(e, pseudoE);
	}

	@Override
	public void fillAttributeSet(Set<String> attrSet) {
		((ExtendedSelector)getSimpleSelector()).fillAttributeSet(attrSet);
		((ExtendedCondition)getCondition()).fillAttributeSet(attrSet);
	}

	@Override
