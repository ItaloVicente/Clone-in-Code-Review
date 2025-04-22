    /**
     * Constructs a frame for the specified tree viewer.
     * The frame's input element is set to the specified input element.
     * The frame's name and tool tip text are set to the text for the input
     * element, as provided by the viewer's label provider.
     *
     * @param viewer the tree viewer
     * @param input the input element
     */
    public TreeFrame(AbstractTreeViewer viewer, Object input) {
        this(viewer);
        setInput(input);
        ILabelProvider provider = (ILabelProvider) viewer.getLabelProvider();
        String name = provider.getText(input);
        if(name == null) {
