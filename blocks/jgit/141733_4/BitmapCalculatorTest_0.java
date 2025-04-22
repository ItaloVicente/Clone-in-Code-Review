package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.internal.storage.file.GC;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.junit.Before;
import org.junit.Test;

public class BitmapCalculatorTest extends LocalDiskRepositoryTestCase {
	TestRepository<FileRepository> repo;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		FileRepository db = createWorkRepository();
		repo = new TestRepository<>(db);
	}

	@Test
	public void addOnlyCommits() throws Exception {
		RevBlob abBlob = repo.blob("a_b_content");
		RevCommit root = repo.commit().add("a/b"
		repo.update("refs/heads/master"

		GC gc = new GC(repo.getRepository());
		gc.setAuto(false);
		gc.gc();

		RevBlob acBlob = repo.blob("a_c_content");
		RevCommit head = repo.commit().parent(root).add("a/c"
		repo.update("refs/heads/master"

		BitmapCalculator bitmapWalker = new BitmapCalculator(repo.getRevWalk());
		BitmapBuilder bitmap = bitmapWalker
				.getBitmap(head

		assertTrue(bitmap.contains(root.getId()));
		assertTrue(bitmap.contains(root.getTree().getId()));
		assertTrue(bitmap.contains(abBlob.getId()));

		assertTrue(bitmap.contains(head.getId()));
		assertFalse(bitmap.contains(head.getTree().getId()));
		assertFalse(bitmap.contains(acBlob.getId()));
	}

	@Test
	public void walkUntilBitmap() throws Exception {
		RevCommit root = repo.commit().create();
		repo.update("refs/heads/master"

		GC gc = new GC(repo.getRepository());
		gc.setAuto(false);
		gc.gc();

		RevCommit commit1 = repo.commit(root);
		RevCommit commit2 = repo.commit(commit1);
		repo.update("refs/heads/master"

		CounterProgressMonitor monitor = new CounterProgressMonitor();
		BitmapCalculator bitmapWalker = new BitmapCalculator(repo.getRevWalk());
		BitmapBuilder bitmap = bitmapWalker.getBitmap(commit2

		assertTrue(bitmap.contains(root));
		assertTrue(bitmap.contains(commit1));
		assertTrue(bitmap.contains(commit2));
		assertEquals(2
	}

	@Test
	public void noNeedToWalk() throws Exception {
		RevCommit root = repo.commit().create();
		RevCommit commit1 = repo.commit(root);
		RevCommit commit2 = repo.commit(commit1);
		repo.update("refs/heads/master"

		GC gc = new GC(repo.getRepository());
		gc.setAuto(false);
		gc.gc();

		CounterProgressMonitor monitor = new CounterProgressMonitor();
		BitmapCalculator bitmapWalker = new BitmapCalculator(repo.getRevWalk());
		BitmapBuilder bitmap = bitmapWalker.getBitmap(commit2

		assertTrue(bitmap.contains(root));
		assertTrue(bitmap.contains(commit1));
		assertTrue(bitmap.contains(commit2));
		assertEquals(0
	}

	private static class CounterProgressMonitor implements ProgressMonitor {

		private int counter;

		@Override
		public void start(int totalTasks) {
		}

		@Override
		public void beginTask(String title
		}

		@Override
		public void update(int completed) {
			counter += 1;
		}

		@Override
		public void endTask() {
		}

		@Override
		public boolean isCancelled() {
			return false;
		}

		int getUpdates() {
			return counter;
		}
	}
}
