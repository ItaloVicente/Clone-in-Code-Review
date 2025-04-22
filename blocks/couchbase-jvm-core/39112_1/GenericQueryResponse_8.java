package com.couchbase.client.core.message.document;

import io.netty.buffer.ByteBuf;

public class CoreDocument
{
	private final ByteBuf content;
	private final int flags;
	private final int expiration;
	private final long cas;
	private final boolean json;

	public CoreDocument(final ByteBuf content, final int flags, final int expiration, final long cas, final boolean json)
	{
		this.content = content;
		this.flags = flags;
		this.expiration = expiration;
		this.cas = cas;
		this.json = json;
	}

	public ByteBuf content()
	{
		return content;
	}

	public int flags()
	{
		return flags;
	}

	public int expiration()
	{
		return expiration;
	}

	public long cas()
	{
		return cas;
	}

	public boolean isJson()
	{
		return json;
	}
}
