    /**
     * If compression is supported.
     *
     * @return true if it is, false otherwise.
     */
    public boolean compression() {
        return compression;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SupportedDatatypes{");
        sb.append("json=").append(json);
        sb.append(", compression=").append(compression);
        sb.append('}');
        return sb.toString();
    }
