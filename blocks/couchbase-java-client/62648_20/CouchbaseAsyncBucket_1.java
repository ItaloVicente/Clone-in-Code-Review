            public AsyncSearchQueryResult call(SearchQueryResponse response) {
                if (response.status().isSuccess()) {
                    JsonObject json = JsonObject.fromJson(response.payload());
                    return DefaultAsyncSearchQueryResult.fromJson(json);
                } else if (response.status() == ResponseStatus.INVALID_ARGUMENTS) {
                    return DefaultAsyncSearchQueryResult.fromHttp400(response.payload());
                } else {
                    throw new CouchbaseException("Could not query search index, " + response.status() + ": " + response.payload());
