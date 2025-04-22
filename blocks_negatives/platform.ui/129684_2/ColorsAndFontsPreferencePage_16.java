        for (Iterator i = previewSet.iterator(); i.hasNext();) {
            IThemePreview preview = (IThemePreview) i.next();
            try {
                preview.dispose();
            } catch (RuntimeException e) {
                WorkbenchPlugin.log(RESOURCE_BUNDLE.getString("errorDisposePreviewLog"), //$NON-NLS-1$
                		StatusUtil.newStatus(IStatus.ERROR, e.getMessage(), e));
            }
        }
        previewSet.clear();
    }

    /**
     * Get the ancestor of the given color, if any.
     *
     * @param definition the descendant <code>ColorDefinition</code>.
     * @return the ancestor <code>ColorDefinition</code>, or <code>null</code>
     * 		if none.
     */
    private ColorDefinition getColorAncestor(ColorDefinition definition) {
        String defaultsTo = definition.getDefaultsTo();
        if (defaultsTo == null)
