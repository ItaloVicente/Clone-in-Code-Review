
        if (environment.encryptionConfig() != null) {
            JsonCryptoTranscoder transcoder = new JsonCryptoTranscoder(environment.encryptionConfig());
            transcoders.put(transcoder.documentType(), transcoder);
        } else {
            transcoders.put(JSON_OBJECT_TRANSCODER.documentType(), JSON_OBJECT_TRANSCODER);
        }

