package org.eclipse.jgit.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class ResetCommandTest extends RepositoryTestCase {

	private Git git;

	private RevCommit initialCommit;

	private File indexFile;

	private File untrackedFile;

	@Before
	public void setupRepository() throws IOException
			NoHeadException
			JGitInternalException

		git = new Git(db);
		initialCommit = git.commit().setMessage("initial commit").call();

		indexFile = new File(db.getWorkTree()
		FileUtils.createNewFile(indexFile);
		PrintWriter writer = new PrintWriter(indexFile);
		writer.print("content");
		writer.close();

		git.add().addFilepattern("a.txt").call();
		git.commit().setMessage("adding a.txt").call();

		PrintWriter writer3 = new PrintWriter(indexFile);
		writer3.print("content");
		writer3.close();
		git.add().addFilepattern("a.txt").call();

		untrackedFile = new File(db.getWorkTree()
				"notAddedToIndex.txt");
		FileUtils.createNewFile(untrackedFile);
		PrintWriter writer2 = new PrintWriter(untrackedFile);
		writer2.print("content");
		writer2.close();
	}

	@Test
	public void testHardReset() throws JGitInternalException
			AmbiguousObjectException
		git.reset().setMode(ResetType.HARD).setRef(initialCommit.getName())
				.call();

		ObjectId head = db.resolve(Constants.HEAD);
		assertTrue(head.equals(initialCommit));
		assertFalse(untrackedFile.exists());
		assertFalse(indexFile.exists());
		String fileInIndexPath = indexFile.getAbsolutePath();
		assertFalse(inHead(fileInIndexPath));
		assertFalse(inIndex(fileInIndexPath));
	}

	@Test
	public void testSoftReset() throws JGitInternalException
			AmbiguousObjectException
		git.reset().setMode(ResetType.SOFT).setRef(initialCommit.getName())
				.call();
		ObjectId head = db.resolve(Constants.HEAD);
		assertTrue(head.equals(initialCommit));
		assertTrue(untrackedFile.exists());
		assertTrue(indexFile.exists());
		String fileInIndexPath = indexFile.getAbsolutePath();
		assertFalse(inHead(fileInIndexPath));
		assertTrue(inIndex(fileInIndexPath));
	}

	@Test
	public void testMixedReset() throws JGitInternalException
			AmbiguousObjectException
		git.reset().setMode(ResetType.MIXED).setRef(initialCommit.getName())
				.call();
		ObjectId head = db.resolve(Constants.HEAD);
		assertTrue(head.equals(initialCommit));
		assertTrue(untrackedFile.exists());
		assertTrue(indexFile.exists());
		String fileInIndexPath = indexFile.getAbsolutePath();
		assertFalse(inHead(fileInIndexPath));
		assertFalse(inIndex(fileInIndexPath));
	}

	private boolean inHead(String path) throws IOException {
		ObjectId headId = db.resolve(Constants.HEAD);
		RevWalk rw = new RevWalk(db);
		TreeWalk tw = null;
		try {
			tw = TreeWalk.forPath(db
			return tw != null;
		} finally {
			rw.release();
			rw.dispose();
			if (tw != null)
				tw.release();
		}
	}

	private boolean inIndex(String path) throws IOException {
		String repoPath = getRepoRelativePath(path);
		DirCache dc = DirCache.read(db.getIndexFile()
		return dc.getEntry(repoPath) != null;
	}

	private String getRepoRelativePath(String path) throws NoWorkTreeException
			IOException {
		final int pfxLen = db.getWorkTree().getCanonicalPath().length();
		final int pLen = path.length();
		if (pLen > pfxLen)
			return path.substring(pfxLen);
		else if (path.length() == pfxLen - 1)
		return null;
	}

}
