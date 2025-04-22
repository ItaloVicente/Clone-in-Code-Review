
package org.eclipse.jgit.storage.dht;

public interface RowKey {
	public byte[] asByteArray();

	public int hashCode();

	public boolean equals(Object other);

	public String toString();
}
