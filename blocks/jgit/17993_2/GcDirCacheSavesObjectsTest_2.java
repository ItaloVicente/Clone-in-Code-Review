
package org.eclipse.jgit.internal.storage.file;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.EmptyProgressMonitor;
import org.eclipse.jgit.revwalk.RevBlob;
import org.junit.Test;

public class GcConcurrentTest extends GcTestCase {
	@Test
	public void concurrentRepack() throws Exception {
		final CyclicBarrier syncPoint = new CyclicBarrier(2);

		class DoRepack extends EmptyProgressMonitor implements
				Callable<Integer> {

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
}
