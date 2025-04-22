        }

        descriptionText.selectAll();
    }

    /**
     * Updates the dialog from the predefined attributes.
     */
    protected void updateDialogForNewMarker() {
        if (resource != null && resourceText != null && folderText != null) {
            resourceText.setText(resource.getName());

            IPath path = resource.getFullPath();
            if (n > 0) {
                int len = 0;
                for (int i = 0; i < n; ++i) {
