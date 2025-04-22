
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.storage.dht.KeyUtils.format32;
import static org.eclipse.jgit.storage.dht.KeyUtils.parse32;
import static org.eclipse.jgit.util.RawParseUtils.decode;

import java.text.MessageFormat;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;

public final class ObjectIndexKey extends ObjectId implements RowKey {
	private static final int KEYLEN = 52;

	public static ObjectIndexKey create(RepositoryKey repo
		return new ObjectIndexKey(repo.asInt()
	}

	public static ObjectIndexKey fromBytes(byte[] key) {
		if (key.length != KEYLEN)
			throw new IllegalArgumentException(MessageFormat.format(
					DhtText.get().invalidChunkKey

		int repo = parse32(key
		ObjectId id = ObjectId.fromString(key
		return new ObjectIndexKey(repo
	}

	public static ObjectIndexKey fromString(String key) {
		return fromBytes(Constants.encodeASCII(key));
	}

	private final int repo;

	ObjectIndexKey(int repo
		super(objId);
		this.repo = repo;
	}

	public RepositoryKey getRepositoryKey() {
		return RepositoryKey.fromInt(repo);
	}

	int getRepositoryId() {
		return repo;
	}

	public byte[] asBytes() {
		byte[] r = new byte[KEYLEN];
		copyTo(r
		format32(r
		r[11] = '.';
		r[2] = '.';
		r[1] = r[12 + 1];
		r[0] = r[12 + 0];
		return r;
	}

	public String asString() {
		return decode(asBytes());
	}

	@Override
	public String toString() {
		return "object-index:" + asString();
	}
}
