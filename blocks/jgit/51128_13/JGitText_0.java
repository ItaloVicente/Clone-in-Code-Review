
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.lib.ObjectId.zeroId;
import static org.eclipse.jgit.lib.RefUpdate.Result.FAST_FORWARD;
import static org.eclipse.jgit.lib.RefUpdate.Result.LOCK_FAILURE;
import static org.eclipse.jgit.lib.RefUpdate.Result.NEW;
import static org.eclipse.jgit.lib.RefUpdate.Result.NO_CHANGE;
import static org.junit.Assert.assertEquals;

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
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Before;
import org.junit.Test;

public class PushCertificateStoreTest {
	private static final ObjectId ID1 =
		ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");

	private static final ObjectId ID2 =
		ObjectId.fromString("badc0ffebadc0ffebadc0ffebadc0ffebadc0ffe");

	private static PushCertificate newCert(String... updateLines) {
		StringBuilder cert = new StringBuilder(
				"certificate version 0.1\n"
				+ "pusher Dave Borowitz <dborowitz@google.com> 1433954361 -0700\n"
				+ "nonce 1433954361-bde756572d665bba81d8\n"
				+ "\n");
		for (String updateLine : updateLines) {
			cert.append(updateLine).append('\n');
		}
		cert.append(
				"-----BEGIN PGP SIGNATURE-----\n"
				+ "DUMMY/SIGNATURE\n"
				+ "-----END PGP SIGNATURE-----\n");
		try {
			return PushCertificateParser.fromReader(new InputStreamReader(
					new ByteArrayInputStream(Constants.encode(cert.toString()))));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static String command(ObjectId oldId
		return oldId.name() + " " + newId.name() + " " + ref;
	}

	private AtomicInteger ts = new AtomicInteger(1433954361);
	private InMemoryRepository repo;
	private PushCertificateStore store;

	@Before
	public void setUp() throws Exception {
		repo = new InMemoryRepository(new DfsRepositoryDescription("repo"));
		store = newStore();
	}

	@Test
	public void missingRef() throws Exception {
		assertCerts("refs/heads/master");
	}

	@Test
	public void saveNoChange() throws Exception {
		assertEquals(NO_CHANGE
	}

	@Test
	public void saveOneCertOnOneRef() throws Exception {
		PersonIdent ident = newIdent();
		PushCertificate addMaster = newCert(
				command(zeroId()
		store.put(addMaster
		assertEquals(NEW
		assertCerts("refs/heads/master"
		assertCerts("refs/heads/branch");

		try (RevWalk rw = new RevWalk(repo)) {
			RevCommit c = rw.parseCommit(repo.resolve(PushCertificateStore.REF_NAME));
			rw.parseBody(c);
			assertEquals("Store push certificate for refs/heads/master\n"
					c.getFullMessage());
			assertEquals(ident
			assertEquals(ident
		}
	}

	@Test
	public void saveTwoCertsOnSameRefInTwoUpdates() throws Exception {
		PushCertificate addMaster = newCert(
				command(zeroId()
		store.put(addMaster
		assertEquals(NEW
		PushCertificate updateMaster = newCert(
				command(ID1
		store.put(updateMaster
		assertEquals(FAST_FORWARD
		assertCerts("refs/heads/master"
	}

	@Test
	public void saveTwoCertsOnSameRefInOneUpdate() throws Exception {
		PersonIdent ident1 = newIdent();
		PersonIdent ident2 = newIdent();
		PushCertificate updateMaster = newCert(
				command(ID1
		store.put(updateMaster
		PushCertificate addMaster = newCert(
				command(zeroId()
		store.put(addMaster
		assertEquals(NEW
		assertCerts("refs/heads/master"
	}

	@Test
	public void saveTwoCertsOnDifferentRefsInOneUpdate() throws Exception {
		PersonIdent ident1 = newIdent();
		PersonIdent ident3 = newIdent();
		PushCertificate addBranch = newCert(
				command(zeroId()
		store.put(addBranch
		PushCertificate addMaster = newCert(
				command(zeroId()
		store.put(addMaster
		assertEquals(NEW
		assertCerts("refs/heads/master"
		assertCerts("refs/heads/branch"
	}

	@Test
	public void saveTwoCertsOnDifferentRefsInTwoUpdates() throws Exception {
		PushCertificate addMaster = newCert(
				command(zeroId()
		store.put(addMaster
		assertEquals(NEW
		PushCertificate addBranch = newCert(
				command(zeroId()
		store.put(addBranch
		assertEquals(FAST_FORWARD
		assertCerts("refs/heads/master"
		assertCerts("refs/heads/branch"
	}

	@Test
	public void saveOneCertOnMultipleRefs() throws Exception {
		PersonIdent ident = newIdent();
		PushCertificate addMasterAndBranch = newCert(
				command(zeroId()
				command(zeroId()
		store.put(addMasterAndBranch
		assertEquals(NEW
		assertCerts("refs/heads/master"
		assertCerts("refs/heads/branch"

		try (RevWalk rw = new RevWalk(repo)) {
			RevCommit c = rw.parseCommit(repo.resolve(PushCertificateStore.REF_NAME));
			rw.parseBody(c);
			assertEquals("Store push certificate for 2 refs\n"
			assertEquals(ident
			assertEquals(ident
		}
	}

	@Test
	public void changeRefFileToDirectory() throws Exception {
		PushCertificate deleteRefsHeads = newCert(
				command(ID1
		store.put(deleteRefsHeads
		PushCertificate addMaster = newCert(
				command(zeroId()
		store.put(addMaster
		assertEquals(NEW
		assertCerts("refs/heads"
		assertCerts("refs/heads/master"
	}

	@Test
	public void getBeforeSaveDoesNotIncludePending() throws Exception {
		PushCertificate addMaster = newCert(
				command(zeroId()
		store.put(addMaster
		assertEquals(NEW

		PushCertificate updateMaster = newCert(
				command(ID1
		store.put(updateMaster

		assertCerts("refs/heads/master"
		assertEquals(FAST_FORWARD
		assertCerts("refs/heads/master"
	}

	@Test
	public void lockFailure() throws Exception {
		PushCertificateStore store1 = store;
		PushCertificateStore store2 = newStore();
		store2.get("refs/heads/master");

		PushCertificate addMaster = newCert(
				command(zeroId()
		store1.put(addMaster
		assertEquals(NEW

		PushCertificate addBranch = newCert(
				command(zeroId()
		store2.put(addBranch

		assertEquals(LOCK_FAILURE
		assertCerts(store2
		assertCerts(store2

		assertEquals(FAST_FORWARD
		assertCerts(store2
		assertCerts(store2
	}

	private PersonIdent newIdent() {
		return new PersonIdent(
				"A U. Thor"
	}

	private PushCertificateStore newStore() {
		return new PushCertificateStore(repo);
	}

	private void assertCerts(String refName
			throws Exception {
		assertCerts(store
		assertCerts(newStore()
	}

	private static void assertCerts(PushCertificateStore store
			PushCertificate... expected) throws Exception {
		List<PushCertificate> ex = Arrays.asList(expected);
		PushCertificate first = !ex.isEmpty() ? ex.get(0) : null;
		assertEquals(first
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
