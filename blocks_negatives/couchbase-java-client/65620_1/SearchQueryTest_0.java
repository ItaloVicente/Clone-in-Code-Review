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
