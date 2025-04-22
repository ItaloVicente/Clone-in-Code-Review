
package com.couchbase.client.core.utils.yasjl.Callbacks;

import io.netty.buffer.ByteBuf;

public interface JsonPointerCB1 extends JsonPointerCB {
	void call(ByteBuf value);
}
