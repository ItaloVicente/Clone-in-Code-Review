
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.storage.dht.KeyUtils.format32;
import static org.eclipse.jgit.storage.dht.KeyUtils.parse32;
import static org.eclipse.jgit.util.RawParseUtils.decode;

import java.text.MessageFormat;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

public class ObjectIndexKey extends ObjectId implements RowKey {
	private static final int KEYLEN = 54;

	public static ObjectIndexKey create(RepositoryKey repo
		return new ObjectIndexKey(repo.asInt()
	}

	public static ObjectIndexKey fromBytes(byte[] key) {
		if (key.length != KEYLEN)
			throw new IllegalArgumentException(MessageFormat.format(
					DhtText.get().invalidChunkKey

		int repo = parse32(key
		ObjectId chunk = ObjectId.fromString(key
		return new ObjectIndexKey(repo
	}

	private final int repo;

	ObjectIndexKey(int repo
		super(objId);
		this.repo = repo;
	}

	public byte[] asByteArray() {
		byte[] r = new byte[54];
		this.copyTo(r
		r[13] = '.';
		format32(r
		r[4] = '.';
		r[3] = r[14 + 3];
		r[2] = r[14 + 2];
		r[1] = r[14 + 1];
		r[0] = r[14 + 0];
		return r;
	}

	@Override
	public String toString() {
		return "object-index:" + decode(asByteArray());
	}
}
