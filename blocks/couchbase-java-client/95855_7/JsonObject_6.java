    private void addValueEncryptionInfo(String path, String providerName, boolean escape) {
        if (escape) {
            path = path.replaceAll("~", "~0").replaceAll("/", "~1");
        }
        if (this.encryptionPathInfo == null) {
            this.encryptionPathInfo = new HashMap<String, String>();
        }
        this.encryptionPathInfo.put(path, providerName);
