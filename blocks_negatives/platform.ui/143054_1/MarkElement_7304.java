    private String headingName;

    private IAdaptable parent;

    private int offset;

    private int numberOfLines;

    private int length;

    private ArrayList<MarkElement> children;

    /**
     * Creates a new MarkElement and stores parent element and
     * location in the text.
     *
     * @param parent  the parent of this element
     * @param heading text corresponding to the heading
     * @param offset  the offset into the Readme text
     * @param length  the length of the element
     */
    public MarkElement(IAdaptable parent, String heading, int offset, int length) {
        this.parent = parent;
        if (parent instanceof MarkElement) {
            ((MarkElement) parent).addChild(this);
        }
        this.headingName = heading;
        this.offset = offset;
        this.length = length;
    }

    /**
     * Adds a child to this element
     */
    private void addChild(MarkElement child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    @SuppressWarnings("unchecked")
