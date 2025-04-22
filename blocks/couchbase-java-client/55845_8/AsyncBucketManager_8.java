

    Observable<IndexSettings> insertSearchIndex(IndexSettings settings);
    Observable<IndexSettings> updateSearchIndex(IndexSettings settings);
    Observable<Boolean> hasSearchIndex(String name);
    Observable<Boolean> removeSearchIndex(String name);
