package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.ListCommits;
import org.eclipse.jgit.niofs.internal.op.model.CommitHistory;
import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
import org.eclipse.jgit.niofs.internal.op.model.MoveCommitContent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JGitHistoryTest extends AbstractTestInfra {

	private Git git;

	@Before
	public void setup() throws IOException {
		final File tmpDir = createTempDirectory();
		final File repoDir = new File(tmpDir
		git = new CreateRepository(repoDir).execute()
				.orElseThrow(() -> new IllegalStateException("Unable to create git repo for tests."));

		commit(git
				content("moving.txt"
		moveCommit(singleMove("moving.txt"
		commit(git
				content("moving1.txt"
		moveCommit(singleMove("moving1.txt"
		commit(git
	}

	private Map<String
		Map<String
		moves.put(from
		return moves;
	}

	private void moveCommit(Map<String
		git.commit("master"
				new MoveCommitContent(moves));
	}

	@Test
	public void listCommitsForUnmovedFile() throws Exception {
		final CommitHistory history = new ListCommits(git
		assertEquals("non-moving.txt"
		assertEquals(2
		assertEquals("/non-moving.txt"
		assertEquals("/non-moving.txt"
	}

	@Test
	public void listCommitsForMovedFile() throws Exception {
		final CommitHistory history = new ListCommits(git
		assertEquals("dir/moving2.txt"
		assertEquals(4

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("move moving file to new dir"
		assertEquals("/dir/moving2.txt"

		final RevCommit commit1 = history.getCommits().get(1);
		String oPath1 = history.trackedFileNameChangeFor(commit1.getId());
		assertEquals("change content
		assertEquals("/moving1.txt"

		final RevCommit commit2 = history.getCommits().get(2);
		String oPath2 = history.trackedFileNameChangeFor(commit2.getId());
		assertEquals("rename moving file"
		assertEquals("/moving1.txt"

		final RevCommit commit3 = history.getCommits().get(3);
		String oPath3 = history.trackedFileNameChangeFor(commit3.getId());
		assertEquals("create files"
		assertEquals("/moving.txt"
	}

	@Test
	public void listCommitsForRestoredFile() throws Exception {
		final CommitHistory history = new ListCommits(git
		assertEquals("moving1.txt"
		assertEquals(4

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("simulate checkout old version"
		assertEquals("/moving1.txt"

		final RevCommit commit1 = history.getCommits().get(1);
		String oPath1 = history.trackedFileNameChangeFor(commit1.getId());
		assertEquals("change content
		assertEquals("/moving1.txt"

		final RevCommit commit2 = history.getCommits().get(2);
		String oPath2 = history.trackedFileNameChangeFor(commit2.getId());
		assertEquals("rename moving file"
		assertEquals("/moving1.txt"

		final RevCommit commit3 = history.getCommits().get(3);
		String oPath3 = history.trackedFileNameChangeFor(commit3.getId());
		assertEquals("create files"
		assertEquals("/moving.txt"
	}

	@Test
	public void listCommitsOnDirectory() throws Exception {
		final CommitHistory history = new ListCommits(git
		assertEquals("dir"
		assertEquals(1

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("move moving file to new dir"
		assertEquals("/dir"
	}

	@Test
	public void listCommitsOnRootDirectoryViaAbsolute() throws Exception {
		final CommitHistory history = new ListCommits(git
		assertEquals("/"
		assertEquals(5

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("simulate checkout old version"
		assertEquals("/"
	}

	@Test
	public void listCommitsOnRootDirectoryViaNull() throws Exception {
		final CommitHistory history = new ListCommits(git
		assertEquals("/"
		assertEquals(5

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("simulate checkout old version"
		assertEquals("/"
	}

	@Test
	public void listCommitsOnRootDirectoryViaEmpty() throws Exception {
		final CommitHistory history = new ListCommits(git
		assertEquals("/"
		assertEquals(5

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("simulate checkout old version"
		assertEquals("/"
	}
}
