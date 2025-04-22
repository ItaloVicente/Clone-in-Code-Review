        this.defaultsTo = defaultsId;
        this.value = value;
        this.isEditable = isEditable;
    }

    /**
     * Create a new instance of the receiver.
     *
     * @param originalFont the original definition.  This will be used to populate
     * all fields except defaultsTo and value.  defaultsTo will always be
     * <code>null</code>.
     * @param datas the FontData[] value
     */
    public FontDefinition(FontDefinition originalFont, FontData[] datas) {
