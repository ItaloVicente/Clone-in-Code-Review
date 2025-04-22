package org.eclipse.jgit.lib;

import static java.lang.Long.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeSet;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIteratorWithTimeControl;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class RacyGitTests extends RepositoryTestCase {
	@Test
	public void testIterator()
			throws IllegalStateException
		TreeSet<Long> modTimes = new TreeSet<>();
		File lastFile = null;
		for (int i = 0; i < 10; i++) {
			lastFile = new File(db.getWorkTree()
			FileUtils.createNewFile(lastFile);
			if (i == 5)
				fsTick(lastFile);
		}
		modTimes.add(valueOf(fsTick(lastFile)));
		for (int i = 0; i < 10; i++) {
			lastFile = new File(db.getWorkTree()
			FileUtils.createNewFile(lastFile);
		}
		modTimes.add(valueOf(fsTick(lastFile)));
		for (int i = 0; i < 10; i++) {
			lastFile = new File(db.getWorkTree()
			FileUtils.createNewFile(lastFile);
			if (i % 4 == 0)
				fsTick(lastFile);
		}
		FileTreeIteratorWithTimeControl fileIt = new FileTreeIteratorWithTimeControl(
				db
		try (NameConflictTreeWalk tw = new NameConflictTreeWalk(db)) {
			tw.addTree(fileIt);
			tw.setRecursive(true);
			FileTreeIterator t;
			long t0 = 0;
			for (int i = 0; i < 10; i++) {
				assertTrue(tw.next());
				t = tw.getTree(0
				if (i == 0) {
					t0 = t.getEntryLastModified();
				} else {
					assertEquals(t0
				}
			}
			long t1 = 0;
			for (int i = 0; i < 10; i++) {
				assertTrue(tw.next());
				t = tw.getTree(0
				if (i == 0) {
					t1 = t.getEntryLastModified();
					assertTrue(t1 > t0);
				} else {
					assertEquals(t1
				}
			}
			long t2 = 0;
			for (int i = 0; i < 10; i++) {
				assertTrue(tw.next());
				t = tw.getTree(0
				if (i == 0) {
					t2 = t.getEntryLastModified();
					assertTrue(t2 > t1);
				} else {
					assertEquals(t2
				}
			}
		}
	}

	@Test
	public void testRacyGitDetection() throws Exception {
		try (Git git = new Git(db)) {
			git.reset().call();
		}

		fsTick(db.getIndexFile());

		File a = addToWorkDir("a"
		File b = addToWorkDir("b"
		assertTrue(a.setLastModified(b.lastModified()));
		assertTrue(b.setLastModified(b.lastModified()));

		fsTick(b);

		resetIndex(new FileTreeIterator(db));

		assertEquals(
				"[a
						+ "[b
				indexState(SMUDGE | MOD_TIME | LENGTH | CONTENT));

		fsTick(db.getIndexFile());

		File updatedA = addToWorkDir("a"
		assertTrue(updatedA.setLastModified(updatedA.lastModified() + 100));
		resetIndex(new FileTreeIterator(db));
		assertTrue(db.getIndexFile()
				.setLastModified(updatedA.lastModified() + 90));

		db.readDirCache();
		assertEquals(
				"[a
						+ "[b
				indexState(SMUDGE | MOD_TIME | LENGTH | CONTENT));
	}

	private File addToWorkDir(String path
		File f = new File(db.getWorkTree()
		try (FileOutputStream fos = new FileOutputStream(f)) {
			fos.write(content.getBytes(UTF_8));
			return f;
		}
	}
}
