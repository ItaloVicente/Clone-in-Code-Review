package com.couchbase.client.java.convert;

import io.netty.buffer.ByteBuf;

public interface Converter<D, T> {

  T decode(ByteBuf buffer);

  ByteBuf encode(T content);

  D newDocument();

}
