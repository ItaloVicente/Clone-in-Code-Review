
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BinaryResponse{");
        sb.append("bucket='").append(bucket).append('\'');
        sb.append(", status=").append(status());
        sb.append(", request=").append(request());
        sb.append(", content=").append(content);
        sb.append('}');
        return sb.toString();
    }
