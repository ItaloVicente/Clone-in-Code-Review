     * Converts the ${PARENT-COUNT-VAR} format to "VAR/../../" format
     * @param value
     * @return the converted value
     */
    private String removeParentVariable(String value) {
    	return pathVariableManager.convertToUserEditableFormat(value, false);
    }

    /**
     * Commits the temporary state to the path variable manager in response to user
     * confirmation.
     * @return boolean <code>true</code> if there were no problems.
     * @see IPathVariableManager#setValue(String, IResource, URI)
     */
    public boolean performOk() {
        try {
