
package org.eclipse.jgit.storage.dht;

import static org.junit.Assert.*;

import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class ChunkKeyTest {
	@Test
	public void testKey() {
		RepositoryKey repo1 = RepositoryKey.fromInt(0x41234567);
		RepositoryKey repo2 = RepositoryKey.fromInt(2);
		ObjectId id = ObjectId
				.fromString("3e64b928d51b3a28e89cfe2a3f0eeae35ef07839");

		ChunkKey key1 = ChunkKey.create(repo1
		assertEquals(repo1.asInt()
		assertEquals(id
		assertEquals("3e.41234567.3e64b928d51b3a28e89cfe2a3f0eeae35ef07839"
				key1.asString());

		ChunkKey key2 = ChunkKey.fromBytes(key1.asBytes());
		assertEquals(repo1.asInt()
		assertEquals(id
		assertEquals("3e.41234567.3e64b928d51b3a28e89cfe2a3f0eeae35ef07839"
				key2.asString());

		ChunkKey key3 = ChunkKey.fromString(key1.asString());
		assertEquals(repo1.asInt()
		assertEquals(id
		assertEquals("3e.41234567.3e64b928d51b3a28e89cfe2a3f0eeae35ef07839"
				key3.asString());

		assertEquals(key1
		assertEquals(key2

		ChunkKey key4 = ChunkKey.create(repo2
		assertFalse("not equal"

		ObjectId id2 = ObjectId
				.fromString("3e64b928d51b3a28e89cfe2a3f0eeae35ef07840");
		ChunkKey key5 = ChunkKey.create(repo1
		assertFalse("not equal"
	}
}
