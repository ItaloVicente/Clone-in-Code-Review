    /**
     * Return whether the decorator is applicable to the given element
     * @param element the element to be decorated
     * @return whether the decorator w=should be applied to the element
     */
    public boolean isEnabledFor(Object element) {
    	if(isEnabled()){
    		ActionExpression expression =  getEnablement();
    		if(expression != null) {
