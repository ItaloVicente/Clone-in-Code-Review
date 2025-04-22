    @Test
    public void shouldThrowFtsConsistencyTimeoutException() {
        Document d = ctx.bucket().get("21st_amendment_brewery_cafe-21a_ipa");
        d = ctx.bucket().upsert(d);
        AbstractFtsQuery fts = SearchQuery.match("beer");
        SearchQuery query = new SearchQuery(INDEX, fts)
                .consistentWith(d)
                .serverSideTimeout(1, TimeUnit.MILLISECONDS);

        SearchQueryResult result = ctx.bucket().query(query);

        assertThat(result.status().isSuccess()).isFalse();
        catchException(result).hitsOrFail();
        assertThat(caughtException()).isInstanceOf(FtsConsistencyTimeoutException.class);
    }

    @Test
    public void shouldThrowCouchbaseExceptionWhenTimeout() {
        AbstractFtsQuery fts = SearchQuery.match("beer");
        SearchQuery query = new SearchQuery(INDEX, fts)
                .serverSideTimeout(3, TimeUnit.MILLISECONDS);

        SearchQueryResult result = ctx.bucket().query(query);

        assertThat(result.status().isSuccess()).isFalse();
        catchException(result).hitsOrFail();
        assertThat(caughtException())
                .isInstanceOf(CompositeException.class);
        CompositeException compositeException = caughtException();
        Throwable inner = compositeException.getExceptions().get(0);
        assertThat(inner).hasMessageContaining("context deadline exceeded");
    }

