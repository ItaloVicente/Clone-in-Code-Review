package com.couchbase.client.java;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.cluster.SearchIndexDefinitionBuilder;
import com.couchbase.client.java.cluster.SearchIndexSourceType;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.util.CouchbaseTestContext;
import com.couchbase.client.java.util.features.CouchbaseFeature;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SearchIndexManagementTest {

    private static CouchbaseTestContext ctx;

    @BeforeClass
    public static void init() {
        ctx = CouchbaseTestContext.builder()
                .withEnv(DefaultCouchbaseEnvironment.builder()
                        .mutationTokensEnabled(true))
                .bucketName("beer-sample")
                .flushOnInit(false)
                .adhoc(false)
                .build()
                .ignoreIfMissing(CouchbaseFeature.FTS_BETA);
    }

    private void createIndex(String indexName) throws Exception {
        ctx.clusterManager().searchIndexManager().createIndex(
                new SearchIndexDefinitionBuilder(indexName, "beer-sample",
                        SearchIndexSourceType.COUCHBASE));
        do {
            Thread.sleep(10); //Wait for pindexes to be up on the first time
        } while (ctx.clusterManager().searchIndexManager().getIndexedDocumentCount(indexName) != 7303);

    }

    private void createIndexAlias(String indexName, String aliasName) throws Exception {
        JsonObject indexParams = JsonObject.create();
        indexParams.put("targets", JsonObject.create().put(indexName, JsonObject.create()));
        ctx.clusterManager().searchIndexManager().createIndex(
                new SearchIndexDefinitionBuilder(aliasName).setIndexParams(indexParams));
    }

    @Test
    public void testCreateDeleteIndex() throws Exception {
        createIndex("testCreateDeleteIndex");
        ctx.clusterManager().searchIndexManager().deleteIndex("testCreateDeleteIndex");
        JsonObject indexDefResponse = ctx.clusterManager().searchIndexManager().listIndexDefinitions();
        Assert.assertEquals(null, indexDefResponse.getObject("indexDefs").getObject("indexDefs").getObject("testCreateDeleteIndex"));
    }

    @Test
    public void testCreateDeleteIndexAlias() throws Exception {
        createIndex("testIndex");
        createIndexAlias("testIndex", "testIndexAlias");
        ctx.clusterManager().searchIndexManager().deleteIndex("testIndex");
        ctx.clusterManager().searchIndexManager().deleteIndex("testIndexAlias");
        JsonObject indexDefResponse = ctx.clusterManager().searchIndexManager().listIndexDefinitions();
        Assert.assertEquals(null, indexDefResponse.getObject("indexDefs").getObject("indexDefs").getObject("testIndexAlias"));
    }

    @Test(expected = CouchbaseException.class)
    public void testDeleteNotExistingIndex() throws Exception {
        ctx.clusterManager().searchIndexManager().deleteIndex("testDeleteNotExistingIndex");
    }

    @Test
    public void testMultipleIndexCreation() throws Exception {
        boolean multipleIndexCreationFail = true;
        createIndex("testMultipleIndexCreation");
        try {
            createIndex("testMultipleIndexCreation");
        } catch (Exception ex) {
            multipleIndexCreationFail = true;
        }
        ctx.clusterManager().searchIndexManager().deleteIndex("testMultipleIndexCreation");
        Assert.assertEquals(true, multipleIndexCreationFail);
    }
}
