    /**
     * This method checks, if the view is minimized.
     * @param ref {@link IViewReference}
     * @return <code>true</code>, if view is minimized, otherwise <code>false</code>
     */
    public static boolean isViewMinimized(IViewReference ref) {
        MPart part = ((WorkbenchPartReference) ref).getModel();
        MUIElement parent = part.getParent();
        if (parent == null) {
            MPlaceholder placeholder = part.getCurSharedRef();
            if (placeholder != null) {
                parent = placeholder.getParent();
            }
        }

        if (parent != null) {
            List<String> tags = parent.getTags();
            return tags.contains(IPresentationEngine.MINIMIZED) || tags.contains(IPresentationEngine.MINIMIZED_BY_ZOOM);
        }
        return false;
    }
