
package org.eclipse.jgit.internal.storage.file;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.revwalk.RevBlob;
import org.junit.Test;

public class GcPackRefsTest extends GcTestCase {
	@Test
	public void looseRefPacked() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"

		gc.packRefs();
		assertSame(repo.getRef("t").getStorage()
	}

	@Test
	public void concurrentOnlyOneWritesPackedRefs() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"

		final CyclicBarrier syncPoint = new CyclicBarrier(2);

		Callable<Integer> packRefs = new Callable<Integer>() {

			public Integer call() throws Exception {
				syncPoint.await();
				try {
					gc.packRefs();
					return valueOf(0);
				} catch (IOException e) {
					return valueOf(1);
				}
			}
		};
		ExecutorService pool = Executors.newFixedThreadPool(2);
		try {
			Future<Integer> p1 = pool.submit(packRefs);
			Future<Integer> p2 = pool.submit(packRefs);
			assertEquals(1
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
				"refs/tags/t1")
		try {
			refLock.lock();
			gc.packRefs();
		} finally {
			refLock.unlock();
		}

		assertSame(repo.getRef("refs/tags/t1").getStorage()
		assertSame(repo.getRef("refs/tags/t2").getStorage()
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

				public Result call() throws Exception {
					RefUpdate update = new RefDirectoryUpdate(
							(RefDirectory) repo.getRefDatabase()
							repo.getRef("refs/tags/t")) {
						@Override
						public boolean isForceUpdate() {
							try {
								refUpdateLockedRef.await();
								packRefsDone.await();
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							} catch (BrokenBarrierException e) {
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

		assertEquals(repo.getRef("refs/tags/t").getObjectId()
	}
}
