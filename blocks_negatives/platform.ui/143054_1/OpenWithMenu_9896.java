    }

    /**
     * Returns an image to show for the corresponding editor descriptor.
     *
     * @param editorDesc the editor descriptor, or null for the system editor
     * @return the image or null
     */
    private Image getImage(IEditorDescriptor editorDesc) {
        ImageDescriptor imageDesc = getImageDescriptor(editorDesc);
        if (imageDesc == null) {
            return null;
        }
        return IDEWorkbenchPlugin.getDefault().getResourceManager().createImage(imageDesc);
    }

    /**
     * Returns the image descriptor for the given editor descriptor,
     * or null if it has no image.
     */
    private ImageDescriptor getImageDescriptor(IEditorDescriptor editorDesc) {
        ImageDescriptor imageDesc = null;
        if (editorDesc == null) {
