    @Test
    public void shouldAcceptAtPlusConsistencyVector() {
        String key = "21st_amendment_brewery_cafe-21a_ipa";
        String category = "North American Ale";

        ctx.bucket().mutateIn(key)
                .replace("category", "batman")
                .execute();

        DocumentFragment<Mutation> mutation = ctx
                .bucket().mutateIn(key)
                .replace("category", "superman")
                .execute();

        SearchQuery query = new SearchQuery("beer-search",
                SearchQuery.match("superman").field("category"))
                .consistentWith(mutation)
                .limit(3);
        SearchQueryResult result = ctx.bucket().query(query);

        ctx.bucket().mutateIn(key).replace("category", category).execute();

        assertThat(result.hits()).hasSize(1);
        assertThat(result.hits().get(0).id()).isEqualToIgnoringCase(key);
    }

