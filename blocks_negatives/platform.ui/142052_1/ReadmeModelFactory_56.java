    /**
     * Returns the content outline for the given Readme file.
     *
     * @param adaptable  the element for which to return the content outline
     * @return the content outline for the argument
     */
    public AdaptableList getContentOutline(IAdaptable adaptable) {
        return new AdaptableList(getToc((IFile) adaptable));
    }
