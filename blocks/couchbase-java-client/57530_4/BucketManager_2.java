
    @InterfaceStability.Experimental
    List<IndexInfo> listIndexes();

    @InterfaceStability.Experimental
    List<IndexInfo> listIndexes(long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    boolean createPrimaryIndex(boolean ignoreIfExist, boolean defer);

    @InterfaceStability.Experimental
    boolean createPrimaryIndex(boolean ignoreIfExist, boolean defer, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    boolean createIndex(String indexName, boolean ignoreIfExist, boolean defer, Object... fields); //for convenience

    @InterfaceStability.Experimental
    boolean createIndex(String indexName, List<Object> fields, boolean ignoreIfExist, boolean defer); //for consistency with timeout api below

    @InterfaceStability.Experimental
    boolean createIndex(String indexName, List<Object> fields, boolean ignoreIfExist, boolean defer, long timeout,
            TimeUnit timeUnit);

    @InterfaceStability.Experimental
    boolean dropPrimaryIndex(boolean ignoreIfNotExist);

    @InterfaceStability.Experimental
    boolean dropPrimaryIndex(boolean ignoreIfNotExist, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    boolean dropIndex(String name, boolean ignoreIfNotExist);

    @InterfaceStability.Experimental
    boolean dropIndex(String name, boolean ignoreIfNotExist, long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    List<String> buildDeferredIndexes();

    @InterfaceStability.Experimental
    List<String> buildDeferredIndexes(long timeout, TimeUnit timeUnit);

    @InterfaceStability.Experimental
    List<IndexInfo> watchIndexes(List<String> watchList, boolean watchPrimary, long watchTimeout, TimeUnit watchTimeUnit);

    @InterfaceStability.Experimental
    boolean watchIndex(String indexName, long watchTimeout, TimeUnit watchTimeUnit);
