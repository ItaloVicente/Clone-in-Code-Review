
    @InterfaceStability.Experimental
    Observable<IndexInfo> listIndexes();

    @InterfaceStability.Experimental
    Observable<Boolean> createPrimaryIndex(boolean ignoreIfExist, boolean defer);

    @InterfaceStability.Experimental
    Observable<Boolean> createIndex(String indexName, boolean ignoreIfExist, boolean defer, Object... fields);

    @InterfaceStability.Experimental
    Observable<Boolean> dropPrimaryIndex(boolean ignoreIfNotExist);


    @InterfaceStability.Experimental
    Observable<Boolean> dropIndex(String name, boolean ignoreIfNotExist);

    @InterfaceStability.Experimental
    Observable<List<String>> buildDeferredIndexes();

    @InterfaceStability.Experimental
    Observable<IndexInfo> watchIndex(String indexName, long watchTimeout, TimeUnit watchTimeUnit);

    @InterfaceStability.Experimental
    Observable<IndexInfo> watchIndexes(boolean watchPrimary, long watchTimeout, TimeUnit watchTimeUnit, String... watchList);

    @InterfaceStability.Experimental
    Observable<IndexInfo> watchIndexes(boolean watchPrimary, long watchTimeout, TimeUnit watchTimeUnit,
            List<String> watchList);
