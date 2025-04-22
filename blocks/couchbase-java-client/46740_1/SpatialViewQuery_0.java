    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpatialViewQuery query = (SpatialViewQuery) o;

        if (development != query.development) return false;
        if (design != null ? !design.equals(query.design) : query.design != null) return false;
        if (!Arrays.equals(params, query.params)) return false;
        if (view != null ? !view.equals(query.view) : query.view != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = params != null ? Arrays.hashCode(params) : 0;
        result = 31 * result + (design != null ? design.hashCode() : 0);
        result = 31 * result + (view != null ? view.hashCode() : 0);
        result = 31 * result + (development ? 1 : 0);
        return result;
    }
