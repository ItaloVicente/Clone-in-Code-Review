        IPropertySheetEntry entry = (IPropertySheetEntry) selection
                .getFirstElement();

        StringBuilder buffer = new StringBuilder();
        buffer.append(entry.getDisplayName());
        buffer.append(entry.getValueAsString());

        event.data = buffer.toString();
    }

    /**
     * Make action objects.
     */
    private void makeActions() {
        ISharedImages sharedImages = PlatformUI.getWorkbench()
                .getSharedImages();

        defaultsAction = new DefaultsAction(viewer, "defaults"); //$NON-NLS-1$
        defaultsAction.setText(PropertiesMessages.Defaults_text);
        defaultsAction.setToolTipText(PropertiesMessages.Defaults_toolTip);
