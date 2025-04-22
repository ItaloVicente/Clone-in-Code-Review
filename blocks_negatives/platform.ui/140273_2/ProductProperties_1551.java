    private static String[] loadMappings(Bundle definingBundle) {
		URL location = FileLocator.find(definingBundle, new Path(
                ABOUT_MAPPINGS));
        PropertyResourceBundle bundle = null;
        InputStream is;
        if (location != null) {
            is = null;
            try {
                is = location.openStream();
                bundle = new PropertyResourceBundle(is);
            } catch (IOException e) {
                bundle = null;
            } finally {
                try {
                    if (is != null) {
