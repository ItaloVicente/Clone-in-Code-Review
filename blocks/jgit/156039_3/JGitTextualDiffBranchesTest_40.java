package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.niofs.internal.op.model.TextualDiff;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitTextualDiffBranchesTest extends AbstractTestInfra {

	private Git git;

	private static final String MASTER_BRANCH = "master";
	private static final String DEVELOP_BRANCH = "develop";

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

		new CreateBranch((GitImpl) git
	}

	@Test
	public void testDiffWithAddedFiles() throws IOException {
		commit(git
				content(TXT_FILES.get(3)
				content(TXT_FILES.get(4)

		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(2);

		diffs.forEach(diff -> {
			assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.ADD.toString());
			assertThat(diff.getDiffText()).isNotEmpty();
		});

		assertThat(diffs.get(0).getOldFilePath()).isEqualTo(DiffEntry.DEV_NULL);
		assertThat(diffs.get(0).getNewFilePath()).isEqualTo(TXT_FILES.get(3));
		assertThat(diffs.get(0).getLinesAdded()).isEqualTo(4);
		assertThat(diffs.get(0).getLinesDeleted()).isEqualTo(0);
	}

	@Test
	public void testDiffWithAddedFilesSameBranch() throws IOException {
		RevCommit startCommit = git.getLastCommit(MASTER_BRANCH);

		commit(git
				content(TXT_FILES.get(3)
				content(TXT_FILES.get(4)

		RevCommit endCommit = git.getLastCommit(MASTER_BRANCH);

		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH
				endCommit.getName());

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(2);
	}

	@Test
	public void testDiffWithAddedFileAndTargetUpdatedLater() throws IOException {
		commit(git
				content(TXT_FILES.get(3)

		commit(git
				content(TXT_FILES.get(4)

		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(1);

		assertThat(diffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.ADD.toString());
		assertThat(diffs.get(0).getDiffText()).isNotEmpty();

		assertThat(diffs.get(0).getOldFilePath()).isEqualTo(DiffEntry.DEV_NULL);
		assertThat(diffs.get(0).getNewFilePath()).isEqualTo(TXT_FILES.get(3));
		assertThat(diffs.get(0).getLinesAdded()).isEqualTo(4);
		assertThat(diffs.get(0).getLinesDeleted()).isEqualTo(0);
	}

	@Test
	public void testDiffWithRemovedFile() {
		new Commit(git
				new HashMap<String
					{
						put(TXT_FILES.get(0)
					}
				}).execute();

		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(1);

		assertThat(diffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.DELETE.toString());
		assertThat(diffs.get(0).getOldFilePath()).isEqualTo(TXT_FILES.get(0));
		assertThat(diffs.get(0).getNewFilePath()).isEqualTo(DiffEntry.DEV_NULL);
		assertThat(diffs.get(0).getLinesAdded()).isEqualTo(0);
		assertThat(diffs.get(0).getLinesDeleted()).isEqualTo(4);
		assertThat(diffs.get(0).getDiffText()).isNotEmpty();
	}

	@Test
	public void testDiffWithUpdatedFiles() throws IOException {
		commit(git
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)

		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(2);

		diffs.forEach(diff -> {
			assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
			assertThat(diff.getOldFilePath()).isEqualTo(diff.getNewFilePath());
			assertThat(diff.getLinesAdded()).isEqualTo(1);
			assertThat(diff.getLinesDeleted()).isEqualTo(1);
			assertThat(diff.getDiffText()).isNotEmpty();
		});

		assertThat(diffs.get(0).getOldFilePath()).isEqualTo(TXT_FILES.get(1));
		assertThat(diffs.get(1).getOldFilePath()).isEqualTo(TXT_FILES.get(2));
	}

	@Test
	public void testDiffWithUpdateFirstAndLastLines() throws IOException {
		commit(git
				multiline(TXT_FILES.get(1)

		List<TextualDiff> fileDiffs = git.textualDiffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);
	}

	@Test
	public void testDiffWithEvenBranches() {
		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH

		assertThat(diffs).isEmpty();
	}

	@Test(expected = GitException.class)
	public void testDiffWithNonExistentBranch() {
		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH

		assertThat(diffs).isEmpty();
	}
}
