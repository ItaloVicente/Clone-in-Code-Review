    /**
     * Tries to create the Readme file parser. If an error occurs during
     * the creation of the parser, print an error and set the parser
     * to null.
     *
     * @param element  the element to process
     */
    private void processParserElement(IConfigurationElement element) {
        try {
            parser = (IReadmeFileParser) element
                    .createExecutableExtension(IReadmeConstants.ATT_CLASS);
        } catch (CoreException e) {
            System.out
                    .println(MessageUtil
            parser = null;
        }
    }
