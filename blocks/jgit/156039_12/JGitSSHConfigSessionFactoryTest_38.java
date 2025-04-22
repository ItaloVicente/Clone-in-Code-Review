package org.eclipse.jgit.niofs.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.junit.Before;
import org.junit.Test;

public class JGitRevertMergeTest extends BaseTest {

	private Git git;

	private static final String MASTER_BRANCH = "master";
	private static final String DEVELOP_BRANCH = "develop";

	private static final List<String> TXT_FILES = Stream.of("file0"
			.collect(Collectors.toList());

	private static final String[] COMMON_TXT_LINES = { "Line1"

	private String commonAncestorCommitId;

	@Before
	public void setup() throws IOException {
		final File parentFolder = util.createTempDirectory();

		final File gitSource = new File(parentFolder

		git = new CreateRepository(gitSource).execute().get();

		commit(git
				content(TXT_FILES.get(0)
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)

		new CreateBranch((GitImpl) git

		commit(git
				content(TXT_FILES.get(3)
				content(TXT_FILES.get(4)

		commonAncestorCommitId = git.getCommonAncestorCommit(DEVELOP_BRANCH
	}

	@Test(expected = GitException.class)
	public void testInvalidSourceBranch() throws IOException {
		String mergeCommitId = doMerge();

		git.revertMerge("invalid-branch"
	}

	@Test(expected = GitException.class)
	public void testInvalidTargetBranch() throws IOException {
		String mergeCommitId = doMerge();

		git.revertMerge(DEVELOP_BRANCH
	}

	@Test
	public void testRevertFailedMergeIsNotLastTargetCommit() throws IOException {
		String mergeCommitId = doMerge();

		commit(git

		boolean result = git.revertMerge(DEVELOP_BRANCH

		assertThat(result).isFalse();
	}

	@Test
	public void testRevertFailedMergeParentTargetIsNotCommonAncestor() throws IOException {
		commit(git

		String mergeCommitId = doMerge();

		boolean result = git.revertMerge(DEVELOP_BRANCH

		assertThat(result).isFalse();
	}

	@Test
	public void testRevertFailedMergeSourceParentIsNotLastSourceCommit() throws IOException {
		String mergeCommitId = doMerge();

		commit(git

		boolean result = git.revertMerge(DEVELOP_BRANCH

		assertThat(result).isFalse();
	}

	@Test
	public void testRevertSucceeded() throws IOException {
		String mergeCommitId = doMerge();

		boolean result = git.revertMerge(DEVELOP_BRANCH

		assertThat(result).isTrue();
	}

	private String doMerge() throws IOException {
		git.merge(DEVELOP_BRANCH

		return git.getLastCommit(MASTER_BRANCH).getName();
	}
}
