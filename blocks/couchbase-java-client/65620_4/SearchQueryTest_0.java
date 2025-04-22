    @Test
    public void shouldSearchWithNoHits() {
        AbstractFtsQuery fts = SearchQuery.matchPhrase("noabfaobf nnda");
        SearchQuery query = new SearchQuery(INDEX, fts)
                .limit(3);

        SearchQueryResult result = ctx.bucket().query(query);

        assertThat(result).as("result").isNotNull();
        assertThat(result.metrics()).as("metrics").isNotNull();
        assertThat(result.hits()).as("hits").isEmpty();
        assertThat(result.hitsOrFail()).as("hitsOrFail").isEmpty();
        assertThat(result.hits()).as("hits == hitsOrFail").isEqualTo(result.hitsOrFail());
        assertThat(result.hits().size()).as("hits size").isEqualTo((int) result.metrics().totalHits());
        assertThat(result.metrics().totalHits()).as("totalHits").isEqualTo(0L);
        assertThat(result.errors()).as("errors").isEmpty();
    }

