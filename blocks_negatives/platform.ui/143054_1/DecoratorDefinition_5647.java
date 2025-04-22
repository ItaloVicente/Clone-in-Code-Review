    	return Boolean.valueOf(definingElement.getAttribute(ATT_ADAPTABLE)).booleanValue();
    }

    /**
     * Gets the id.
     * @return Returns a String
     */
    public String getId() {
        return id;
    }

    /**
     * Return the default value for this type - this value
     * is the value read from the element description.
     *
     * @return the default value for this type - this value
     * is the value read from the element description
     */
    public boolean getDefaultValue() {
        return defaultEnabled;
    }

    /**
     * Returns the enablement.
     * @return ActionExpression
     */
    protected ActionExpression getEnablement() {
    	if (!hasReadEnablement) {
    		hasReadEnablement = true;
    		initializeEnablement();
    	}
        return enablement;
    }

    /**
     * Initialize the enablement expression for this decorator
     */
    protected void initializeEnablement() {
