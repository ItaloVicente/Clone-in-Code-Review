    private final Map<Class<?>, EntityMetadata> metadataCache;

    public DefaultEntityConverter() {
        this.metadataCache = new ConcurrentHashMap<Class<?>, EntityMetadata>();
    }

