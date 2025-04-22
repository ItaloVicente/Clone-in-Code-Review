        return typesToExportField.getText();
    }

    /**
     * Returns the resource extensions currently specified to be exported.
     *
     * @return the resource extensions currently specified to be exported (element
     *   type: <code>String</code>)
     */
    protected List getTypesToExport() {
        List result = new ArrayList();
        StringTokenizer tokenizer = new StringTokenizer(typesToExportField
                .getText(), TYPE_DELIMITER);

        while (tokenizer.hasMoreTokens()) {
            String currentExtension = tokenizer.nextToken().trim();
