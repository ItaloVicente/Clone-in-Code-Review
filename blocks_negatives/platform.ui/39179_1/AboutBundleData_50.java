    
    public AboutBundleData(Bundle bundle) {
        super(getResourceString(bundle, Constants.BUNDLE_VENDOR),
                getResourceString(bundle, Constants.BUNDLE_NAME),
                getResourceString(bundle, Constants.BUNDLE_VERSION), bundle
                        .getSymbolicName());
        
        this.bundle = bundle;
        
    }

    public int getState() {
        return bundle.getState();
    }

    /**
    * @return a string representation of the argument state. 
    *         Does not return null.
    */
    public String getStateName() {
        switch (getState()) {
        case Bundle.INSTALLED:
            return WorkbenchMessages.AboutPluginsDialog_state_installed;
        case Bundle.RESOLVED:
            return WorkbenchMessages.AboutPluginsDialog_state_resolved;
        case Bundle.STARTING:
            return WorkbenchMessages.AboutPluginsDialog_state_starting;
        case Bundle.STOPPING:
            return WorkbenchMessages.AboutPluginsDialog_state_stopping; 
        case Bundle.UNINSTALLED:
            return WorkbenchMessages.AboutPluginsDialog_state_uninstalled;
        case Bundle.ACTIVE:
            return WorkbenchMessages.AboutPluginsDialog_state_active; 
        default:
            return WorkbenchMessages.AboutPluginsDialog_state_unknown; 
        }
    }

    /**
     * A function to translate the resource tags that may be embedded in a
     * string associated with some bundle.
     * 
     * @param headerName
     *            the used to retrieve the correct string
     * @return the string or null if the string cannot be found
     */
    private static String getResourceString(Bundle bundle, String headerName) {
        String value = bundle.getHeaders().get(headerName);
        return value == null ? null : Platform.getResourceString(bundle, value);
    }

    public boolean isSignedDetermined() {
    	return isSignedDetermined;
    }

    public boolean isSigned() throws IllegalStateException {
