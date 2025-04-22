    @InterfaceStability.Uncommitted
    public N1qlParams rawParam(String name, Object value) {
        if (this.rawParams == null) {
            this.rawParams = new HashMap<String, Object>();
        }

        if (!JsonValue.checkType(value)) {
            throw new IllegalArgumentException("Only JSON types are supported.");
        }

        rawParams.put(name, value);
        return this;
    }

