    }

    /**
     * Appends the installed and configured features.
     */
    private void appendFeatures(PrintWriter writer) {
        writer.println();
        writer.println(WorkbenchMessages.SystemSummary_features);

        IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
        LinkedList groups = new LinkedList();
        if (providers != null) {
