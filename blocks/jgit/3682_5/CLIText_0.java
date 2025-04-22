package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class StatusTest extends CLIRepositoryTestCase {
	@Test
	public void testStatus() throws Exception {
		Git git = new Git(db);
		writeTrashFile("tracked"
		writeTrashFile("stagedNew"
		writeTrashFile("stagedModified"
		writeTrashFile("stagedDeleted"
		writeTrashFile("trackedModified"
		writeTrashFile("trackedDeleted"
		writeTrashFile("untracked"
						"# On branch master"
						"# "
						"# Untracked files:"
						"# \tstagedDeleted"
						"# \tstagedModified"
						"# \tstagedNew"
						"# \ttracked"
						"# \ttrackedDeleted"
						"# \ttrackedModified"
						"# \tuntracked"
						"# "
						"# Summary: 0 to commit
				}
		git.add().addFilepattern("tracked").call();
		git.add().addFilepattern("stagedModified").call();
		git.add().addFilepattern("stagedDeleted").call();
		git.add().addFilepattern("trackedModified").call();
		git.add().addFilepattern("trackedDeleted").call();
						"# On branch master"
						"# "
						"# Changes to be committed:"
						"# \tnew file:   stagedDeleted"
						"# \tnew file:   stagedModified"
						"# \tnew file:   tracked"
						"# \tnew file:   trackedDeleted"
						"# \tnew file:   trackedModified"
						"# "
						"# Untracked files:"
						"# \tstagedNew"
						"# \tuntracked"
						"# "
						"# Summary: 5 to commit
				}
		git.commit().setMessage("initial commit")
				.call();
						"# On branch master"
						"# "
						"# Untracked files:"
						"# \tstagedNew"
						"# \tuntracked"
						"# "
						"# Summary: 0 to commit
				}
		writeTrashFile("stagedModified"
		deleteTrashFile("stagedDeleted");
		writeTrashFile("trackedModified"
		deleteTrashFile("trackedDeleted");
		git.add().addFilepattern("stagedModified").call();
		git.rm().addFilepattern("stagedDeleted").call();
		git.add().addFilepattern("stagedNew").call();
						"# On branch master"
						"# "
						"# Changes to be committed:"
						"# \tnew file:   stagedNew"
						"# \tmodified:   stagedModified"
						"# \tdeleted:    stagedDeleted"
						"# "
						"# Changes not staged for commit:"
						"# \tmodified:   trackedModified"
						"# \tdeleted:    trackedDeleted"
						"# "
						"# Untracked files:"
						"# \tuntracked"
						"# "
						"# Summary: 3 to commit
				}
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		git.add().addFilepattern("trackedModified").call();
		git.rm().addFilepattern("trackedDeleted").call();
		git.commit().setMessage("commit before branching").call();
						"# On branch master"
						"# "
						"# Untracked files:"
						"# \tuntracked"
						"# "
						"# Summary: 0 to commit
				}
		git.checkout().setCreateBranch(true).setName("test").call();
						"# On branch test"
						"# "
						"# Untracked files:"
						"# \tuntracked"
						"# "
						"# Summary: 0 to commit
				}
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		RevCommit testBranch = git.commit()
				.setMessage("changed unmerged in test branch").call();
						"# On branch test"
						"# "
						"# Untracked files:"
						"# \tuntracked"
						"# "
						"# Summary: 0 to commit
				}
		git.checkout().setName("master").call();
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		git.commit().setMessage("changed unmerged in master branch").call();
						"# On branch master"
						"# "
						"# Untracked files:"
						"# \tuntracked"
						"# "
						"# Summary: 0 to commit
				}
		git.merge().include(testBranch.getId()).call();
						"# On branch master"
						"# "
						"# Unmerged paths:"
						"# \tunmerged"
						"# "
						"# Untracked files:"
						"# \tuntracked"
						"# "
						"# Summary: 0 to commit
				}
		String commitId = db.getRef(Constants.MASTER).getObjectId().name();
		git.checkout().setName(commitId).call();
						"# Not currently on any branch."
						"# "
						"# Unmerged paths:"
						"# \tunmerged"
						"# "
						"# Untracked files:"
						"# \tuntracked"
						"# "
						"# Summary: 0 to commit
				}
	}
}
