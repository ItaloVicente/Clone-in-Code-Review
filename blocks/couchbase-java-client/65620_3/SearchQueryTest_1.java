        SearchQueryResult result = null;
        try {
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
            result = ctx.bucket().query(query);
        } finally {
            ctx.bucket().mutateIn(key).replace("category", category).execute();
        }
