package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class MergerRenameCrissCrossTest extends RepositoryTestCase {

	private static final String ORIGINAL_CONTENT = "a\nb\nc\n";

	private static final String SLIGHTLY_MODIFIED_CONTENT1 = "z\na\nb\nc";

	String SLIGHTLY_MODIFIED_CONTENT2 = "a\nb\nc\nd";

	String ORIGINAL_FILENAME = "file1";

	String RENAME_FILENAME1 = "file2";

	String RENAME_FILENAME2 = "file3";

	private static final ThreeWayMergeStrategy STRATEGY = MergeStrategy.RECURSIVE;

	@Before
	public void enableRename() throws IOException
		StoredConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_DIFF_SECTION
				ConfigConstants.CONFIG_KEY_RENAMES
		config.setString(ConfigConstants.CONFIG_MERGE_SECTION
				ConfigConstants.CONFIG_KEY_RENAMES
		config.save();
	}

	public MergeResult setUpRenameRenameConflictInVirtualAncestor(
			String resolveName1
			String resolveContent2) throws Exception {

		Git git = Git.wrap(db);

		writeTrashFile(ORIGINAL_FILENAME
		git.add().addFilepattern(ORIGINAL_FILENAME).call();
		RevCommit commitI = git.commit().setMessage("Initial commit").call();

		writeTrashFile(RENAME_FILENAME1
		git.rm().addFilepattern(ORIGINAL_FILENAME).call();
		git.add().addFilepattern(RENAME_FILENAME1).call();
		RevCommit commitA1 = git.commit().setMessage("Ancestor 1").call();

		writeTrashFile(RENAME_FILENAME1
		git.rm().addFilepattern(ORIGINAL_FILENAME).call();
		git.add().addFilepattern(RENAME_FILENAME1).call();
		git.commit().setMessage("Child 1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitI)
				.setName("branch-to-merge").call();
		git.rm().addFilepattern(ORIGINAL_FILENAME).call();
		writeTrashFile(RENAME_FILENAME2
		git.add().addFilepattern(RENAME_FILENAME2).call();
		RevCommit commitA2 = git.commit().setMessage("Ancestor 2").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitA1)
				.setName("second-branch").call();
		writeTrashFile(RENAME_FILENAME1
		git.add().addFilepattern(RENAME_FILENAME1).call();
		git.commit().setMessage("Child 2 on second-branch").call();
		MergeResult mergeResult = git.merge().include(commitA2)
				.setStrategy(MergeStrategy.RECURSIVE).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()

		git.rm().addFilepattern(ORIGINAL_FILENAME).call();
		git.rm().addFilepattern(RENAME_FILENAME1).call();
		git.rm().addFilepattern(RENAME_FILENAME2).call();
		writeTrashFile(resolveName1
		git.add().addFilepattern(resolveName1).call();
		RevCommit commitC3S = git.commit()
				.setMessage("Child 3 on second bug - resolve merge conflict")
				.call();

		git.checkout().setName("master").call();
		mergeResult = git.merge().include(commitA2)
				.setStrategy(MergeStrategy.RECURSIVE).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()

		git.rm().addFilepattern(ORIGINAL_FILENAME).call();
		git.rm().addFilepattern(RENAME_FILENAME1).call();
		git.rm().addFilepattern(RENAME_FILENAME2).call();
		writeTrashFile(resolveName2
		git.add().addFilepattern(resolveName2).call();
		git.commit().setMessage("Child 4 on master - resolve merge conflict")
				.call();

		return git.merge().setStrategy(STRATEGY).include(commitC3S).call();

	}

	@Theory
	public void checkRenameMergeConflictInVirtualAncestor_resolveToDifferentNamesInChildren_renameNotDetected(
			boolean keepInOurs) throws Exception {
		MergeResult mergeResult = setUpRenameRenameConflictInVirtualAncestor(
				keepInOurs ? RENAME_FILENAME1 : RENAME_FILENAME2
				SLIGHTLY_MODIFIED_CONTENT1
				keepInOurs ? RENAME_FILENAME2 : RENAME_FILENAME1
				SLIGHTLY_MODIFIED_CONTENT2);
		assertFalse(check(ORIGINAL_FILENAME));
		assertEquals(SLIGHTLY_MODIFIED_CONTENT1
				read(keepInOurs ? RENAME_FILENAME1 : RENAME_FILENAME2));
		assertEquals(SLIGHTLY_MODIFIED_CONTENT2
				read(keepInOurs ? RENAME_FILENAME2 : RENAME_FILENAME1));
		assertEquals(mergeResult.getMergeStatus()

	}

	@Theory
	public void checkRenameMergeConflictInVirtualAncestor_resolveToSameName_conflicting()
			throws Exception {
		MergeResult mergeResult = setUpRenameRenameConflictInVirtualAncestor(
				RENAME_FILENAME2
				SLIGHTLY_MODIFIED_CONTENT1);
		assertEquals(mergeResult.getMergeStatus()
		assertFalse(check(ORIGINAL_FILENAME));
		assertFalse(check(RENAME_FILENAME1));
		assertTrue(read(RENAME_FILENAME2).contains(String.format(
				"<<<<<<< HEAD\n%s\n" + "=======\n%s\n"
				SLIGHTLY_MODIFIED_CONTENT1
	}
}
