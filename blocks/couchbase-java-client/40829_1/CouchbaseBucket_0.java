        transcoders = new ConcurrentHashMap<Class<? extends Document>, Transcoder<? extends Document, ?>>();
        transcoders.put(JSON_TRANSCODER.documentType(), JSON_TRANSCODER);
        transcoders.put(LEGACY_TRANSCODER.documentType(), LEGACY_TRANSCODER);

        for (Transcoder<? extends Document, ?> custom : customTranscoders) {
            transcoders.put(custom.documentType(), custom);
        }

