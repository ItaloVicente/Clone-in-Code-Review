
package org.eclipse.jgit.storage.dht;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RepositoryKeyTest {
	@Test
	public void fromString() {
		assertEquals(RepositoryKey.create(2)
				.fromString("40000000"));

		assertEquals(RepositoryKey.create(1)
				.fromString("80000000"));
	}
}
