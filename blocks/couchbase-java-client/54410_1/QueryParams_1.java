
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QueryParams{");
        sb.append("serverSideTimeout='").append(serverSideTimeout).append('\'');
        sb.append(", consistency=").append(consistency);
        sb.append(", scanWait='").append(scanWait).append('\'');
        sb.append(", clientContextId='").append(clientContextId).append('\'');
        sb.append(", maxParallelism=").append(maxParallelism);
        sb.append(", adhoc=").append(adhoc);
        sb.append('}');
        return sb.toString();
    }
