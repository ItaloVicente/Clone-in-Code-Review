        Image image = JFaceResources.getResources().createImageWithDefault(imageDescriptor);
        titleImage = image;
    }

    /**
     * Sets or clears the title image of this part.
     *
     * @param titleImage
     *            the title image, or <code>null</code> to clear
     */
    protected void setTitleImage(Image titleImage) {
        Assert.isTrue(titleImage == null || !titleImage.isDisposed());
        if (this.titleImage == titleImage) {
