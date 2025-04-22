    /**
     * Creates a new record for the given extension id and data.
     *
     * @param extensionId the extension id
     * @param data the data to transfer
     */
    public PluginTransferData(String extensionId, byte[] data) {
        this.extensionName = extensionId;
        this.transferData = data;
    }
