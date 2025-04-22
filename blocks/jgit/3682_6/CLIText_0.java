package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

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
						"# Untracked files:"
						"# "
						"# \tstagedDeleted"
						"# \tstagedModified"
						"# \tstagedNew"
						"# \ttracked"
						"# \ttrackedDeleted"
						"# \ttrackedModified"
						"# \tuntracked"
				}
		git.add().addFilepattern("tracked").call();
		git.add().addFilepattern("stagedModified").call();
		git.add().addFilepattern("stagedDeleted").call();
		git.add().addFilepattern("trackedModified").call();
		git.add().addFilepattern("trackedDeleted").call();
						"# On branch master"
						"# Changes to be committed:"
						"# "
						"# \tnew file:   stagedDeleted"
						"# \tnew file:   stagedModified"
						"# \tnew file:   tracked"
						"# \tnew file:   trackedDeleted"
						"# \tnew file:   trackedModified"
						"# "
						"# Untracked files:"
						"# "
						"# \tstagedNew"
						"# \tuntracked"
				}
		git.commit().setMessage("initial commit")
				.call();
						"# On branch master"
						"# Untracked files:"
						"# "
						"# \tstagedNew"
						"# \tuntracked"
				}
		writeTrashFile("stagedModified"
		deleteTrashFile("stagedDeleted");
		writeTrashFile("trackedModified"
		deleteTrashFile("trackedDeleted");
		git.add().addFilepattern("stagedModified").call();
		git.rm().addFilepattern("stagedDeleted").call();
		git.add().addFilepattern("stagedNew").call();
						"# On branch master"
						"# Changes to be committed:"
						"# "
						"# \tdeleted:    stagedDeleted"
						"# \tmodified:   stagedModified"
						"# \tnew file:   stagedNew"
						"# "
						"# Changes not staged for commit:"
						"# "
						"# \tdeleted:    trackedDeleted"
						"# \tmodified:   trackedModified"
						"# "
						"# Untracked files:"
						"# "
						"# \tuntracked"
				}
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		git.add().addFilepattern("trackedModified").call();
		git.rm().addFilepattern("trackedDeleted").call();
		git.commit().setMessage("commit before branching").call();
						"# On branch master"
						"# Untracked files:"
						"# "
						"# \tuntracked"
				}
		git.checkout().setCreateBranch(true).setName("test").call();
						"# On branch test"
						"# Untracked files:"
						"# "
						"# \tuntracked"
				}
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		RevCommit testBranch = git.commit()
				.setMessage("changed unmerged in test branch").call();
						"# On branch test"
						"# Untracked files:"
						"# "
						"# \tuntracked"
				}
		git.checkout().setName("master").call();
		writeTrashFile("unmerged"
		git.add().addFilepattern("unmerged").call();
		git.commit().setMessage("changed unmerged in master branch").call();
						"# On branch master"
						"# Untracked files:"
						"# "
						"# \tuntracked"
				}
		git.merge().include(testBranch.getId()).call();
						"# On branch master"
						"# Unmerged paths:"
						"# "
						"# \tunmerged"
						"# "
						"# Untracked files:"
						"# "
						"# \tuntracked"
				}
		String commitId = db.getRef(Constants.MASTER).getObjectId().name();
		git.checkout().setName(commitId).call();
						"# Not currently on any branch."
						"# Unmerged paths:"
						"# "
						"# \tunmerged"
						"# "
						"# Untracked files:"
						"# "
						"# \tuntracked"
				}
	}

	private void assertArrayOfLinesEquals(String[] expected
		assertEquals(toText(expected)
	}

	private String toText(String[] lines) {
		StringBuilder b = new StringBuilder();
		for (String s : lines) {
			b.append(s);
			b.append('\n');
		}
		return b.toString();
	}
}
