    /**
     * Returns the presentation factory with the given id, or <code>null</code> if not found.
     * @param targetID The id of the presentation factory to use.
     * @return AbstractPresentationFactory or <code>null</code>
     * if not factory matches that id.
     */
    public AbstractPresentationFactory getPresentationFactory(String targetID) {
        Object o = createExtension(
                IWorkbenchRegistryConstants.PL_PRESENTATION_FACTORIES,
                "factory", targetID); //$NON-NLS-1$
        if (o instanceof AbstractPresentationFactory) {
            return (AbstractPresentationFactory) o;
        }
        WorkbenchPlugin
        return null;
    }

