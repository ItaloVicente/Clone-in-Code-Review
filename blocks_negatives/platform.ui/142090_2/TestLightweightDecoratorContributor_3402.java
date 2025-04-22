        while (iterator.hasNext()) {
            LabelProviderChangedEvent event = new LabelProviderChangedEvent(
                    this, element);
            iterator.next()
                    .labelProviderChanged(event);
        }
    }

    public ImageDescriptor getOverlay(Object element) {
        Assert.isTrue(element instanceof IResource);
        if (descriptor == null) {
