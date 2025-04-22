    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewQuery viewQuery = (ViewQuery) o;

        if (development != viewQuery.development) return false;
        if (design != null ? !design.equals(viewQuery.design) : viewQuery.design != null) return false;
        if (!Arrays.equals(params, viewQuery.params)) return false;
        if (view != null ? !view.equals(viewQuery.view) : viewQuery.view != null) return false;

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
