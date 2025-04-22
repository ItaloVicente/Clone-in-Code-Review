
package org.eclipse.jgit.storage.dht;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class ObjectIndexKeyTest {
	@Test
	public void testKey() {
		RepositoryKey repo = RepositoryKey.fromInt(0x41234567);
		ObjectId id = ObjectId
				.fromString("3e64b928d51b3a28e89cfe2a3f0eeae35ef07839");

		ObjectIndexKey key1 = ObjectIndexKey.create(repo
		assertEquals(repo.asInt()
		assertEquals(key1
		assertEquals("3e.41234567.3e64b928d51b3a28e89cfe2a3f0eeae35ef07839"
				key1.asString());

		ObjectIndexKey key2 = ObjectIndexKey.fromBytes(key1.asBytes());
		assertEquals(repo.asInt()
		assertEquals(key2
		assertEquals("3e.41234567.3e64b928d51b3a28e89cfe2a3f0eeae35ef07839"
				key2.asString());

		ObjectIndexKey key3 = ObjectIndexKey.fromString(key1.asString());
		assertEquals(repo.asInt()
		assertEquals(key3
		assertEquals("3e.41234567.3e64b928d51b3a28e89cfe2a3f0eeae35ef07839"
				key3.asString());
	}
}
