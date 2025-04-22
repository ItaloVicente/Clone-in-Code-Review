        @InterfaceAudience.Public
        @InterfaceStability.Uncommitted
        public SELF encryptionConfig(final EncryptionConfig config) {
            this.encryptionConfig = config;
            return self();
        }

