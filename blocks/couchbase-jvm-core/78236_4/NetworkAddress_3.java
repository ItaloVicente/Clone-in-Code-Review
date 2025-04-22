
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkAddress that = (NetworkAddress) o;

        return inner.equals(that.inner);
    }

    @Override
    public int hashCode() {
        return inner.hashCode();
    }
