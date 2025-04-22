        }
        if (name.equals(IResourcePropertyConstants.P_DERIVED_RES)) {
            if (element.isDerived())
            	return IDEPropertiesMessages.ResourceProperty_true;
        	return IDEPropertiesMessages.ResourceProperty_false;
        }
        if (name.equals(IResourcePropertyConstants.P_LINKED_RES)) {
        	if (element.isLinked())
        		return IDEPropertiesMessages.ResourceProperty_true;
        	return IDEPropertiesMessages.ResourceProperty_false;
        }
        if (name.equals(IResourcePropertyConstants.P_LOCATION_RES)) {
            return TextProcessor.process(IDEResourceInfoUtils.getLocationText(element));
        }
        if (name.equals(IResourcePropertyConstants.P_RESOLVED_LOCATION_RES)) {
            return TextProcessor.process(IDEResourceInfoUtils.getResolvedLocationText(element));
        }
        return null;
    }

    /**
     * Returns whether the given resource is a linked resource bound
     * to a path variable.
     *
     * @param resource resource to test
     * @return boolean <code>true</code> the given resource is a linked
     * 	resource bound to a path variable. <code>false</code> the given
     * 	resource is either not a linked resource or it is not using a
     * 	path variable.
     */
    private boolean isPathVariable(IResource resource) {
        if (!resource.isLinked()) {
