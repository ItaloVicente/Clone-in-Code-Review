            this.valueEncryptionConfig = new ValueEncryptionConfig(encryptedField.provider());
            if (!encryptedField.key().isEmpty()) {
                this.valueEncryptionConfig.addKey(encryptedField.key());
            }
            if (!encryptedField.hmac().isEmpty()) {
                this.valueEncryptionConfig.addHMACKey(encryptedField.hmac());
            }
