
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DesignDocument that = (DesignDocument) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (views != null ? !views.equals(that.views) : that.views != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (views != null ? views.hashCode() : 0);
        return result;
    }
