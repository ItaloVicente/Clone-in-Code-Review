        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < content.size(); i++) {
            Object item = content.get(i);
            boolean isString = item instanceof String;

            if (isString) {
                sb.append("\"");
            }
            sb.append(item.toString());
            if (isString) {
                sb.append("\"");
            }
            if (i < content.size()-1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
