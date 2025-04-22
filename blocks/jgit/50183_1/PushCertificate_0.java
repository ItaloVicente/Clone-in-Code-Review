
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushCertificate.NonceStatus;
import org.junit.Before;
import org.junit.Test;

public class HMACSHA1NonceGeneratorTest {
	private static final long TS = 1433954361;

	private HMACSHA1NonceGenerator gen;
	private Repository db;

	@Before
	public void setUp() {
		gen = new HMACSHA1NonceGenerator("sekret");
		db = new InMemoryRepository(new DfsRepositoryDescription("db"));
	}

	@Test
	public void missing() throws Exception {
		assertEquals(NonceStatus.MISSING
	}

	@Test
	public void unsolicited() throws Exception {
		assertEquals(NonceStatus.UNSOLICITED
	}

	@Test
	public void invalidFormat() throws Exception {
		String sent = gen.createNonce(db
		assertEquals(NonceStatus.BAD
		assertEquals(NonceStatus.BAD
	}

	@Test
	public void slop() throws Exception {
		String sent = gen.createNonce(db
		String received = gen.createNonce(db
		assertEquals(NonceStatus.BAD
				gen.verify(received
		assertEquals(NonceStatus.BAD
				gen.verify(received
		assertEquals(NonceStatus.SLOP
				gen.verify(received
		assertEquals(NonceStatus.SLOP
				gen.verify(received
		assertEquals(NonceStatus.OK
				gen.verify(received
		assertEquals(NonceStatus.OK
				gen.verify(received
	}

	@Test
	public void ok() throws Exception {
		String sent = gen.createNonce(db
		assertEquals(NonceStatus.OK
	}

	@Test
	public void signedByDifferentKey() throws Exception {
		HMACSHA1NonceGenerator other = new HMACSHA1NonceGenerator("other");
		String sent = gen.createNonce(db
		String received = other.createNonce(db
		assertNotEquals(received
		assertEquals(NonceStatus.BAD
				gen.verify(received
	}
}
