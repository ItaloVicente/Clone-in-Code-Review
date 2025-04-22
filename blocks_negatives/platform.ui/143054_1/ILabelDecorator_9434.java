    /**
     * Returns an image that is based on the given image,
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
     * @param image the input image to decorate, or <code>null</code> if the element has no image
     * @param element the element whose image is being decorated
     * @return the decorated image, or <code>null</code> if no decoration is to be applied
     *
     * @see org.eclipse.jface.resource.CompositeImageDescriptor
     */
    public Image decorateImage(Image image, Object element);
