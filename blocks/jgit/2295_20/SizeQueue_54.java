
package org.eclipse.jgit.storage.dht;

public interface RowKey {
	public byte[] asBytes();

	public String asString();

	public int hashCode();

	public boolean equals(Object other);

	public String toString();
}
