
package org.eclipse.jgit.internal.storage.file;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.errors.CancelledException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.EmptyProgressMonitor;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Sets;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.junit.Test;

public class GcConcurrentTest extends GcTestCase {
	@Test
	public void concurrentRepack() throws Exception {
		final CyclicBarrier syncPoint = new CyclicBarrier(2);

		class DoRepack extends EmptyProgressMonitor implements
				Callable<Integer> {

			@Override
			public void beginTask(String title
				if (title.equals(JGitText.get().writingObjects)) {
					try {
						syncPoint.await();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} catch (BrokenBarrierException ignored) {
					}
				}
			}

			@Override
			public Integer call() throws Exception {
				try {
					gc.setProgressMonitor(this);
					gc.repack();
					return valueOf(0);
				} catch (IOException e) {
					Thread.currentThread().interrupt();
					try {
						syncPoint.await();
					} catch (InterruptedException ignored) {
					}
					return valueOf(1);
				}
			}
		}

		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"

		ExecutorService pool = Executors.newFixedThreadPool(2);
		try {
			DoRepack repack1 = new DoRepack();
			DoRepack repack2 = new DoRepack();
			Future<Integer> result1 = pool.submit(repack1);
			Future<Integer> result2 = pool.submit(repack2);
			assertEquals(0
		} finally {
			pool.shutdown();
			pool.awaitTermination(Long.MAX_VALUE
		}
	}

	@Test
	public void repackAndGetStats() throws Exception {
		TestRepository<FileRepository>.BranchBuilder test = tr.branch("test");
		test.commit().add("a"
		GC gc1 = new GC(tr.getRepository());
		gc1.setPackExpireAgeMillis(0);
		gc1.gc();
		test.commit().add("b"

		FileRepository r2 = new FileRepository(
				tr.getRepository().getDirectory());
		GC gc2 = new GC(r2);
		gc2.setPackExpireAgeMillis(0);
		gc2.gc();

		new GC(tr.getRepository()).getStatistics();
	}

	@Test
	public void repackAndUploadPack() throws Exception {
		TestRepository<FileRepository>.BranchBuilder test = tr.branch("test");
		test.commit().add("a"

		GC gc1 = new GC(tr.getRepository());
		gc1.setPackExpireAgeMillis(0);
		gc1.gc();

		RevCommit b = test.commit().add("b"

		FileRepository r2 = new FileRepository(
				tr.getRepository().getDirectory());
		GC gc2 = new GC(r2);
		gc2.setPackExpireAgeMillis(0);
		gc2.gc();

		try (PackWriter pw = new PackWriter(tr.getRepository())) {
			pw.setUseBitmaps(true);
			pw.preparePack(NullProgressMonitor.INSTANCE
					Collections.<ObjectId> emptySet());
			new GC(tr.getRepository()).getStatistics();
		}
	}

	PackFile getSinglePack(FileRepository r) {
		Collection<PackFile> packs = r.getObjectDatabase().getPacks();
		assertEquals(1
		return packs.iterator().next();
	}

	@Test
	public void repackAndCheckBitmapUsage() throws Exception {
		TestRepository<FileRepository>.BranchBuilder test = tr.branch("test");
		test.commit().add("a"
		FileRepository repository = tr.getRepository();
		GC gc1 = new GC(repository);
		gc1.setPackExpireAgeMillis(0);
		gc1.gc();
		String oldPackName = getSinglePack(repository).getPackName();
		RevCommit b = test.commit().add("b"

		FileRepository repository2 = new FileRepository(repository.getDirectory());
		GC gc2 = new GC(repository2);
		gc2.setPackExpireAgeMillis(0);
		gc2.gc();
		String newPackName = getSinglePack(repository2).getPackName();
		assertNotEquals(oldPackName

		assertNotEquals(getSinglePack(repository).getPackName()

		repository.getObjectDatabase().open(b).getSize();
		assertEquals(getSinglePack(repository).getPackName()
		assertNotNull(getSinglePack(repository).getBitmapIndex());
	}

	@Test
	public void testInterruptGc() throws Exception {
		FileBasedConfig c = repo.getConfig();
		c.setInt(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTOPACKLIMIT
		c.save();
		SampleDataRepositoryTestCase.copyCGitTestPacks(repo);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		final CountDownLatch latch = new CountDownLatch(1);
		Future<Collection<PackFile>> result = executor
				.submit(new Callable<Collection<PackFile>>() {

					@Override
					public Collection<PackFile> call() throws Exception {
						long start = System.currentTimeMillis();
						System.out.println("starting gc");
						latch.countDown();
						Collection<PackFile> r = gc.gc();
						System.out.println("gc took "
								+ (System.currentTimeMillis() - start) + " ms");
						return r;
					}
				});
		try {
			latch.await();
			Thread.sleep(5);
			executor.shutdownNow();
			result.get();
			fail("thread wasn't interrupted");
		} catch (ExecutionException e) {
			Throwable cause = e.getCause();
			if (cause instanceof CancelledException) {
				assertEquals(JGitText.get().operationCanceled
						cause.getMessage());
			} else if (cause instanceof IOException) {
				Throwable cause2 = cause.getCause();
				assertTrue(cause2 instanceof InterruptedException
						|| cause2 instanceof ExecutionException);
			} else {
				fail("unexpected exception " + e);
			}
		}
	}
}
