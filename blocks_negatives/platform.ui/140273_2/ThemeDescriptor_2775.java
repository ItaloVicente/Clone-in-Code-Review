    	if (name == null)
    		return getId();
        return name;
    }

    /*
     * load the name if it is not already set.
     */
    void extractName(IConfigurationElement configElement) {
        if (name == null) {
