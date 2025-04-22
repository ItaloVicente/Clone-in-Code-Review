
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouchbaseNode that = (CouchbaseNode) o;

        if (!hostname.equals(that.hostname)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return hostname.hashCode();
    }
