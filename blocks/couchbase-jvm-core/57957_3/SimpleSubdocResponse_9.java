
package com.couchbase.client.core.message.kv.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;
import com.couchbase.client.core.message.kv.BinaryRequest;
import io.netty.buffer.ByteBuf;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface BinarySubdocRequest extends BinaryRequest {

    String path();

    int pathLength();

    byte opcode();

    ByteBuf content();

}
