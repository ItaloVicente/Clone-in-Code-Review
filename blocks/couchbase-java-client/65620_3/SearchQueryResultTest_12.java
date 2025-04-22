                .isInstanceOf(FtsMalformedRequestException.class)
                .hasMessage("FTS request is malformed. Details: some error message");
    }

    @Test
    public void testHttp412Conversion() {
        SearchQueryResult result = toSync(DefaultAsyncSearchQueryResult.fromHttp412());

        assertThat(result).isNotNull();
        assertThat(result.status()).isNotNull();
        assertThat(result.status().errorCount()).isEqualTo(1);
        assertThat(result.status().totalCount()).isEqualTo(1);
        assertThat(result.status().successCount()).isEqualTo(0);
        assertThat(result.facets())
                .isNotNull()
                .isEmpty();
        assertThat(result.metrics()).isNotNull();
        assertThat(result.metrics().maxScore()).isEqualTo(0d);
        assertThat(result.metrics().took()).isEqualTo(0);
        assertThat(result.metrics().totalHits()).isEqualTo(0);

        assertThat(result.hits()).isEmpty();
        assertThat(result.errors())
                .hasSize(1)
                .contains("The requested consistency level could not be satisfied before the timeout was reached");
        catchException(result).hitsOrFail();
        assertThat(caughtException())
                .isInstanceOf(FtsConsistencyTimeoutException.class)
                .hasMessage("The requested consistency level could not be satisfied before the timeout was reached");
        catchException(result).iterator();
        assertThat(caughtException())
                .isInstanceOf(FtsConsistencyTimeoutException.class)
                .hasMessage("The requested consistency level could not be satisfied before the timeout was reached");
