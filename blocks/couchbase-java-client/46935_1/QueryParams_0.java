
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryParams that = (QueryParams) o;

        if (clientContextId != null ? !clientContextId.equals(that.clientContextId) : that.clientContextId != null)
            return false;
        if (consistency != that.consistency) return false;
        if (scanWait != null ? !scanWait.equals(that.scanWait) : that.scanWait != null) return false;
        if (serverSideTimeout != null ? !serverSideTimeout.equals(that.serverSideTimeout) : that.serverSideTimeout != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serverSideTimeout != null ? serverSideTimeout.hashCode() : 0;
        result = 31 * result + (consistency != null ? consistency.hashCode() : 0);
        result = 31 * result + (scanWait != null ? scanWait.hashCode() : 0);
        result = 31 * result + (clientContextId != null ? clientContextId.hashCode() : 0);
        return result;
    }
