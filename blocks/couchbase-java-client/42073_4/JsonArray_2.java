
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsonArray array = (JsonArray) o;

        if (content != null ? !content.equals(array.content) : array.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
