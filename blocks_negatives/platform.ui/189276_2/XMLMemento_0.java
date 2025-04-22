    /**
     * Creates a <code>Document</code> from the <code>Reader</code>
     * and returns a memento on the first <code>Element</code> for reading
     * the document.
     *
     * @param reader the <code>Reader</code> used to create the memento's document
     * @param baseDir the directory used to resolve relative file names
     * 		in the XML document. This directory must exist and include the
     * 		trailing separator. The directory format, including the separators,
     * 		must be valid for the platform. Can be <code>null</code> if not
     * 		needed.
     * @return a memento on the first <code>Element</code> for reading the document
     * @throws WorkbenchException if IO problems, invalid format, or no element.
     */
