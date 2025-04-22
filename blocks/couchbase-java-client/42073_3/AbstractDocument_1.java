
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDocument that = (AbstractDocument) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cas != that.cas) return false;
        if (expiry != that.expiry) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (cas ^ (cas >>> 32));
        result = 31 * result + expiry;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
