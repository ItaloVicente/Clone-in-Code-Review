
package com.couchbase.client.core.message.kv.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.BinaryRequest;
import com.couchbase.client.core.message.kv.subdoc.multi.MutationCommand;
import io.netty.buffer.ByteBuf;

import java.util.List;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface BinarySubdocMultiMutationRequest extends BinaryRequest {

    int expiration();

    long cas();

    List<MutationCommand> commands();

    ByteBuf content();
}
