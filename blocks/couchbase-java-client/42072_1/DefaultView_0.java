
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultView that = (DefaultView) o;

        if (map != null ? !map.equals(that.map) : that.map != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (reduce != null ? !reduce.equals(that.reduce) : that.reduce != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (map != null ? map.hashCode() : 0);
        result = 31 * result + (reduce != null ? reduce.hashCode() : 0);
        return result;
    }
