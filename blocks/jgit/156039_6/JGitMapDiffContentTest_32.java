package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitMapDiffContentTest extends AbstractTestInfra {

	private Git git;

	private static final String MASTER_BRANCH = "master";

	private static final List<String> TXT_FILES = Stream.of("file0"
			.collect(Collectors.toList());

	private static final String[] COMMON_TXT_LINES = { "Line1"

	@Before
	public void setup() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder

		git = new CreateRepository(gitSource).execute().get();

		commit(git
				content(TXT_FILES.get(0)
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)
	}

	@Test
	public void testNoDiffOnlyOneCommit() throws IOException {
		Map<String
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName()
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(content).isEmpty();
	}

	@Test
	public void testHasContent() throws IOException {
		commit(git
				content(TXT_FILES.get(4)

		Map<String
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName()
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(1);
	}

	@Test
	public void testHasContents() throws IOException {
		commit(git
				content(TXT_FILES.get(3)
				content(TXT_FILES.get(4)

		Map<String
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName()
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(2);
	}

	@Test
	public void testHasDeleteContents() throws IOException {
		new Commit(git
				new HashMap<String
					{
						put(TXT_FILES.get(0)
					}
				}).execute();

		new Commit(git
				new HashMap<String
					{
						put(TXT_FILES.get(1)
					}
				}).execute();

		Map<String
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName()
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(2);
		contents.values().forEach(v -> assertThat(v).isNull());
	}

	@Test
	public void testWithManyCommitsOneFile() throws IOException {
		commit(git

		commit(git

		commit(git

		Map<String
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName()
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(1);
	}

	@Test
	public void testWithMiddleCommits() throws IOException {
		commit(git

		RevCommit startCommit = git.getLastCommit(MASTER_BRANCH);

		commit(git
				content(TXT_FILES.get(3)
				content(TXT_FILES.get(4)

		new Commit(git
				new HashMap<String
					{
						put(TXT_FILES.get(1)
					}
				}).execute();

		RevCommit endCommit = git.getLastCommit(MASTER_BRANCH);

		commit(git

		Map<String

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(3);
	}

	@Test(expected = GitException.class)
	public void testWithWrongBranchName() throws IOException {
		git.mapDiffContent("wrong-branch-name"
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());
	}

	@Test(expected = GitException.class)
	public void testWithInvalidCommit() throws IOException {
		git.mapDiffContent(MASTER_BRANCH
	}
}
