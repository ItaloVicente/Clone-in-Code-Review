        /**
         * Add the encryption configuration for field level encryption
         *
         * @param config encryption configuration to use.
         * @return the builder for chaining purposes.
         */
        @InterfaceAudience.Public
        @InterfaceStability.Uncommitted
        public SELF encryptionConfig(final EncryptionConfig config) {
            this.encryptionConfig = config;
            return self();
        }

