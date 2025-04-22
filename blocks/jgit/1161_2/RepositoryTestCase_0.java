package org.eclipse.jgit.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeSet;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIteratorWithTimeControl;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;

public class RacyGitTests extends RepositoryTestCase {
	public void testIterator() throws IllegalStateException
			InterruptedException {
		TreeSet<Long> modTimes = new TreeSet<Long>();
		File lastFile = null;
		for (int i = 0; i < 10; i++) {
			lastFile = new File(db.getWorkDir()
			lastFile.createNewFile();
			if (i == 5)
				fsTick(lastFile);
		}
		modTimes.add(fsTick(lastFile));
		for (int i = 0; i < 10; i++) {
			lastFile = new File(db.getWorkDir()
			lastFile.createNewFile();
		}
		modTimes.add(fsTick(lastFile));
		for (int i = 0; i < 10; i++) {
			lastFile = new File(db.getWorkDir()
			lastFile.createNewFile();
			if (i % 4 == 0)
				fsTick(lastFile);
		}
		FileTreeIteratorWithTimeControl fileIt = new FileTreeIteratorWithTimeControl(
				db
		NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.reset();
		tw.addTree(fileIt);
		tw.setRecursive(true);
		FileTreeIterator t;
		long t0 = 0;
		for (int i = 0; i < 10; i++) {
			assertTrue(tw.next());
			t = tw.getTree(0
			if (i == 0)
				t0 = t.getEntryLastModified();
			else
				assertEquals(t0
		}
		long t1 = 0;
		for (int i = 0; i < 10; i++) {
			assertTrue(tw.next());
			t = tw.getTree(0
			if (i == 0) {
				t1 = t.getEntryLastModified();
				assertTrue(t1 > t0);
			} else
				assertEquals(t1
		}
		long t2 = 0;
		for (int i = 0; i < 10; i++) {
			assertTrue(tw.next());
			t = tw.getTree(0
			if (i == 0) {
				t2 = t.getEntryLastModified();
				assertTrue(t2 > t1);
			} else
				assertEquals(t2
		}
	}

	public void testRacyGitDetection() throws IOException
			IllegalStateException
		DirCache dc;
		TreeSet<Long> modTimes = new TreeSet<Long>();
		File lastFile;

		modTimes.add(fsTick(db.getIndexFile()));

		addToWorkDir("a"
		lastFile = addToWorkDir("b"

		modTimes.add(fsTick(lastFile));

		addToIndex(modTimes);

		dc = DirCache.read(db);
		assertEquals("[[a

		modTimes.add(Long.valueOf(db.getIndexFile().lastModified()));

		addToWorkDir("a"
		addToIndex(modTimes);

		dc = DirCache.read(db);
		assertTrue(dc.getEntryCount() == 2);
		assertTrue(dc.getEntry("a").isSmudged());
		assertFalse(dc.getEntry("b").isSmudged());

		assertEquals("[[a
		assertEquals("[[a

	}

	public static long fsTick(File lastFile) throws InterruptedException
			IOException {
		long sleepTime = 1;
		File tmp = File.createTempFile("FileTreeIteratorWithTimeControl"
		try {
			long startTime = (lastFile == null) ? tmp.lastModified() : lastFile
					.lastModified();
			long actTime = tmp.lastModified();
			while (actTime <= startTime) {
				Thread.sleep(sleepTime);
				sleepTime *= 5;
				tmp.setLastModified(System.currentTimeMillis());
				actTime = tmp.lastModified();
			}
			return actTime;
		} finally {
			tmp.delete();
		}
	}

	private void addToIndex(TreeSet<Long> modTimes)
			throws FileNotFoundException
		DirCacheBuilder builder = DirCache.lock(db).builder();
		FileTreeIterator fIt = new FileTreeIteratorWithTimeControl(
				db
		DirCacheEntry dce;
		while (!fIt.eof()) {
			dce = new DirCacheEntry(fIt.getEntryPathString());
			dce.setFileMode(fIt.getEntryFileMode());
			dce.setLastModified(fIt.getEntryLastModified());
			dce.setLength((int) fIt.getEntryLength());
			dce.setObjectId(fIt.getEntryObjectId());
			builder.add(dce);
			fIt.next(1);
		}
		builder.commit();
	}

	private File addToWorkDir(String path
		File f = new File(db.getWorkDir()
		FileOutputStream fos = new FileOutputStream(f);
		try {
			fos.write(content.getBytes(Constants.CHARACTER_ENCODING));
			return f;
		} finally {
			fos.close();
		}
	}
}
