
package com.couchbase.client.java.query;

import static com.couchbase.client.java.query.dsl.Expression.x;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.dsl.path.index.DefaultWithPath;
import com.couchbase.client.java.query.dsl.path.index.IndexType;
import com.couchbase.client.java.query.dsl.path.index.OnPath;
import com.couchbase.client.java.query.dsl.path.index.OnPrimaryPath;
import com.couchbase.client.java.query.dsl.path.index.UsingPath;
import com.couchbase.client.java.query.dsl.path.index.UsingWherePath;
import com.couchbase.client.java.query.dsl.path.index.WherePath;
import com.couchbase.client.java.query.dsl.path.index.WithPath;
import org.junit.Test;

public class IndexDslTest {

    @Test
    public void testCreateIndex() throws Exception {
        OnPath idx1 = Index.createIndex("test");
        assertFalse(idx1 instanceof Statement);

        UsingWherePath idx2 = idx1.on("`beer-sample`", x("abv"));
        assertTrue(idx2 instanceof Statement);

        Statement fullIndex = Index.createIndex("test")
                .on("`beer-sample`", x("abv"), x("ibu"))
                .using(IndexType.GSI)
                .where(x("abv").gt(10))
                .withDefer();

        assertEquals("CREATE INDEX test ON `beer-sample`(abv, ibu) " +
                "USING GSI WHERE abv > 10 WITH `{\"defer_build\":true}`", fullIndex.toString());
    }

    @Test
    public void testCreatePrimaryIndex() throws Exception {
        OnPrimaryPath idx1 = Index.createPrimaryIndex();
        assertFalse(idx1 instanceof Statement);

        UsingPath idx2 = idx1.on("`beer-sample`");
        assertTrue(idx2 instanceof Statement);

        Statement fullIndex = Index.createPrimaryIndex()
            .on("`beer-sample`")
            .using(IndexType.GSI)
            .withDefer();

        assertEquals("CREATE PRIMARY INDEX ON `beer-sample` " +
                "USING GSI WITH `{\"defer_build\":true}`", fullIndex.toString());
    }

    @Test
    public void testWithVariants() {
        WithPath path = new DefaultWithPath(null);

        String expectedDefer = "WITH `" + JsonObject.create().put("defer_build", true) + "`";
        String expectedDeferAndNode = "WITH `" + JsonObject.create().put("defer_build", true).put("nodes", "test") + "`";
        String expectedNode = "WITH `" + JsonObject.create().put("nodes", "test") + "`";

        assertEquals(expectedDefer, path.withDefer().toString());
        assertEquals(expectedDeferAndNode, path.withDeferAndNode("test").toString());
        assertEquals(expectedNode, path.withNode("test").toString());
    }
}
