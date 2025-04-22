package org.eclipse.jgit.merge;

import static org.junit.Assert.assertFalse;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class ResolveMergerTest extends RepositoryTestCase {

	@Test
	public void failingPathsShouldNotResultInOKReturnValue() throws Exception {
		File folder1 = new File(db.getWorkTree()
		FileUtils.mkdir(folder1);
		File file = new File(folder1
		write(file
		file = new File(folder1
		write(file

		Git git = new Git(db);
		git.add().addFilepattern(folder1.getName()).call();
		RevCommit base = git.commit().setMessage("adding folder").call();

		recursiveDelete(folder1);
		git.rm().addFilepattern("folder1/file1.txt")
				.addFilepattern("folder1/file2.txt").call();
		RevCommit other = git.commit()
				.setMessage("removing folders on 'other'").call();

		git.checkout().setName(base.name()).call();

		file = new File(db.getWorkTree()
		write(file

		git.add().addFilepattern("unrelated").call();
		RevCommit head = git.commit().setMessage("Adding another file").call();

		file = new File(folder1
		write(file

		ResolveMerger merger = new ResolveMerger(db
		merger.setCommitNames(new String[] { "BASE"
		merger.setWorkingTreeIterator(new FileTreeIterator(db));
		boolean ok = merger.merge(head.getId()

		assertFalse(merger.getFailingPaths().isEmpty());
		assertFalse(ok);
	}

}
