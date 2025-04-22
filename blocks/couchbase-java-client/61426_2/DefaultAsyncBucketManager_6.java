    @Override
    public Observable<Boolean> dropNamedPrimaryIndex(String customName, boolean ignoreIfNotExist) {
        return drop(ignoreIfNotExist, Index.dropNamedPrimaryIndex(bucket, customName).using(IndexType.GSI),
                "Error dropping custom primary index \"" + customName + "\"");
    }

