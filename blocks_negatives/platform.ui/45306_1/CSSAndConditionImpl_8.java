    /**
     * Fills the given set with the attribute names found in this selector.
     */
    @Override
	public void fillAttributeSet(Set attrSet) {
        ((ExtendedCondition)getFirstCondition()).fillAttributeSet(attrSet);
        ((ExtendedCondition)getSecondCondition()).fillAttributeSet(attrSet);
    }
