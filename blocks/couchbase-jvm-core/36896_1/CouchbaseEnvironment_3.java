
    protected boolean getBoolean(String path) {
        try {
            return config.getBoolean(namespace + '.' + path);
        } catch (Exception e) {
            throw new EnvironmentException("Could not load environment setting " + path + '.', e);
        }
    }
