        AboutBundleGroupData[] bundleGroupInfos = (AboutBundleGroupData[]) groups
                .toArray(new AboutBundleGroupData[0]);

        AboutData.sortById(false, bundleGroupInfos);

        for (int i = 0; i < bundleGroupInfos.length; ++i) {
            AboutBundleGroupData info = bundleGroupInfos[i];
            String[] args = new String[] { info.getId(), info.getVersion(),
                    info.getName() };
            writer.println(NLS.bind(WorkbenchMessages.SystemSummary_featureVersion, args)); 
        }
    }

    /**
     * Appends the contents of the Plugin Registry.
     */
    private void appendRegistry(PrintWriter writer) {
        writer.println();
        writer.println(WorkbenchMessages.SystemSummary_pluginRegistry);

        BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
