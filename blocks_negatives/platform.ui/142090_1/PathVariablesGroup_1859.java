     * Creates a new PathVariablesGroup.
     *
     * @param multiSelect create a multi select tree
     * @param variableType the type of variables that are displayed in
     * 	the widget group. <code>IResource.FILE</code> and/or <code>IResource.FOLDER</code>
     * 	logically ORed together.
     */
    public PathVariablesGroup(boolean multiSelect, int variableType) {
        this.multiSelect = multiSelect;
        this.variableType = variableType;
        pathVariableManager = ResourcesPlugin.getWorkspace()
                .getPathVariableManager();
