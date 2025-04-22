    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GetResponse{");
        sb.append("bucket='").append(bucket()).append('\'');
        sb.append(", status=").append(status());
        sb.append(", cas=").append(cas);
        sb.append(", flags=").append(flags);
        sb.append(", request=").append(request());
        sb.append(", content=").append(content().toString(CharsetUtil.UTF_8));
        sb.append('}');
        return sb.toString();
    }
