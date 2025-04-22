        AbstractFtsQuery fts = SearchQuery.match("beer");
        SearchQuery query = new SearchQuery(INDEX, fts)
                .addFacets(term("foo", "name", 3),
                        date("bar", "updated", 1).addRange("old", null, "2014-01-01T00:00:00"),
                        numeric("baz", "abv", 2).addRange("strong", 4.9, null).addRange("light", null, 4.89)
                );
