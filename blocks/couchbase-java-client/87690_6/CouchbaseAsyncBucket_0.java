package com.couchbase.client.java;

import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.util.CouchbaseTestContext;
import io.opentracing.mock.MockTracer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TracingTest  {

  protected static CouchbaseTestContext ctx;

  @BeforeClass
  public static void connect() {
    ctx = CouchbaseTestContext.builder()
      .withEnv(DefaultCouchbaseEnvironment.builder().tracer(new MockTracer()))
      .bucketQuota(256)
      .bucketType(BucketType.COUCHBASE)
      .flushOnInit(true)
      .enableFlush(true)
      .build();
  }

  @AfterClass
  public static void disconnect()  {
    ctx.disconnect();
  }

  @Test
  public void shouldTrace() {
    MockTracer tracer = (MockTracer) ctx.env().tracer();

    ctx.bucket().upsert(JsonDocument.create("foobar", JsonObject.create()));
    ctx.bucket().get("foobar");

    System.err.println(tracer.finishedSpans());
  }

}
