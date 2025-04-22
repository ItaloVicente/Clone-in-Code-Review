        }).map(new Func1<InsertSearchIndexResponse, IndexSettings>() {
            @Override
            public IndexSettings call(InsertSearchIndexResponse response) {
                if (!response.status().isSuccess()) {
                    throw new CouchbaseException("Could not insert search index: " + response.payload());
                }
                return settings;
            }
        });
