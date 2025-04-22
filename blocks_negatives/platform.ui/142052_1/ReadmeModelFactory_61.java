    /**
     * Parses the contents of the Readme file by looking for lines
     * that start with a number.
     *
     * @param file  the file representing the Readme file
     * @return an element collection representing the table of contents
     */
    private MarkElement[] getToc(IFile file) {
        if (registryLoaded == false)
            loadParser();
        return parser.parse(file);
    }
