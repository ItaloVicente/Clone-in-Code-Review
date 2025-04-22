
package com.couchbase.client.core.message.kv.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import io.netty.buffer.ByteBuf;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface BinarySubdocMutationRequest extends BinarySubdocRequest {

    int expiration();

    ByteBuf fragment();

    boolean createIntermediaryPath();

    long cas();
}
