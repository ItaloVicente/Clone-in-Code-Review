    /**
     * Declares a workbench image.
     * <p>
     * The workbench remembers the given image descriptor under the given name,
     * and makes the image available to plug-ins via
     * {@link IWorkbench#getSharedImages() IWorkbench.getSharedImages()}.
     * For "shared" images, the workbench remembers the image descriptor and
     * will manages the image object create from it; clients retrieve "shared"
     * images via
     * {@link org.eclipse.ui.ISharedImages#getImage ISharedImages.getImage()}.
     * For the other, "non-shared" images, the workbench remembers only the
     * image descriptor; clients retrieve the image descriptor via
     * {@link org.eclipse.ui.ISharedImages#getImageDescriptor
     * ISharedImages.getImageDescriptor()} and are entirely
     * responsible for managing the image objects they create from it.
     * (This is made confusing by the historical fact that the API interface
     *  is called "ISharedImages".)
     * </p>
     *
     * @param symbolicName the symbolic name of the image
     * @param descriptor the image descriptor
     * @param shared <code>true</code> if this is a shared image, and
     * <code>false</code> if this is not a shared image
     * @see org.eclipse.ui.ISharedImages#getImage
     * @see org.eclipse.ui.ISharedImages#getImageDescriptor
     */
    void declareImage(String symbolicName, ImageDescriptor descriptor,
            boolean shared);
