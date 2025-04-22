    private String fsName;

    private Map<String, Object> params = new HashMap<>();

    public FileSystemHookExecutionContext(String fsName) {
        this.fsName = fsName;
    }

    /**
     * Returns the fileSystem name that this hook executes
     */
    public String getFsName() {
        return fsName;
    }

    /**
     * Adds a param to the context
     * @param name the name of the param
     * @param value
     */
    public void addParam(String name, Object value) {
        params.put(name, value);
    }

    /**
     * Gets a param value
     * @param name the name of the param
     * @return the param value
     */
    public Object getParamValue(String name) {
        return params.get(name);
    }
