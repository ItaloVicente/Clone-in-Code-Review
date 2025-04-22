    /**
     * Drags the given view OR editor to the given location (i.e. it only cares that we're given
     * a 'Part' and doesn't care whether it's a 'View' or an 'Editor'.
     * <p>
     * This method should eventually replace the original one once the Workbench has been updated
     * to handle Views and Editors without distincton.
     *
     * @param editor
     * @param target
     * @param wholeFolder
     */
    public static void drag(IWorkbenchPart part, TestDropLocation target,
            boolean wholeFolder) {
        DragUtil.forceDropLocation(target);
