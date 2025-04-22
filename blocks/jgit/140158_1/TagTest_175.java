package org.eclipse.jgit.pgm;

import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class StatusTest extends CLIRepositoryTestCase {

	@Test
	public void testPathOptionHelp() throws Exception {
		String[] result = execute("git status -h");
		assertTrue("Unexpected argument: " + result[1]
				result[1].endsWith("[-- path ...]"));
	}

	@Test
	public void testStatusDefault() throws Exception {
		executeTest("git status"
	}

	@Test
	public void testStatusU() throws Exception {
		executeTest("git status -u"
	}

	@Test
	public void testStatusUno() throws Exception {
		executeTest("git status -uno"
	}

	@Test
	public void testStatusUall() throws Exception {
		executeTest("git status -uall"
	}

	@Test
	public void testStatusUntrackedFiles() throws Exception {
		executeTest("git status --untracked-files"
	}

	@Test
	public void testStatusUntrackedFilesNo() throws Exception {
		executeTest("git status --untracked-files=no"
	}

	@Test
	public void testStatusUntrackedFilesAll() throws Exception {
		executeTest("git status --untracked-files=all"
	}

	@Test
	public void testStatusPorcelain() throws Exception {
		executeTest("git status --porcelain"
	}

	@Test
	public void testStatusPorcelainU() throws Exception {
		executeTest("git status --porcelain -u"
	}

	@Test
	public void testStatusPorcelainUno() throws Exception {
		executeTest("git status --porcelain -uno"
	}

	@Test
	public void testStatusPorcelainUall() throws Exception {
		executeTest("git status --porcelain -uall"
	}

	@Test
	public void testStatusPorcelainUntrackedFiles() throws Exception {
		executeTest("git status --porcelain --untracked-files"
	}

	@Test
	public void testStatusPorcelainUntrackedFilesNo() throws Exception {
		executeTest("git status --porcelain --untracked-files=no"
	}

	@Test
	public void testStatusPorcelainUntrackedFilesAll() throws Exception {
		executeTest("git status --porcelain --untracked-files=all"
	}

	private void executeTest(String command
			boolean untrackedFiles) throws Exception {
		Git git = new Git(db);
		writeAllFiles();
		assertUntrackedFiles(command
		addFilesToIndex(git);
		assertStagedFiles(command
		makeInitialCommit(git);
		assertAfterInitialCommit(command
		makeSomeChangesAndStageThem(git);
		assertStagedStatus(command
		createUnmergedFile(git);
		commitPendingChanges(git);
		assertUntracked(command
		checkoutTestBranch(git);
		assertUntracked(command
		RevCommit testBranch = commitChangesInTestBranch(git);
		assertUntracked(command
		checkoutMasterBranch(git);
		changeUnmergedFileAndCommit(git);
		assertUntracked(command
		mergeTestBranchInMaster(git
		assertUntrackedAndUnmerged(command
		detachHead(git);
		assertUntrackedAndUnmerged(command
	}

	private void writeAllFiles() throws IOException {
		writeTrashFile("tracked"
		writeTrashFile("stagedNew"
		writeTrashFile("stagedModified"
		writeTrashFile("stagedDeleted"
		writeTrashFile("trackedModified"
		writeTrashFile("trackedDeleted"
		writeTrashFile("untracked"
	}

	private void addFilesToIndex(Git git) throws GitAPIException {
		git.add().addFilepattern("tracked").call();
		git.add().addFilepattern("stagedModified").call();
		git.add().addFilepattern("stagedDeleted").call();
		git.add().addFilepattern("trackedModified").call();
		git.add().addFilepattern("trackedDeleted").call();
	}

	private void makeInitialCommit(Git git) throws GitAPIException {
		git.commit().setMessage("initial commit").call();
	}

	private void makeSomeChangesAndStageThem(Git git) throws IOException
			GitAPIException {
		writeTrashFile("stagedModified"
		deleteTrashFile("stagedDeleted");
		writeTrashFile("trackedModified"
		deleteTrashFile("trackedDeleted");
		git.add().addFilepattern("stagedModified").call();
		git.rm().addFilepattern("stagedDeleted").call();
		git.add().addFilepattern("stagedNew").call();
	}

	private void createUnmergedFile(Git git) throws IOException
			GitAPIException {
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
	}

	private void commitPendingChanges(Git git) throws GitAPIException {
		git.add().addFilepattern("trackedModified").call();
		git.rm().addFilepattern("trackedDeleted").call();
		git.commit().setMessage("commit before branching").call();
	}

	private void checkoutTestBranch(Git git) throws GitAPIException {
		git.checkout().setCreateBranch(true).setName("test").call();
	}

	private RevCommit commitChangesInTestBranch(Git git) throws IOException
			GitAPIException {
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		return git.commit()
				.setMessage("changed unmerged in test branch").call();
	}

	private void checkoutMasterBranch(Git git) throws GitAPIException {
		git.checkout().setName("master").call();
	}

	private void changeUnmergedFileAndCommit(Git git) throws IOException
			GitAPIException {
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		git.commit().setMessage("changed unmerged in master branch").call();
	}

	private void mergeTestBranchInMaster(Git git
			throws GitAPIException {
		git.merge().include(aCommit.getId()).call();
	}

	private void detachHead(Git git) throws IOException
		String commitId = db.exactRef(R_HEADS + MASTER).getObjectId().name();
		git.checkout().setName(commitId).call();
	}

	private void assertUntrackedFiles(String command
			boolean untrackedFiles) throws Exception {
		String[] output = new String[0];

		if (porcelain) {
			if (untrackedFiles) {
						"?? stagedDeleted"
						"?? stagedModified"
						"?? stagedNew"
						"?? tracked"
						"?? trackedDeleted"
						"?? trackedModified"
						"?? untracked"
				};
			} else {
				};
			}
		} else {
			if (untrackedFiles) {
						"On branch master"
						"Untracked files:"
						""
						"\tstagedDeleted"
						"\tstagedModified"
						"\tstagedNew"
						"\ttracked"
						"\ttrackedDeleted"
						"\ttrackedModified"
						"\tuntracked"
				};
			} else {
						"On branch master"
				};
			}
		}

		assertArrayOfLinesEquals(output
	}

	private void assertStagedFiles(String command
			boolean untrackedFiles) throws Exception {
		String[] output = new String[0];

		if (porcelain) {
			if (untrackedFiles) {
						"A  stagedDeleted"
						"A  stagedModified"
						"A  tracked"
						"A  trackedDeleted"
						"A  trackedModified"
						"?? stagedNew"
						"?? untracked"
				};
			} else {
						"A  stagedDeleted"
						"A  stagedModified"
						"A  tracked"
						"A  trackedDeleted"
						"A  trackedModified"
				};
			}
		} else {
			if (untrackedFiles) {
						"On branch master"
						"Changes to be committed:"
						""
						"\tnew file:   stagedDeleted"
						"\tnew file:   stagedModified"
						"\tnew file:   tracked"
						"\tnew file:   trackedDeleted"
						"\tnew file:   trackedModified"
						""
						"Untracked files:"
						""
						"\tstagedNew"
						"\tuntracked"
				};
			} else {
						"On branch master"
						"Changes to be committed:"
						""
						"\tnew file:   stagedDeleted"
						"\tnew file:   stagedModified"
						"\tnew file:   tracked"
						"\tnew file:   trackedDeleted"
						"\tnew file:   trackedModified"
				};
			}
		}

		assertArrayOfLinesEquals(output
	}

	private void assertAfterInitialCommit(String command
			boolean untrackedFiles) throws Exception {
		String[] output = new String[0];

		if (porcelain) {
			if (untrackedFiles) {
						"?? stagedNew"
						"?? untracked"
				};
			} else {
				};
			}
		} else {
			if (untrackedFiles) {
						"On branch master"
						"Untracked files:"
						""
						"\tstagedNew"
						"\tuntracked"
				};
			} else {
						"On branch master"
				};
			}
		}

		assertArrayOfLinesEquals(output
	}

	private void assertStagedStatus(String command
			boolean untrackedFiles) throws Exception {
		String[] output = new String[0];

		if (porcelain) {
			if (untrackedFiles) {
						"D  stagedDeleted"
						"M  stagedModified"
						"A  stagedNew"
						" D trackedDeleted"
						" M trackedModified"
						"?? untracked"
				};
			} else {
						"D  stagedDeleted"
						"M  stagedModified"
						"A  stagedNew"
						" D trackedDeleted"
						" M trackedModified"
				};
			}
		} else {
			if (untrackedFiles) {
						"On branch master"
						"Changes to be committed:"
						""
						"\tdeleted:    stagedDeleted"
						"\tmodified:   stagedModified"
						"\tnew file:   stagedNew"
						""
						"Changes not staged for commit:"
						""
						"\tdeleted:    trackedDeleted"
						"\tmodified:   trackedModified"
						""
						"Untracked files:"
						""
						"\tuntracked"
				};
			} else {
						"On branch master"
						"Changes to be committed:"
						""
						"\tdeleted:    stagedDeleted"
						"\tmodified:   stagedModified"
						"\tnew file:   stagedNew"
						""
						"Changes not staged for commit:"
						""
						"\tdeleted:    trackedDeleted"
						"\tmodified:   trackedModified"
						""
				};
			}
		}

		assertArrayOfLinesEquals(output
	}

	private void assertUntracked(String command
			boolean porcelain
			boolean untrackedFiles
		String[] output = new String[0];
		String branchHeader = "On branch " + branch;

		if (porcelain) {
			if (untrackedFiles) {
						"?? untracked"
				};
			} else {
				};
			}
		} else {
			if (untrackedFiles) {
						branchHeader
						"Untracked files:"
						""
						"\tuntracked"
				};
			} else {
						branchHeader
				};
			}
		}

		assertArrayOfLinesEquals(output
	}

	private void assertUntrackedAndUnmerged(String command
			boolean untrackedFiles
		String[] output = new String[0];
				: "On branch " + branch;

		if (porcelain) {
			if (untrackedFiles) {
						"UU unmerged"
						"?? untracked"
				};
			} else {
						"UU unmerged"
				};
			}
		} else {
			if (untrackedFiles) {
						branchHeader
						"Unmerged paths:"
						""
						"\tboth modified:      unmerged"
						""
						"Untracked files:"
						""
						"\tuntracked"
				};
			} else {
						branchHeader
						"Unmerged paths:"
						""
						"\tboth modified:      unmerged"
				};
			}
		}

		assertArrayOfLinesEquals(output
	}
}
