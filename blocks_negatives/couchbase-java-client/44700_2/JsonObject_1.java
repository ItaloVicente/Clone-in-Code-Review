        StringBuilder sb = new StringBuilder("{");
        int size = content.size();
        int item = 0;
        for(Map.Entry<String, Object> entry : content.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\":");
            if (entry.getValue() instanceof String) {
                sb.append("\"").append(entry.getValue()).append("\"");
            } else {
                if (entry.getValue() == null) {
                    sb.append("null");
                } else {
                    sb.append(entry.getValue().toString());
                }
            }
            if (++item < size) {
                sb.append(",);
-            }
+        try {
+            return JacksonTransformers.MAPPER.writeValueAsString(this);
+        } catch (JsonProcessingException e) {
+            throw new IllegalStateException(Cannot convert JsonObject to Json String", e);
