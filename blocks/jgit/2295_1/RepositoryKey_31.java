
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.encode;
import static org.eclipse.jgit.storage.dht.KeyUtils.format32;
import static org.eclipse.jgit.storage.dht.KeyUtils.parse32;
import static org.eclipse.jgit.util.RawParseUtils.decode;

public class RefKey implements RowKey {
	public static RefKey create(RepositoryKey repo
		return new RefKey(repo.asInt()
	}

	public static RefKey fromBytes(byte[] key) {
		int repo = parse32(key
		String name = decode(key
		return new RefKey(repo
	}

	private final int repo;

	private final String name;

	RefKey(int repo
		this.repo = repo;
		this.name = name;
	}

	public RepositoryKey getRepositoryKey() {
		return RepositoryKey.fromInt(repo);
	}

	public String getName() {
		return name;
	}

	public byte[] asByteArray() {
		byte[] nameRaw = encode(name);
		byte[] r = new byte[9 + nameRaw.length];
		format32(r
		r[8] = ':';
		System.arraycopy(nameRaw
		return r;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof RefKey) {
			RefKey thisRef = this;
			RefKey otherRef = (RefKey) other;
			return thisRef.repo == otherRef.repo
					&& thisRef.name.equals(otherRef.name);
		}
		return false;
	}

	@Override
	public String toString() {
		return "ref:" + decode(asByteArray());
	}
}
