
package com.couchbase.client.java.cluster;

import java.util.concurrent.TimeUnit;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
public interface SearchIndexManager {
	JsonObject listIndexDefinitions();

	JsonObject listIndexDefinitions(long timeout, TimeUnit timeUnit);

	JsonObject listIndexDefinition(String indexName);

	JsonObject listIndexDefinition(String indexName, long timeout, TimeUnit timeUnit);

	JsonObject createIndex(SearchIndexDefinitionBuilder definition);

	JsonObject createIndex(SearchIndexDefinitionBuilder definition, long timeout, TimeUnit timeUnit);

	boolean deleteIndex(String indexName);

	boolean deleteIndex(String indexName, long timeout, TimeUnit timeUnit);

	int getIndexedDocumentCount(String indexName);


	int getIndexedDocumentCount(String indexName, long timeout, TimeUnit timeUnit);
}
