    @Override
    public Observable<Boolean> createNamedPrimaryIndex(final String customName, final boolean ignoreIfExist, boolean defer) {
        Statement createIndex;
        UsingWithPath usingWithPath = Index.createNamedPrimaryIndex(customName).on(bucket);
        if (defer) {
            createIndex = usingWithPath.withDefer();
        } else {
            createIndex = usingWithPath;
        }

        return queryExecutor.execute(N1qlQuery.simple(createIndex))
            .compose(checkIndexCreation(ignoreIfExist, "Error creating custom primary index " + customName));
    }

