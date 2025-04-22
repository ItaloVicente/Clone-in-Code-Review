    private String pluginId;

    private String rawValue;

    boolean isEditable;

    private RGB parsedValue;

    /**
     * Create a new instance of the receiver.
     *
     * @param label the label for this definition
     * @param id the identifier for this definition
     * @param defaultsTo the id of a definition that this definition will
     * 		default to.
     * @param value the default value of this definition, either in the form
     * rrr,ggg,bbb or the name of an SWT color constant.
     * @param description the description for this definition.
     * @param pluginId the identifier of the plugin that contributed this
     * 		definition.
     */
    public ColorDefinition(String label, String id, String defaultsTo,
            String value, String categoryId, boolean isEditable,
            String description, String pluginId) {
