
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsonObject object = (JsonObject) o;

        if (content != null ? !content.equals(object.content) : object.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
