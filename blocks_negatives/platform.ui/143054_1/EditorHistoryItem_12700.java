    private IEditorInput input;

    private IEditorDescriptor descriptor;

    private IMemento memento;

    /**
     * Constructs a new item.
     */
    public EditorHistoryItem(IEditorInput input, IEditorDescriptor descriptor) {
        this.input = input;
        this.descriptor = descriptor;
    }

    /**
     * Constructs a new item from a memento.
     */
    public EditorHistoryItem(IMemento memento) {
        this.memento = memento;
    }

    /**
     * Returns the editor descriptor.
     *
     * @return the editor descriptor.
     */
    public IEditorDescriptor getDescriptor() {
        return descriptor;
    }

    /**
     * Returns the editor input.
     *
     * @return the editor input.
     */
    public IEditorInput getInput() {
        return input;
    }

    /**
     * Returns whether this item has been restored from the memento.
     */
    public boolean isRestored() {
        return memento == null;
    }

    /**
     * Returns the name of this item, either from the input if restored,
     * otherwise from the memento.
     */
    public String getName() {
        if (isRestored() && getInput() != null) {
            return getInput().getName();
        } else if (memento != null) {
            String name = memento.getString(IWorkbenchConstants.TAG_NAME);
            if (name != null) {
                return name;
            }
        }
    }

    /**
     * Returns the tooltip text of this item, either from the input if restored,
     * otherwise from the memento.
     */
    public String getToolTipText() {
        if (isRestored() && getInput() != null) {
            return getInput().getToolTipText();
        } else if (memento != null) {
            String name = memento.getString(IWorkbenchConstants.TAG_TOOLTIP);
            if (name != null) {
                return name;
            }
        }
    }

    /**
     * Returns whether this item matches the given editor input.
     */
    public boolean matches(IEditorInput input) {
        if (isRestored()) {
