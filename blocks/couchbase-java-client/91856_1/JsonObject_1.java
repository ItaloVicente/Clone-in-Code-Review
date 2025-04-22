    @InterfaceAudience.Private
    private void addValueEncryptionInfo(String path, ValueEncryptionConfig config) {
        path = path.replaceAll("~", "~0").replaceAll("/", "~1");
        this.encryptionPathInfo.put(path, config);
    }

