package com.couchbase.client.java.convert;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

public final class CachedData {

  private final int flags;
  private final ByteBuf buffer;

  public CachedData(final int flags, final ByteBuf buffer) {
    this.flags = flags;
    this.buffer = buffer;
  }

  public ByteBuf getBuffer() {
    return buffer;
  }

  public int getFlags() {
    return flags;
  }

  @Override
  public String toString() {
    return "{CachedData flags=" + flags + " buffer=" + buffer.toString(CharsetUtil.UTF_8) + "}";
  }
}
