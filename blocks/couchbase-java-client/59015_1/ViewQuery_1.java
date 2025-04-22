    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ViewQuery(").append(design).append('/').append(view).append("){");
        sb.append("params=\"").append(toQueryString()).append('"');
        if (isDevelopment()) {
            sb.append(", dev");
        }
        if (isIncludeDocs()) {
            sb.append(", includeDocs");
        }
        if (keysJson != null) {
            sb.append(", keys=\"");
            if (keysJson.length() < 140) {
                sb.append(keysJson).append('"');
            } else {
                sb.append(keysJson, 0, 140)
                    .append("...\"(")
                    .append(keysJson.length())
                    .append(" chars total)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

