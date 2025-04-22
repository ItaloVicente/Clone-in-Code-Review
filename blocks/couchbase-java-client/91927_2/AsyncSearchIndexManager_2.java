
package com.couchbase.client.java.cluster;

import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;
import rx.Observable;

@InterfaceStability.Experimental
public interface AsyncSearchIndexManager {

    Observable<JsonObject> listIndexDefinitions();

    Observable<JsonObject> listIndexDefinition(String indexName);

    Observable<JsonObject> createIndex(SearchIndexDefinitionBuilder definition);

    Observable<Boolean> deleteIndex(String indexName);

    Observable<Integer> getIndexedDocumentCount(String indexName);
}
