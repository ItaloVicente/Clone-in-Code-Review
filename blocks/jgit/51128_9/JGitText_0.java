
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.RefUpdate.Result.FAST_FORWARD;
import static org.eclipse.jgit.lib.RefUpdate.Result.NEW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Before;
import org.junit.Test;

public class PushCertificateStoreTest {
	private static final ObjectId ID1 =
		ObjectId.fromString("063488a5115244f1345bc64713b6c3d9ecaa2900");

	private static final String COMMIT1 =
			"tree 205f6b799e7d5c2524468ca006a0131aa57ecce7\n"
			+ "author Dave Borowitz <dborowitz@google.com> 1436282673 -0400\n"
			+ "committer Dave Borowitz <dborowitz@google.com> 1436282673 -0400\n"
			+ "\n"
			+ "Add foo\n";

	private static final ObjectId ID2 =
			ObjectId.fromString("f4f8178d0e6b9a58cefbe66577b7d479a0430ddd");

	private static final String COMMIT2 =
			"tree 89ff1a2aefcbff0f09197f0fd8beeb19a7b6e51c\n"
			+ "parent " + ID1.name() + "\n"
			+ "author Dave Borowitz <dborowitz@google.com> 1436282684 -0400\n"
			+ "committer Dave Borowitz <dborowitz@google.com> 1436282684 -0400\n"
			+ "\n"
			+ "Add bar\n";

	private static final PushCertificate CERT1 = parseCertificate(
			"certificate version 0.1\n"
			+ "pusher Dave Borowitz <dborowitz@google.com> 1433954361 -0700\n"
			+ "nonce 1433954361-bde756572d665bba81d8\n"
			+ "\n"
			+ ObjectId.zeroId().name() + " " + ID1.name() + " refs/heads/master\n"
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
			+ ID1.name() + " " + ID2.name() + " refs/heads/master\n"
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

	private static final PushCertificate CERT3 = parseCertificate(
			"certificate version 0.1\n"
			+ "pusher Dave Borowitz <dborowitz@google.com> 1433954361 -0700\n"
			+ "nonce 1433954361-bde756572d665bba81d8\n"
			+ "\n"
			+ ObjectId.zeroId().name() + " " + ID1.name() + " refs/heads/branch\n"
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
	public void setUp() throws Exception {
		repo = new InMemoryRepository(new DfsRepositoryDescription("repo"));
		try (ObjectInserter ins = repo.newObjectInserter()) {
			assertEquals(ID1
			assertEquals(ID2
			ins.flush();
		}
		store = newStore();
	}

	@Test
	public void missingRef() throws Exception {
		assertCerts("refs/heads/master");
	}

	@Test
	public void saveOneCert() throws Exception {
		PersonIdent ident = newIdent();
		updateRef("refs/heads/master"
		assertEquals(NEW
		assertCerts("refs/heads/master"
		assertCerts("refs/heads/branch");

		try (RevWalk rw = new RevWalk(repo)) {
			RevCommit c = rw.parseCommit(repo.resolve(PushCertificateStore.REF_NAME));
			rw.parseBody(c);
			assertEquals(
					"Store push certificate for refs/heads/master\n"
						+ "\n"
						+ CERT1.toText()
						+ CERT1.getSignature()
					c.getFullMessage());
			assertEquals(ident
			assertEquals(ident
		}
	}

	@Test
	public void saveTwoCertsOnSameRef() throws Exception {
		updateRef("refs/heads/master"
		assertEquals(NEW
		updateRef("refs/heads/master"
		assertEquals(FAST_FORWARD
		assertCerts("refs/heads/master"
	}

	@Test
	public void saveTwoCertsOnDifferentRefs() throws Exception {
		updateRef("refs/heads/master"
		assertEquals(NEW
		updateRef("refs/heads/branch"
		assertEquals(FAST_FORWARD
		assertCerts("refs/heads/master"
		assertCerts("refs/heads/branch"
	}

	@Test
	public void ignoreMismatchedRef() throws Exception {
		updateRef("refs/heads/master"
		assertEquals(NEW

		assertCerts("refs/heads/master");
		try (RevWalk rw = new RevWalk(repo)) {
			RevCommit c = rw.parseCommit(repo.resolve(PushCertificateStore.REF_NAME));
			rw.parseBody(c);
			assertEquals(
					"Store push certificate for refs/heads/master\n"
						+ "\n"
						+ CERT1.toText()
						+ CERT1.getSignature()
					c.getFullMessage());
		}
	}

	private PersonIdent newIdent() {
		return new PersonIdent(
				"A U. Thor"
	}

	private PushCertificateStore newStore() {
		return new PushCertificateStore(repo);
	}

	private void updateRef(String name
		RefUpdate ru = repo.updateRef(name);
		ru.setNewObjectId(id);
		RefUpdate.Result result = ru.forceUpdate();
		switch (result) {
			case FAST_FORWARD:
			case FORCED:
			case NEW:
			case NO_CHANGE:
			default:
				fail(result.name());
				break;
		}
	}

	private void assertCerts(String refName
			throws Exception {
		PushCertificateStore newStore = newStore();
		List<PushCertificate> ex = Arrays.asList(expected);
		PushCertificate first = !ex.isEmpty() ? ex.get(0) : null;
		assertEquals(first
		assertEquals(first
		assertEquals(ex
		assertEquals(ex
	}

	private static <T> List<T> toList(Iterable<T> it) {
		List<T> list = new ArrayList<>();
		for (T t : it) {
			list.add(t);
		}
		return list;
	}
}
