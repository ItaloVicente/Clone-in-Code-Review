    /**
     * Returns the label decorator which applies the decorations from all
     * enabled decorators.
     * Views which allow decoration of their elements should use this
     * label decorator.
     * This decorator should be disposed when it is no longer referenced
     * by the caller or the images created within it may be kept until
     * {@link JFaceResources#getResources()} is disposed.
     *
     * @return {@link ILabelDecorator}
     * @see DecoratingLabelProvider
     * @see IBaseLabelProvider#dispose()
     */
    ILabelDecorator getLabelDecorator();
