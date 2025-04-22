
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.util.RawParseUtils.decode;

import java.text.MessageFormat;

import org.eclipse.jgit.lib.ObjectId;

public final class CachedPackKey implements RowKey {
	static final int KEYLEN = 81;

	public static CachedPackKey fromBytes(byte[] key) {
		if (key.length != KEYLEN)
			throw new IllegalArgumentException(MessageFormat.format(
					DhtText.get().invalidChunkKey

		ObjectId name = ObjectId.fromString(key
		ObjectId vers = ObjectId.fromString(key
		return new CachedPackKey(name
	}

	private final ObjectId name;

	private final ObjectId version;

	CachedPackKey(ObjectId name
		this.name = name;
		this.version = version;
	}

	public byte[] toBytes() {
		byte[] r = new byte[KEYLEN];
		name.copyTo(r
		r[40] = '.';
		version.copyTo(r
		return r;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other instanceof CachedPackKey) {
			CachedPackKey key = (CachedPackKey) other;
			return name.equals(key.name) && version.equals(key.version);
		}
		return false;
	}

	@Override
	public String toString() {
		return "cached-pack:" + name.name() + "." + version.name();
	}
}
