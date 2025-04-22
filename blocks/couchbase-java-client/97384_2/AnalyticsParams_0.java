    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnalyticsParams params = (AnalyticsParams) o;

        if (serverSideTimeout != null ? !serverSideTimeout.equals(params.serverSideTimeout) : params.serverSideTimeout != null)
            return false;
        if (clientContextId != null ? !clientContextId.equals(params.clientContextId) : params.clientContextId != null)
            return false;
        return rawParams != null ? rawParams.equals(params.rawParams) : params.rawParams == null;
    }

    @Override
    public int hashCode() {
        int result = serverSideTimeout != null ? serverSideTimeout.hashCode() : 0;
        result = 31 * result + (clientContextId != null ? clientContextId.hashCode() : 0);
        result = 31 * result + (rawParams != null ? rawParams.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnalyticsParams{" +
            "serverSideTimeout='" + serverSideTimeout + '\'' +
            ", clientContextId='" + clientContextId + '\'' +
            ", rawParams=" + rawParams +
            '}';
    }
