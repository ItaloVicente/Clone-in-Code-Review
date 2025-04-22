    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ViewQueryResponse{");
        sb.append("content=").append(content.toString(CharsetUtil.UTF_8));
        sb.append(", totalRows=").append(totalRows);
        sb.append(", status=").append(status());
        sb.append('}');
        return sb.toString();
    }
