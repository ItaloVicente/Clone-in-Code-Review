package org.eclipse.jgit.niofs.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.DIRECTORY;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.FILE;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.niofs.fs.attribute.VersionAttributes;
import org.eclipse.jgit.niofs.fs.attribute.VersionRecord;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.Clone;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.GetTreeFromRef;
import org.eclipse.jgit.niofs.internal.op.commands.ListDiffs;
import org.eclipse.jgit.niofs.internal.op.commands.ListRefs;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Test;

public class JGitUtilTest extends BaseTest {

	@Test
	public void testNewRepo() throws IOException {

		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		assertThat(git).isNotNull();

		assertThat(new ListRefs(git.getRepository()).execute().size()).isEqualTo(0);

		new Commit(git
			{
				put("file.txt"
			}
		}).execute();

		assertThat(new ListRefs(git.getRepository()).execute().size()).isEqualTo(1);
	}

	@Test
	public void testClone() throws IOException
		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file2.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("file.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("file3.txt"
					}
				}).execute();

		final File gitClonedFolder = new File(parentFolder

		final Git git = new Clone(gitClonedFolder
				CredentialsProvider.getDefault()

		assertThat(git).isNotNull();

		assertThat(new ListRefs(git.getRepository()).execute()).hasSize(2);

		assertThat(new ListRefs(git.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
		assertThat(new ListRefs(git.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");
	}

	@Test
	public void testPathResolve() throws IOException
		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/to/file3.txt"
					}
				}).execute();

		final File gitClonedFolder = new File(parentFolder

		final Git git = new Clone(gitClonedFolder
				CredentialsProvider.getDefault()

		assertThat(git.getPathInfo("user_branch"
		assertThat(git.getPathInfo("user_branch"
		assertThat(git.getPathInfo("user_branch"
	}

	@Test
	public void testAmend() throws IOException
		final File parentFolder = util.createTempDirectory();
		System.out.println("COOL!:" + parentFolder.toString());
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/to/file3.txt"
					}
				}).execute();

		final File gitClonedFolder = new File(parentFolder

		final Git git = new Clone(gitClonedFolder
				CredentialsProvider.getDefault()

		assertThat(git.getPathInfo("master"
		assertThat(git.getPathInfo("master"
		assertThat(git.getPathInfo("master"
	}

	@Test
	public void testBuildVersionAttributes() throws Exception {

		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();

		JGitFileSystem jGitFileSystem = mock(JGitFileSystem.class);
		when(jGitFileSystem.getGit()).thenReturn(git);

		final JGitPathImpl path = mock(JGitPathImpl.class);
		when(path.getFileSystem()).thenReturn(jGitFileSystem);
		when(path.getRefTree()).thenReturn("master");
		when(path.getPath()).thenReturn("path/to/file2.txt");

		final VersionAttributes versionAttributes = new JGitVersionAttributeViewImpl(path).readAttributes();

		List<VersionRecord> records = versionAttributes.history().records();
		assertEquals("commit 1"
		assertEquals("commit 2"
		assertEquals("commit 3"
		assertEquals("commit 4"
	}

	@Test
	public void testDiffForFileCreatedInEmptyRepositoryOrBranch() throws Exception {

		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		final ObjectId oldHead = new GetTreeFromRef(git

		new Commit(git
				new HashMap<String
					{
						put("path/to/file.txt"
					}
				}).execute();

		final ObjectId newHead = new GetTreeFromRef(git

		List<DiffEntry> diff = new ListDiffs(git
		assertNotNull(diff);
		assertFalse(diff.isEmpty());
		assertEquals(ChangeType.ADD
		assertEquals("path/to/file.txt"
	}
}
