package org.eclipse.jgit.niofs.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.niofs.fs.attribute.FileDiff;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.junit.Before;
import org.junit.Test;

public class JGitDiffBranchesTest extends BaseTest {

	private Git git;

	private static final String MASTER_BRANCH = "master";
	private static final String DEVELOP_BRANCH = "develop";
	private static final String NON_EXISTENT_FILE = "/dev/null";

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
	public void testDiffWithAddedFiles() throws IOException {
		commit(git
				content(TXT_FILES.get(3)
				content(TXT_FILES.get(4)

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(2);

		fileDiffs.forEach(diff -> assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.ADD.toString()));

		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(NON_EXISTENT_FILE);
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(TXT_FILES.get(3));
		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(4);
	}

	@Test
	public void testDiffWithRemovedFile() {
		new Commit(git
				new HashMap<String
					{
						put(TXT_FILES.get(0)
					}
				}).execute();

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);

		assertThat(fileDiffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.DELETE.toString());
		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(0));
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(NON_EXISTENT_FILE);
		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(4);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(0);
	}

	@Test
	public void testDiffWithUpdatedFiles() throws IOException {
		commit(git
				content(TXT_FILES.get(1)
				content(TXT_FILES.get(2)

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(2);

		fileDiffs.forEach(diff -> assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString()));

		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(1).getNameB()).isEqualTo(TXT_FILES.get(2));
	}

	@Test
	public void testDiffWithUpdateFirstLine() throws IOException {
		commit(git
				content(TXT_FILES.get(1)

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);
		assertThat(fileDiffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(1);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(1);
	}

	@Test
	public void testDiffWithUpdateLastLine() throws IOException {
		commit(git
				content(TXT_FILES.get(1)

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);
		assertThat(fileDiffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(3);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(4);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(3);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(4);
	}

	@Test
	public void testDiffWithUpdateTwoConsecutiveLines() throws IOException {
		commit(git
				multiline(TXT_FILES.get(1)

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);
		assertThat(fileDiffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(1);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(3);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(1);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(3);
	}

	@Test
	public void testDiffWithUpdateFirstAndLastLines() throws IOException {
		commit(git
				multiline(TXT_FILES.get(1)

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(2);

		fileDiffs.forEach(diff -> {
			assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
			assertThat(diff.getNameA()).isEqualTo(TXT_FILES.get(1));
			assertThat(diff.getNameB()).isEqualTo(TXT_FILES.get(1));
		});

		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(1);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(1);

		assertThat(fileDiffs.get(1).getStartA()).isEqualTo(3);
		assertThat(fileDiffs.get(1).getEndA()).isEqualTo(4);
		assertThat(fileDiffs.get(1).getStartB()).isEqualTo(3);
		assertThat(fileDiffs.get(1).getEndB()).isEqualTo(4);
	}

	@Test
	public void testDiffWithAddedRemovedUpdatedFiles() throws IOException {
		new Commit(git
				new HashMap<String
					{
						put(TXT_FILES.get(0)
					}
				}).execute();

		commit(git
				content(TXT_FILES.get(1)

		commit(git
				content(TXT_FILES.get(3)

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(3);

		assertThat(fileDiffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.DELETE.toString());
		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(0));
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(NON_EXISTENT_FILE);

		assertThat(fileDiffs.get(1).getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
		assertThat(fileDiffs.get(1).getNameA()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(1).getNameB()).isEqualTo(TXT_FILES.get(1));

		assertThat(fileDiffs.get(2).getChangeType()).isEqualTo(DiffEntry.ChangeType.ADD.toString());
		assertThat(fileDiffs.get(2).getNameA()).isEqualTo(NON_EXISTENT_FILE);
		assertThat(fileDiffs.get(2).getNameB()).isEqualTo(TXT_FILES.get(3));
	}

	@Test
	public void testDiffWithNonExistentBranch() {
		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH

		assertThat(fileDiffs).isEmpty();
	}
}
