    /**
     * Returns a text label that is based on the given text label,
     * but decorated with additional information relating to the state
     * of the provided element.
     *
     * Text and image decoration updates can occur as a result of other updates
     * within the workbench including deferred decoration by background processes.
     * Clients should handle labelProviderChangedEvents for the given element to get
     * the complete decoration.
     * @see LabelProviderChangedEvent
     * @see IBaseLabelProvider#addListener
     *
     * @param text the input text label to decorate
     * @param element the element whose image is being decorated
     * @return the decorated text label, or <code>null</code> if no decoration is to be applied
     */
    public String decorateText(String text, Object element);
