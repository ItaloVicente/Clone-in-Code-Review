    @InterfaceStability.Uncommitted
    public boolean isEncrypted(final String name) {
        return this.containsKey(ENCRYPTION_PREFIX + name);
    }

    @InterfaceStability.Uncommitted
    @InterfaceAudience.Private
    public Map<String, String> encryptionPathInfo() {
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

