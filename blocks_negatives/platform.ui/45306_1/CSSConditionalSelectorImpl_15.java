        return ((ExtendedSelector)getSimpleSelector()).match(e, pseudoE) &&
               ((ExtendedCondition)getCondition()).match(e, pseudoE);
    }

    /**
     * Fills the given set with the attribute names found in this selector.
     */
    @Override
	public void fillAttributeSet(Set attrSet) {
        ((ExtendedSelector)getSimpleSelector()).fillAttributeSet(attrSet);
        ((ExtendedCondition)getCondition()).fillAttributeSet(attrSet);
    }

    /**
     * Returns the specificity of this selector.
     */
    @Override
