
package com.couchbase.client.core.utils.yasjl.Callbacks;

import com.couchbase.client.core.utils.yasjl.JsonPointer;
import io.netty.buffer.ByteBuf;

public interface JsonPointerCB2 extends JsonPointerCB {
	void call(JsonPointer jsonPointer, ByteBuf value);
}
