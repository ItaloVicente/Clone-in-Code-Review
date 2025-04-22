
package org.eclipse.jgit.storage.dht;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;

class LargeObject extends ObjectLoader {
	private final byte[] data;

	private final int type;

	private final long sz;

	private final int pos;

	LargeObject(byte[] data
		this.data = data;
		this.type = type;
		this.sz = sz;
		this.pos = pos;
	}

	@Override
	public boolean isLarge() {
		return true;
	}

	@Override
	public byte[] getCachedBytes() throws LargeObjectException {
		throw new LargeObjectException.ExceedsByteArrayLimit();
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public long getSize() {
		return sz;
	}

	@Override
	public ObjectStream openStream() throws MissingObjectException
		InputStream in;

		in = new ByteArrayInputStream(data
		in = new InflaterInputStream(in);
		return new ObjectStream.Filter(type
	}
}
