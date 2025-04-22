    protected static double doublePropertyOr(String path, double def) {
        String found = System.getProperty(NAMESPACE + path);
        if (found == null) {
            return def;
        }
        return Double.parseDouble(found);
    }

