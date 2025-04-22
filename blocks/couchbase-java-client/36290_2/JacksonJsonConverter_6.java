package com.couchbase.client.java.convert;

import io.netty.buffer.ByteBuf;

public interface Converter {

  Object from(ByteBuf buffer);

  ByteBuf to(Object content);

}
