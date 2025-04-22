package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitGetCommitTest extends AbstractTestInfra {

	private Git git;

	private static final String MASTER_BRANCH = "master";

	@Before
	public void setup() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder

		git = new CreateRepository(gitSource).execute().get();
	}

	@Test
	public void successTest() throws IOException {
		commit(git

		RevCommit lastCommit = git.getLastCommit(MASTER_BRANCH);

		RevCommit commit = git.getCommit(lastCommit.getName());

		assertThat(commit.getName()).isEqualTo(lastCommit.getName());
	}

	@Test
	public void notFoundTest() {
		RevCommit commit = git.getCommit("non-existent-commit-id");

		assertThat(commit).isNull();
	}
}
