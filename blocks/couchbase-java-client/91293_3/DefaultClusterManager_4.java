
    @Override
    @InterfaceStability.Experimental
    public SearchIndexManager searchIndexManager() {
        return new DefaultSearchIndexManager();
    }

    public class DefaultSearchIndexManager implements SearchIndexManager {

        public JsonObject listIndexDefinitions() {
            return Blocking.blockForSingle(asyncClusterManager.asyncSearchIndexManager().listIndexDefinitions().single(), timeout, TIMEOUT_UNIT);
        }

        public JsonObject listIndexDefinitions(long timeout, TimeUnit timeUnit) {
            return Blocking.blockForSingle(asyncClusterManager.asyncSearchIndexManager().listIndexDefinitions().single(), timeout, timeUnit);
        }

        public JsonObject listIndexDefinition(String indexName) {
            return Blocking.blockForSingle(asyncClusterManager.asyncSearchIndexManager().listIndexDefinition(indexName), timeout, TIMEOUT_UNIT);
        }

        public JsonObject listIndexDefinition(String indexName, long timeout, TimeUnit timeUnit) {
            return Blocking.blockForSingle(asyncClusterManager.asyncSearchIndexManager().listIndexDefinition(indexName), timeout, timeUnit);
        }

        public JsonObject createIndex(SearchIndexDefinitionBuilder definition) {
            return Blocking.blockForSingle(asyncClusterManager.asyncSearchIndexManager().createIndex(definition), timeout, TIMEOUT_UNIT);
        }

        public JsonObject createIndex(SearchIndexDefinitionBuilder definition, long timeout, TimeUnit timeUnit) {
            return Blocking.blockForSingle(asyncClusterManager.asyncSearchIndexManager().createIndex(definition), timeout, timeUnit);
        }

        public boolean deleteIndex(String indexName) {
            return Blocking.blockForSingle(asyncClusterManager.asyncSearchIndexManager().deleteIndex(indexName).single(), timeout, TIMEOUT_UNIT);
        }

        public boolean deleteIndex(String indexName, long timeout, TimeUnit timeUnit) {
            return Blocking.blockForSingle(asyncClusterManager.asyncSearchIndexManager().deleteIndex(indexName).single(), timeout, timeUnit);
        }

        public int getIndexedDocumentCount(String indexName) {
            return Blocking.blockForSingle(asyncClusterManager.asyncSearchIndexManager().getIndexedDocumentCount(indexName).single(), timeout, TIMEOUT_UNIT);
        }

        public int getIndexedDocumentCount(String indexName, long timeout, TimeUnit timeUnit) {
            return Blocking.blockForSingle(asyncClusterManager.asyncSearchIndexManager().getIndexedDocumentCount(indexName).single(), timeout, timeUnit);
        }
    }
