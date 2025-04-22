                .ignoreIfMissing(CouchbaseFeature.FTS_BETA);
        JsonObject jsonObject = ctx.clusterManager().searchIndexManager().listIndexDefinitions();
        if (jsonObject.getObject("indexDefs").getObject("indexDefs").getObject("beer-search") == null) {
            String indexParams = "{\"doc_config\":{\"mode\":\"type_field\",\"type_field\":\"type\"},\"mapping\":{\"default_analyzer\":\"standard\",\"default_datetime_parser\":\"dateTimeOptional\",\"default_field\":\"_all\"," +
                    "\"default_mapping\":{\"dynamic\":true,\"enabled\":false},\"default_type\":\"_default\",\"index_dynamic\":true,\"store_dynamic\":false,\"types\":{\"beer\":{\"dynamic\":false,\"enabled\":true,\"properties\":" +
                    "{\"abv\":{\"dynamic\":false,\"enabled\":true,\"fields\":[{\"analyzer\":\"\",\"include_in_all\":true,\"include_term_vectors\":true,\"index\":true,\"name\":\"abv\",\"store\":false,\"type\":\"number\"}]}," +
                    "\"category\":{\"dynamic\":false,\"enabled\":true,\"fields\":[{\"analyzer\":\"\",\"include_in_all\":true,\"include_term_vectors\":true,\"index\":true,\"name\":\"category\",\"store\":false,\"type\":\"text\"}]}," +
                    "\"description\":{\"dynamic\":false,\"enabled\":true,\"fields\":[{\"analyzer\":\"\",\"include_in_all\":true,\"include_term_vectors\":true,\"index\":true,\"name\":\"description\",\"store\":false,\"type\":\"text\"}]}," +
                    "\"name\":{\"dynamic\":false,\"enabled\":true,\"fields\":[{\"analyzer\":\"\",\"include_in_all\":true,\"include_term_vectors\":true,\"index\":true,\"name\":\"name\",\"store\":false,\"type\":\"text\"}]}," +
                    "\"style\":{\"dynamic\":false,\"enabled\":true,\"fields\":[{\"analyzer\":\"\",\"include_in_all\":true,\"include_term_vectors\":true,\"index\":true,\"name\":\"style\",\"store\":false,\"type\":\"text\"}]}," +
                    "\"updated\":{\"dynamic\":false,\"enabled\":true,\"fields\":[{\"analyzer\":\"\",\"include_in_all\":true,\"include_term_vectors\":true,\"index\":true,\"name\":\"updated\",\"store\":false,\"type\":\"datetime\"}]}}}}}," +
                    "\"store\":{\"kvStoreName\":\"mossStore\"}}";
            ctx.clusterManager().searchIndexManager().createIndex(new SearchIndexDefinitionBuilder("beer-search",
                    "beer-sample", SearchIndexSourceType.COUCHBASE)
                    .setIndexParams(JsonObject.fromJson(indexParams)));
            Thread.sleep(10);
            while (ctx.clusterManager().searchIndexManager().getIndexedDocumentCount("beer-search") != 7303) {
                Thread.sleep(10);
            }
        }
