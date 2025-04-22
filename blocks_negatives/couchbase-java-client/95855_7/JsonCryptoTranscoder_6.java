        if (content == null || content.encryptionPathInfo().size() == 0) {
            return;
        }

        for (Map.Entry<String, ValueEncryptionConfig> entry:content.encryptionPathInfo().entrySet()) {
            ValueEncryptionConfig config = entry.getValue();
            String[] pathSplit = entry.getKey().split("/");

            int i = 0;
            for (String path: pathSplit) {
                pathSplit[i] = path.replace("~1", "/").replace("~0", "~");
                i++;
            }

            JsonObject parent = content;
            String lastPointer = pathSplit[pathSplit.length-1];

            for (i=0; i < pathSplit.length-1; i++) {
                parent = (JsonObject) parent.get(pathSplit[i]);
