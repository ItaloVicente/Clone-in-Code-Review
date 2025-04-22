

    IndexSettings insertSearchIndex(IndexSettings settings);
    IndexSettings insertSearchIndex(IndexSettings settings, long timeout, TimeUnit timeUnit);

    IndexSettings updateSearchIndex(IndexSettings settings);
    IndexSettings updateSearchIndex(IndexSettings settings, long timeout, TimeUnit timeUnit);

    Boolean hasSearchIndex(String name);
    Boolean hasSearchIndex(String name, long timeout, TimeUnit timeUnit);

    Boolean removeSearchIndex(String name);
    Boolean removeSearchIndex(String name, long timeout, TimeUnit timeUnit);

