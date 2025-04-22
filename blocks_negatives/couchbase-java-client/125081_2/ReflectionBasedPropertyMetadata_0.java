        isId = fieldReference.isAnnotationPresent(Id.class);
        isField = fieldReference.isAnnotationPresent(com.couchbase.client.java.repository.annotation.Field.class);
        if (fieldReference.isAnnotationPresent(EncryptedField.class)) {
            EncryptedField encryptedField = fieldReference.getAnnotation(EncryptedField.class);
            this.encryptionProviderName = encryptedField.provider();
        }
