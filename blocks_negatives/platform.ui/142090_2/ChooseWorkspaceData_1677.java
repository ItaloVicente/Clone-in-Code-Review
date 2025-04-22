        Integer version = protocolMemento.getInteger(XML.VERSION);
        return version != null && version.intValue() == PERS_ENCODING_VERSION;
    }

    /**
     * The workspace data is stored in the well known file pointed to by the result
     * of this method.
     * @param create If the directory and file does not exist this parameter
     *               controls whether it will be created.
     * @return An url to the file and null if it does not exist or could not
     *         be created.
     */
    private static URL getPersistenceUrl(URL baseUrl, boolean create) {
        if (baseUrl == null) {
