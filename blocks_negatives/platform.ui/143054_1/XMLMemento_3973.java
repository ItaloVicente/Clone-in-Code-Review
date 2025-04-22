            throw new Error(e.getMessage());
        }
    }

    /**
     * Creates a memento for the specified document and element.
     * <p>
     * Clients should use <code>createReadRoot</code> and
     * <code>createWriteRoot</code> to create the initial
     * memento on a document.
     * </p>
     *
     * @param document the document for the memento
     * @param element the element node for the memento
     */
    public XMLMemento(Document document, Element element) {
        super();
        this.factory = document;
        this.element = element;
    }
