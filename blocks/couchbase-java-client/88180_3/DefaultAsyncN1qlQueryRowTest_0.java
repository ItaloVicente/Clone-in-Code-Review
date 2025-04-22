package com.couchbase.client.java.query;

import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.error.TranscodingException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DefaultAsyncN1qlQueryRowTest {

  @Test
  public void shouldLogValueIfNotDecodable() {
    String invalidValue = "{\"invalid:true}";
    DefaultAsyncN1qlQueryRow row = new DefaultAsyncN1qlQueryRow(invalidValue.getBytes(CharsetUtil.UTF_8));
    try {
      row.value();
      fail("did expect transcoding exception");
    } catch (TranscodingException ex) {
      assertEquals(
        "Error deserializing row value from bytes to JsonObject \"{\"invalid:true}\"",
        ex.getMessage()
      );
    }
  }

}
