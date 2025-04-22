    /**
     * Add the currently-specified extensions to result.
     * @param result
     */
    private void addUserDefinedEntries(List result) {
        StringTokenizer tokenizer = new StringTokenizer(userDefinedText
                .getText(), TYPE_DELIMITER);
        while (tokenizer.hasMoreTokens()) {
            String currentExtension = tokenizer.nextToken().trim();
