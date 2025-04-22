
    private EntityMetadata metadata(final Class<?> source) {
        EntityMetadata metadata = metadataCache.get(source);

        if (metadata == null) {
            EntityMetadata generated = new ReflectionBasedEntityMetadata(source);
            metadataCache.put(source, generated);
            return generated;
        } else {
            return metadata;
        }
    }
