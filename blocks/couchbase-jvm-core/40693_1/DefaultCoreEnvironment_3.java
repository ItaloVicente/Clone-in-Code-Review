        return new DefaultCoreEnvironment(builder());
    }

    public static Builder builder() {
        return new Builder();
    }

    private boolean booleanPropertyOr(String path, boolean def) {
        String found = System.getProperty(NAMESPACE + path);
        if (found == null) {
            return def;
        }
        return Boolean.parseBoolean(found);
    }

    private String stringPropertyOr(String path, String def) {
        String found = System.getProperty(NAMESPACE + path);
        return found == null ? def : found;
