        declareImage(ISharedImages.IMG_TOOL_UNDO_HOVER, PATH_ETOOL
        		+ "undo_edit.png", true); //$NON-NLS-1$
        declareImage(ISharedImages.IMG_TOOL_REDO_HOVER, PATH_ETOOL
                + "redo_edit.png", true); //$NON-NLS-1$
        declareImage(ISharedImages.IMG_TOOL_CUT_HOVER, PATH_ETOOL
        		+ "cut_edit.png", true); //$NON-NLS-1$
        declareImage(ISharedImages.IMG_TOOL_COPY_HOVER, PATH_ETOOL
                + "copy_edit.png", true); //$NON-NLS-1$
        declareImage(ISharedImages.IMG_TOOL_PASTE_HOVER, PATH_ETOOL
                + "paste_edit.png", true); //$NON-NLS-1$
        declareImage(ISharedImages.IMG_TOOL_FORWARD_HOVER, PATH_ELOCALTOOL
                + "forward_nav.png", true); //$NON-NLS-1$
        declareImage(ISharedImages.IMG_TOOL_DELETE_HOVER, PATH_ETOOL
        		+ "delete_edit.png", true); //$NON-NLS-1$
        declareImage(ISharedImages.IMG_TOOL_NEW_WIZARD_HOVER, PATH_ETOOL
                        + "new_wiz.png", true); //$NON-NLS-1$
        declareImage(ISharedImages.IMG_TOOL_BACK_HOVER, PATH_ELOCALTOOL
        		+ "backward_nav.png", true); //$NON-NLS-1$
        declareImage(ISharedImages.IMG_TOOL_UP_HOVER, PATH_ELOCALTOOL
                + "up_nav.png", true); //$NON-NLS-1$
    }

    /**
     * Declares a workbench image.
     * <p>
     * The workbench remembers the given image descriptor under the given name,
     * and makes the image available to plug-ins via
     * {@link org.eclipse.ui.ISharedImages IWorkbench.getSharedImages()}.
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
    public static void declareImage(String symbolicName,
            ImageDescriptor descriptor, boolean shared) {
        if (Policy.DEBUG_DECLARED_IMAGES) {
            Image image = descriptor.createImage(false);
            if (image == null) {
                WorkbenchPlugin.log("Image not found in WorkbenchImages.declaredImage().  symbolicName=" + symbolicName + " descriptor=" + descriptor, new Exception("stack dump"));   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
            }
            else {
                image.dispose();
            }
        }
        getDescriptors().put(symbolicName, descriptor);
        if (shared) {
            getImageRegistry().put(symbolicName, descriptor);
        }
    }

    /**
     * Returns the map from symbolic name to ImageDescriptor.
     *
     * @return the map from symbolic name to ImageDescriptor.
     */
    public static Map<String, ImageDescriptor> getDescriptors() {
        if (descriptors == null) {
            initializeImageRegistry();
        }
        return descriptors;
    }

    /**
     * Returns the image stored in the workbench plugin's image registry
     * under the given symbolic name.  If there isn't any value associated
     * with the name then <code>null</code> is returned.
     *
     * The returned Image is managed by the workbench plugin's image registry.
     * Callers of this method must not dispose the returned image.
     *
     * This method is essentially a convenient short form of
     * WorkbenchImages.getImageRegistry.get(symbolicName).
     *
     * @param symbolicName the symbolic name
     * @return the image
     */
    public static Image getImage(String symbolicName) {
        return getImageRegistry().get(symbolicName);
    }

    /**
     * Returns the image descriptor stored under the given symbolic name.
     * If there isn't any value associated with the name then <code>null
     * </code> is returned.
     *
     * The class also "caches" commonly used images in the image registry.
     * If you are looking for one of these common images it is recommended you use
     * the getImage() method instead.
     *
     * @param symbolicName the symbolic name
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String symbolicName) {
        return getDescriptors().get(symbolicName);
    }

    /**
     * Convenience Method.
     * Returns an ImageDescriptor obtained from an external program.
     * If there isn't any image then <code>null</code> is returned.
     *
     * This method is convenience and only intended for use by the workbench because it
     * explicitly uses the workbench's registry for caching/retrieving images from other
     * extensions -- other plugins must user their own registry.
     * This convenience method is subject to removal.
     *
     * Note:
     * This consults the plugin for extension and obtains its installation location.
     * all requested images are assumed to be in a directory below and relative to that
     * plugins installation directory.
     *
     * @param filename the file name
     * @param offset the offset
     * @return the image descriptor
     */

    public static ImageDescriptor getImageDescriptorFromProgram(
            String filename, int offset) {
        Assert.isNotNull(filename);
        ImageDescriptor desc = getImageDescriptor(key);
        if (desc == null) {
            desc = new ProgramImageDescriptor(filename, offset);
            getDescriptors().put(key, desc);
        }
        return desc;
    }

    /**
     * Returns the ImageRegistry.
     *
     * @return the image registry
     */
    public static ImageRegistry getImageRegistry() {
        if (imageRegistry == null) {
            initializeImageRegistry();
        }
        return imageRegistry;
    }

    /**
	 * Initialize the image registry by declaring all of the required graphics.
	 * This involves creating JFace image descriptors describing how to
	 * create/find the image should it be needed. The image is not actually
	 * allocated until requested.
