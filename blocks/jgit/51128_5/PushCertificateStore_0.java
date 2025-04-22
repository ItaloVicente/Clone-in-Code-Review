
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.junit.Before;
import org.junit.Test;

public class PushCertificateStoreTest {
	private static final PushCertificate CERT1 = parseCertificate(
			"certificate version 0.1\n"
			+ "pusher Dave Borowitz <dborowitz@google.com> 1433954361 -0700\n"
			+ "nonce 1433954361-bde756572d665bba81d8\n"
			+ "\n"
			+ "0000000000000000000000000000000000000000"
			+ " 6c2b981a177396fb47345b7df3e4d3f854c6bea7"
			+ " refs/heads/master\n"
			+ "-----BEGIN PGP SIGNATURE-----\n"
			+ "Version: GnuPG v1\n"
			+ "\n"
			+ "iQEcBAABAgAGBQJVeGg5AAoJEPfTicJkUdPkUggH/RKAeI9/i/LduuiqrL/SSdIa\n"
			+ "9tYaSqJKLbXz63M/AW4Sp+4u+dVCQvnAt/a35CVEnpZz6hN4Kn/tiswOWVJf4CO7\n"
			+ "htNubGs5ZMwvD6sLYqKAnrM3WxV/2TbbjzjZW6Jkidz3jz/WRT4SmjGYiEO7aA+V\n"
			+ "4ZdIS9f7sW5VsHHYlNThCA7vH8Uu48bUovFXyQlPTX0pToSgrWV3JnTxDNxfn3iG\n"
			+ "IL0zTY/qwVCdXgFownLcs6J050xrrBWIKqfcWr3u4D2aCLyR0v+S/KArr7ulZygY\n"
			+ "+SOklImn8TAZiNxhWtA6ens66IiammUkZYFv7SSzoPLFZT4dC84SmGPWgf94NoQ=\n"
			+ "=XFeC\n"
			+ "-----END PGP SIGNATURE-----\n");

	private static final PushCertificate CERT2 = parseCertificate(
			"certificate version 0.1\n"
			+ "pusher Dave Borowitz <dborowitz@google.com> 1435698874 -0700\n"
			+ "nonce 1435698874-40a5a8eb8f01f2427521\n"
			+ "\n"
			+ "6c2b981a177396fb47345b7df3e4d3f854c6bea7"
			+ " b6ce11496ab36260941ecf4e905506baba91268b"
			+ " refs/heads/master\n"
			+ "-----BEGIN PGP SIGNATURE-----\n"
			+ "Version: GnuPG v1\n"
			+ "\n"
			+ "iQEcBAABAgAGBQJVkwa6AAoJEPfTicJkUdPklyIH/23yLNFlFxG67S00cEMXhc7t\n"
			+ "IQ8IbLknaPofDlI+qKE8SYhpNWx2M3AAsUW49YBwIg86QwsRbMvoE42TkheEPKoT\n"
			+ "iYu5OfyAuu7bLF3Qrl6zs6xftLPegRcBZ9djjBjh6U+qkgnp7puNgpw1UGfY87Fq\n"
			+ "gq/wD+IyD3Dni9E21fMEHntdTmOqZELvOBf2KXMDeSIQewUq6hOpzMt9/w0umucv\n"
			+ "SOA4S1+c4tZGswZVIGNLfyHbDDUxLYMoZbPo09wamNMVqjpdew23Xe/+pit1jhMs\n"
			+ "8AJ1iaPnU+X/cicCr6L/Z1eaXMeoK+TdyYRBHdVR5YY04bPYH2Wz2un/XhmYjmo=\n"
			+ "=YKLa\n"
			+ "-----END PGP SIGNATURE-----\n");

	private static PushCertificate parseCertificate(String str) {
		try {
			return PushCertificateParser.fromReader(new InputStreamReader(
					new ByteArrayInputStream(Constants.encode(str))));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private AtomicInteger ts = new AtomicInteger(1433954361);
	private InMemoryRepository repo;
	private PushCertificateStore store;

	@Before
	public void setUp() {
		repo = new InMemoryRepository(new DfsRepositoryDescription("repo"));
		store = newStore();
	}

	@Test
	public void missingRef() throws Exception {
		assertEquals(Collections.emptyList()
	}

	@Test
	public void saveOneCert() throws Exception {
		store.add("refs/heads/master"
		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master"));

		assertEquals(RefUpdate.Result.NEW

		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master")
		assertCertsEqual(store.get("refs/heads/branch"));
		assertCertsEqual(newStore().get("refs/heads/branch"));
	}

	@Test
	public void saveTwoCertsOnSameRefInSameUpdate() throws Exception {
		store.add("refs/heads/master"
		store.add("refs/heads/master"
		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master"));

		assertEquals(RefUpdate.Result.NEW

		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master")
		assertCertsEqual(store.get("refs/heads/branch"));
		assertCertsEqual(newStore().get("refs/heads/branch"));
	}

	@Test
	public void saveTwoCertsOnDifferentRefsInSameUpdate() throws Exception {
		store.add("refs/heads/master"
		store.add("refs/heads/branch"
		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master"));
		assertCertsEqual(store.get("refs/heads/branch")
		assertCertsEqual(newStore().get("refs/heads/master"));

		assertEquals(RefUpdate.Result.NEW

		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master")
		assertCertsEqual(store.get("refs/heads/branch")
		assertCertsEqual(newStore().get("refs/heads/branch")
	}

	@Test
	public void saveTwoCertsOnSameRefInDifferentUpdate() throws Exception {
		store.add("refs/heads/master"
		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master"));

		assertEquals(RefUpdate.Result.NEW

		store.add("refs/heads/master"
		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master")

		assertEquals(RefUpdate.Result.FAST_FORWARD

		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master")
		assertCertsEqual(store.get("refs/heads/branch"));
		assertCertsEqual(newStore().get("refs/heads/branch"));
	}

	@Test
	public void saveTwoCertsOnDifferentRefsInDifferentUpdate() throws Exception {
		store.add("refs/heads/master"
		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master"));

		assertEquals(RefUpdate.Result.NEW

		store.add("refs/heads/branch"
		assertCertsEqual(store.get("refs/heads/branch")
		assertCertsEqual(newStore().get("refs/heads/branch"));

		assertEquals(RefUpdate.Result.FAST_FORWARD

		assertCertsEqual(store.get("refs/heads/master")
		assertCertsEqual(newStore().get("refs/heads/master")
		assertCertsEqual(store.get("refs/heads/branch")
		assertCertsEqual(newStore().get("refs/heads/branch")
	}

	@Test
	public void saveCertWithLockFailure() throws Exception {
		store.add("refs/heads/master"
		assertEquals(RefUpdate.Result.NEW

		store.add("refs/heads/master"

		RefUpdate ru = repo.updateRef("refs/push-certs");
		ru.setForceUpdate(true);
		assertEquals(RefUpdate.Result.FORCED

		assertEquals(RefUpdate.Result.LOCK_FAILURE
		assertEquals(RefUpdate.Result.NEW
		assertCertsEqual(store.get("refs/heads/master")
	}

	private CommitBuilder newCommitBuilder() {
		PersonIdent ident = new PersonIdent(
				"A U. Thor"
		CommitBuilder cb = new CommitBuilder();
		cb.setAuthor(ident);
		cb.setCommitter(ident);
		cb.setMessage("Store push certificates");
		return cb;
	}

	private PushCertificateStore newStore() {
		return new PushCertificateStore(repo);
	}

	private static void assertCertsEqual(List<PushCertificate> actual
			PushCertificate... expected) {
		assertEquals(expected.length
		for (int i = 0; i < expected.length; i++) {
			assertEquals("text of cert " + i
					expected[i].toText()
			assertEquals("signature of cert " + i
					expected[i].getSignature()
		}
	}
}
