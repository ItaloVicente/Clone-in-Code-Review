        StringBuilder sb = new StringBuilder("{");
        int size = content.size();
        int item = 0;
        for(Map.Entry<String, Object> entry : content.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\":");
            if (entry.getValue() instanceof String) {
                sb.append("\"").append(entry.getValue()).append("\"");
            } else {
                sb.append(entry.getValue().toString());
            }
            if (++item < size) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
