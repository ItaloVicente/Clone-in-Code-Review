
package com.couchbase.client.core.message.kv.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.BinaryRequest;
import com.couchbase.client.core.message.kv.subdoc.multi.LookupCommand;

import java.util.List;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface BinarySubdocMultiLookupRequest extends BinaryRequest {

    List<LookupCommand> commands();
}
