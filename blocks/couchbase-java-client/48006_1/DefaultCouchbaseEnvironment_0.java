
    @Override
    protected StringBuilder dumpParameters(StringBuilder sb) {
        super.dumpParameters(sb);
        sb.append(", queryTimeout=").append(this.queryTimeout);
        sb.append(", viewTimeout=").append(this.viewTimeout);
        sb.append(", kvTimeout=").append(this.kvTimeout);
        sb.append(", connectTimeout=").append(this.connectTimeout);
        sb.append(", disconnectTimeout=").append(this.disconnectTimeout);
        sb.append(", dnsSrvEnabled=").append(this.dnsSrvEnabled);
        return sb;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CouchbaseEnvironment: {");
        this.dumpParameters(sb).append('}');
        return sb.toString();
    }
