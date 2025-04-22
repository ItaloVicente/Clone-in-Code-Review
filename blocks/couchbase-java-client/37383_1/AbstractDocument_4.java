    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + "{");
        sb.append("id='").append(id).append('\'');
        sb.append(", cas=").append(cas);
        sb.append(", expiry=").append(expiry);
        sb.append(", status=").append(status);
        sb.append(", content=").append(content);
        sb.append('}');
        return sb.toString();
    }
