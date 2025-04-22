    private Document factory;

    private Element element;

    /**
     * Creates a <code>Document</code> from the <code>Reader</code>
     * and returns a memento on the first <code>Element</code> for reading
     * the document.
     * <p>
     * Same as calling createReadRoot(reader, null)
     * </p>
     *
     * @param reader the <code>Reader</code> used to create the memento's document
     * @return a memento on the first <code>Element</code> for reading the document
     * @throws WorkbenchException if IO problems, invalid format, or no element.
     */
