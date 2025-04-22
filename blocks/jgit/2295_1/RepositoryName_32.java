
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.storage.dht.KeyUtils.format32;
import static org.eclipse.jgit.storage.dht.KeyUtils.parse32;
import static org.eclipse.jgit.util.RawParseUtils.decode;

public class RepositoryKey implements RowKey {
	public static RepositoryKey create(int sequentialId) {
		return new RepositoryKey(Integer.reverse(sequentialId));
	}

	public static RepositoryKey fromBytes(byte[] key) {
		return new RepositoryKey(parse32(key
	}

	static RepositoryKey fromInt(int reverseId) {
		return new RepositoryKey(reverseId);
	}

	private final int id;

	RepositoryKey(int id) {
		this.id = id;
	}

	int asInt() {
		return id;
	}

	public byte[] asByteArray() {
		byte[] r = new byte[8];
		format32(r
		return r;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof RepositoryKey)
			return id == ((RepositoryKey) other).id;
		return false;
	}

	@Override
	public String toString() {
		return "repository:" + decode(asByteArray());
	}
}
