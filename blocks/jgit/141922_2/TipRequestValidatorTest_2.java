package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.storage.dfs.DfsGarbageCollector;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UploadPack.RequestValidator;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public abstract class RequestValidatorTestCase {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private RevCommit reachableCommit;

	private RevCommit tipAdvertisedCommit;

	private RevCommit tipUnadvertisedCommit;

	private RevCommit unreachableCommit;

	private RevBlob reachableBlob;

	private RevBlob unreachableBlob;

	private InMemoryRepository repo;

	protected abstract RequestValidator createValidator();

	@Before
	public void setUp() throws Exception {
		repo = new InMemoryRepository(new DfsRepositoryDescription());
		try (TestRepository<InMemoryRepository> git = new TestRepository<>(
				repo)) {
			reachableBlob = git.blob("foo");
			reachableCommit = git
					.commit(git.tree(git.file("foo"
			tipAdvertisedCommit = git.commit(reachableCommit);
			git.update("advertised"

			tipUnadvertisedCommit = git.commit(reachableCommit);
			git.update("unadvertised"

			unreachableBlob = git.blob("unreachableFoo");
			unreachableCommit = git
					.commit(git.tree(git.file("foo"
		}
	}

	protected abstract boolean isReachableCommitValid();

	protected abstract boolean isUnreachableCommitValid();

	protected abstract boolean isAdvertisedTipValid();

	protected abstract boolean isUnadvertisedTipCommitValid();

	protected abstract boolean isReachableBlobValid_withBitmaps();

	protected abstract boolean isReachableBlobValid_withoutBitmaps();

	protected abstract boolean isUnreachableBlobValid();

	@Test
	public void validateReachableCommitWithBitmaps()
			throws PackProtocolException
		if (!isReachableCommitValid()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers
					.containsString(
							"want " + reachableCommit.name() + " not valid"));

		}
		createValidator().checkWants(getUploadPack(getRepoWithBitmaps())
				Arrays.asList(reachableCommit));
	}

	@Test
	public void validateReachableCommitWithoutBitmaps()
			throws PackProtocolException
		if (!isReachableCommitValid()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + reachableCommit.name() + " not valid"));

		}
		createValidator().checkWants(getUploadPack(getRepoWithoutBitmaps())
				Arrays.asList(reachableCommit));
	}

	@Test
	public void validateAdvertisedTipWithBitmaps()
			throws PackProtocolException
		if (!isAdvertisedTipValid()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + tipAdvertisedCommit.name() + " not valid"));

		}
		createValidator().checkWants(getUploadPack(getRepoWithBitmaps())
				Arrays.asList(tipAdvertisedCommit));
	}

	@Test
	public void validateAdvertisedTipWithoutBitmaps()
			throws PackProtocolException
		if (!isAdvertisedTipValid()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + tipAdvertisedCommit.name() + " not valid"));

		}
		createValidator().checkWants(getUploadPack(getRepoWithoutBitmaps())
				Arrays.asList(tipAdvertisedCommit));
	}

	@Test
	public void validateUnadvertisedTipWithBitmaps()
			throws PackProtocolException
		if (!isUnadvertisedTipCommitValid()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + tipUnadvertisedCommit.name() + " not valid"));

		}
		createValidator().checkWants(getUploadPack(getRepoWithBitmaps())
				Arrays.asList(tipUnadvertisedCommit));
	}

	@Test
	public void validateUnadvertisedTipWithoutBitmaps()
			throws PackProtocolException
		if (!isUnadvertisedTipCommitValid()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + tipUnadvertisedCommit.name() + " not valid"));

		}
		createValidator().checkWants(getUploadPack(getRepoWithoutBitmaps())
				Arrays.asList(tipUnadvertisedCommit));
	}

	@Test
	public void validateUnreachableCommitWithBitmaps()
			throws PackProtocolException
		if (!isUnreachableCommitValid()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + unreachableCommit.name() + " not valid"));

		}
		createValidator().checkWants(getUploadPack(getRepoWithBitmaps())
				Arrays.asList(unreachableCommit));
	}

	@Test
	public void validateUnreachableCommitWithoutBitmaps()
			throws PackProtocolException
		if (!isUnreachableCommitValid()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + unreachableCommit.name() + " not valid"));

		}
		createValidator().checkWants(getUploadPack(getRepoWithoutBitmaps())
				Arrays.asList(unreachableCommit));
	}

	@Test
	public void validateReachableBlobWithBitmaps()
			throws PackProtocolException
		if (!isReachableBlobValid_withBitmaps()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + reachableBlob.name() + " not valid"));
		}
		createValidator().checkWants(getUploadPack(getRepoWithBitmaps())
				Arrays.asList(reachableBlob));
	}

	@Test
	public void validateReachableBlobWithoutBitmaps()
			throws PackProtocolException
		if (!isReachableBlobValid_withoutBitmaps()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + reachableBlob.name() + " not valid"));
		}
		createValidator().checkWants(getUploadPack(getRepoWithoutBitmaps())
				Arrays.asList(reachableBlob));
	}

	@Test
	public void validateUnreachableBlobWithBitmaps()
			throws PackProtocolException
		if (!isUnreachableBlobValid()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + unreachableBlob.name() + " not valid"));
		}
		createValidator().checkWants(getUploadPack(getRepoWithBitmaps())
				Arrays.asList(unreachableBlob));
	}

	@Test
	public void validateUnreachableBlobWithoutBitmaps()
			throws PackProtocolException
		if (!isUnreachableBlobValid()) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + unreachableBlob.name() + " not valid"));
		}
		createValidator().checkWants(getUploadPack(getRepoWithoutBitmaps())
				Arrays.asList(unreachableBlob));
	}

	private UploadPack getUploadPack(Repository repository) throws IOException {
		UploadPack uploadPack = new UploadPack(repository);

		Ref advertisedRef = repo.getRefDatabase().findRef("advertised");
		Map<String
		advertisedRefs.put(advertisedRef.getName()

		uploadPack.setAdvertisedRefs(advertisedRefs);
		return uploadPack;
	}

	private Repository getRepoWithBitmaps() throws IOException {
		new DfsGarbageCollector(repo).pack(null);
		repo.scanForRepoChanges();
		return repo;
	}

	private Repository getRepoWithoutBitmaps() {
		return repo;
	}
}
