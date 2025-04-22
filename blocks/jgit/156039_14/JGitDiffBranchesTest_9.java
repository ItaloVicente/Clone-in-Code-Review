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

public class JGitConflictBranchesCheckerTest extends BaseTest {

	private Git git;

	private static final String MASTER_BRANCH = "master";
	private static final String DEVELOP_BRANCH = "develop";

	private static final List<String> TXT_FILES = Stream.of("file0"
			.collect(Collectors.toList());

	private static final String[] COMMON_TXT_LINES = { "Line1"

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
	}

	@Test
	public void testReportConflictsAllFiles() throws IOException {
		commit(git
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)

		commit(git
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)
						multiline(TXT_FILES.get(2)

		List<String> conflicts = git.conflictBranchesChecker(MASTER_BRANCH

		assertThat(conflicts).isNotEmpty();
		assertThat(conflicts).hasSize(2);
		assertThat(conflicts.get(0)).isEqualTo(TXT_FILES.get(1));
		assertThat(conflicts.get(1)).isEqualTo(TXT_FILES.get(2));
	}

	@Test
	public void testReportConflictsSomeFiles() throws IOException {
		commit(git
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)

		commit(git
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)
						multiline(TXT_FILES.get(2)

		List<String> conflicts = git.conflictBranchesChecker(MASTER_BRANCH

		assertThat(conflicts).isNotEmpty();
		assertThat(conflicts).hasSize(1);
		assertThat(conflicts.get(0)).isEqualTo(TXT_FILES.get(1));
	}

	@Test
	public void testReportConflictsNoFiles() throws IOException {
		commit(git
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)

		commit(git
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)
						multiline(TXT_FILES.get(2)

		List<String> conflicts = git.conflictBranchesChecker(MASTER_BRANCH

		assertThat(conflicts).isEmpty();
	}

	@Test(expected = GitException.class)
	public void testInvalidBranch() {
		git.conflictBranchesChecker(MASTER_BRANCH
	}
}
