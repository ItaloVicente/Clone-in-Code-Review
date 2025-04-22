
package org.eclipse.jgit.internal.storage.file;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

@SuppressWarnings("boxing")
public class GcPackRefsTest extends GcTestCase {
	@Test
	public void looseRefPacked() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"

		gc.packRefs();
		assertSame(repo.exactRef("refs/tags/t").getStorage()
	}

	@Test
	public void emptyRefDirectoryDeleted() throws Exception {
		String ref = "dir/ref";
		tr.branch(ref).commit().create();
		String name = repo.findRef(ref).getName();
		Path dir = repo.getDirectory().toPath().resolve(name).getParent();
		assertNotNull(dir);
		gc.packRefs();
		assertFalse(Files.exists(dir));
	}

	@Test
	public void concurrentOnlyOneWritesPackedRefs() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"

		CyclicBarrier syncPoint = new CyclicBarrier(2);

		Callable<Integer> packRefs = () -> {
			syncPoint.await();
			try {
				gc.packRefs();
				return 0;
			} catch (IOException e) {
				return 1;
			}
		};
		ExecutorService pool = Executors.newFixedThreadPool(2);
		try {
			Future<Integer> p1 = pool.submit(packRefs);
			Future<Integer> p2 = pool.submit(packRefs);
			assertThat(p1.get() + p2.get()
		} finally {
			pool.shutdown();
			pool.awaitTermination(Long.MAX_VALUE
		}
	}

	@Test
	public void whileRefLockedRefNotPackedNoError()
			throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t1"
		tr.lightweightTag("t2"
		LockFile refLock = new LockFile(new File(repo.getDirectory()
				"refs/tags/t1"));
		try {
			refLock.lock();
			gc.packRefs();
		} finally {
			refLock.unlock();
		}

		assertSame(repo.exactRef("refs/tags/t1").getStorage()
		assertSame(repo.exactRef("refs/tags/t2").getStorage()
	}

	@Test
	public void whileRefUpdatedRefUpdateSucceeds()
			throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"
		final RevBlob b = tr.blob("b");

		final CyclicBarrier refUpdateLockedRef = new CyclicBarrier(2);
		final CyclicBarrier packRefsDone = new CyclicBarrier(2);
		ExecutorService pool = Executors.newFixedThreadPool(2);
		try {
			Future<Result> result = pool.submit(new Callable<Result>() {

				@Override
				public Result call() throws Exception {
					RefUpdate update = new RefDirectoryUpdate(
							(RefDirectory) repo.getRefDatabase()
							repo.exactRef("refs/tags/t")) {
						@Override
						public boolean isForceUpdate() {
							try {
								refUpdateLockedRef.await();
								packRefsDone.await();
							} catch (InterruptedException | BrokenBarrierException e) {
								Thread.currentThread().interrupt();
							}
							return super.isForceUpdate();
						}
					};
					update.setForceUpdate(true);
					update.setNewObjectId(b);
					return update.update();
				}
			});

			pool.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					refUpdateLockedRef.await();
					gc.packRefs();
					packRefsDone.await();
					return null;
				}
			});

			assertSame(result.get()

		} finally {
			pool.shutdownNow();
			pool.awaitTermination(Long.MAX_VALUE
		}

		assertEquals(repo.exactRef("refs/tags/t").getObjectId()
	}

	@Test
	public void dontPackHEAD_nonBare() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/side");
		RevCommit first = bb.commit().add("A"
		bb.commit().add("A"
		Git git = Git.wrap(repo);

		assertEquals(repo.exactRef("HEAD").getTarget().getName()
				"refs/heads/master");
		assertNull(repo.exactRef("HEAD").getTarget().getObjectId());
		gc.packRefs();
		assertSame(repo.exactRef("HEAD").getStorage()
		assertEquals(repo.exactRef("HEAD").getTarget().getName()
				"refs/heads/master");
		assertNull(repo.exactRef("HEAD").getTarget().getObjectId());

		git.checkout().setName("refs/heads/side").call();
		gc.packRefs();
		assertSame(repo.exactRef("HEAD").getStorage()

		git.checkout().setName(first.getName()).call();
		gc.packRefs();
		assertSame(repo.exactRef("HEAD").getStorage()
	}

	@Test
	public void dontPackHEAD_bare() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/side");
		bb.commit().add("A"
		RevCommit second = bb.commit().add("A"

		FileBasedConfig cfg = repo.getConfig();
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		cfg.save();
		Git git = Git.open(repo.getDirectory());
		repo = (FileRepository) git.getRepository();

		assertEquals(repo.exactRef("HEAD").getTarget().getName()
				"refs/heads/master");
		assertNull(repo.exactRef("HEAD").getTarget().getObjectId());
		gc.packRefs();
		assertSame(repo.exactRef("HEAD").getStorage()
		assertEquals(repo.exactRef("HEAD").getTarget().getName()
				"refs/heads/master");
		assertNull(repo.exactRef("HEAD").getTarget().getObjectId());

		repo.updateRef(Constants.HEAD).link("refs/heads/side");
		gc.packRefs();
		assertSame(repo.exactRef("HEAD").getStorage()
		assertEquals(repo.exactRef("HEAD").getTarget().getObjectId()
				second.getId());
	}
}
