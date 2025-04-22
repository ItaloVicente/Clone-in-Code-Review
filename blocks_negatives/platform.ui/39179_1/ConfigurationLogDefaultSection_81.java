        AboutData.sortById(false, bundleInfos);

        for (int i = 0; i < bundleInfos.length; ++i) {
            AboutBundleData info = bundleInfos[i];
            String[] args = new String[] { info.getId(), info.getVersion(),
                    info.getName(), info.getStateName() };
            writer.println(NLS.bind(WorkbenchMessages.SystemSummary_descriptorIdVersionState, args)); 
        }
    }

    /**
     * Appends the preferences
     */
    private void appendUserPreferences(PrintWriter writer) {
        IPreferencesService service = Platform.getPreferencesService();
        IEclipsePreferences node = service.getRootNode();
        ByteArrayOutputStream stm = new ByteArrayOutputStream();
        try {
            service.exportPreferences(node, stm, null);
        } catch (CoreException e) {
        }

        writer.println();
        writer.println(WorkbenchMessages.SystemSummary_userPreferences); 

        BufferedReader reader = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(stm
                    .toByteArray());
            reader = new BufferedReader(new InputStreamReader(in, "8859_1")); //$NON-NLS-1$
            char[] chars = new char[8192];

            while (true) {
                int read = reader.read(chars);
                if (read <= 0) {
