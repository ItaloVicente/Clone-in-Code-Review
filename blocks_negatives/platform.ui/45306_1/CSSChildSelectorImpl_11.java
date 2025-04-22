    /**
     * Fills the given set with the attribute names found in this selector.
     */
    @Override
	public void fillAttributeSet(Set attrSet) {
        ((ExtendedSelector)getAncestorSelector()).fillAttributeSet(attrSet);
        ((ExtendedSelector)getSimpleSelector()).fillAttributeSet(attrSet);
    }
