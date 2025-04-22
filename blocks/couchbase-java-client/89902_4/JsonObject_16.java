    @InterfaceStability.Uncommitted
    @InterfaceAudience.Private
    public Map<String, EncryptionInfo> encryptionPathInfo() {
        return this.encryptionPathInfo;
    }

    @InterfaceStability.Uncommitted
    @InterfaceAudience.Private
    public void clearEncryptionPaths() {
        this.encryptionPathInfo.clear();
    }

    @InterfaceStability.Uncommitted
    @InterfaceAudience.Private
    public void setEncryptionConfig(EncryptionConfig config) {
        this.encryptionConfig = config;
    }

    @InterfaceStability.Uncommitted
    @InterfaceAudience.Private
    public EncryptionConfig getEncryptionConfig() {
        return this.encryptionConfig;
    }

