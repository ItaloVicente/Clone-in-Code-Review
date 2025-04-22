     * Create a new instance of an editor descriptor. Limited
     * to internal framework calls.
     * @param element
     * @param id2
     */
    /* package */EditorDescriptor(String id2, IConfigurationElement element) {
        setID(id2);
        setConfigurationElement(element);
    }


