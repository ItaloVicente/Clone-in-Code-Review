
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.encode;
import static org.eclipse.jgit.util.RawParseUtils.decode;

public class RepositoryName implements RowKey {
	public static RepositoryName create(String name) {
		return new RepositoryName(name);
	}

	public static RepositoryName fromBytes(byte[] name) {
		return new RepositoryName(decode(name));
	}

	private final String name;

	RepositoryName(String name) {
		this.name = name;
	}

	public byte[] toBytes() {
		return encode(name);
	}

	public String asString() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other instanceof RepositoryName)
			return name.equals(((RepositoryName) other).name);
		return false;
	}

	@Override
	public String toString() {
		return "repository:" + decode(toBytes());
	}
}
