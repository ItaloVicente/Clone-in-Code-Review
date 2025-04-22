package org.eclipse.jgit.niofs.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.AssertionsForClassTypes;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Test;

public class JGitFileSystemImplTest extends BaseTest {

	static {
		CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("guest"
	}

	@Test
	public void testOnlyLocalRoot() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit(util.createTempDirectory());
		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		assertThat(fileSystem.isReadOnly()).isFalse();
		assertThat(fileSystem.getSeparator()).isEqualTo("/");
		assertThat(fileSystem.getName()).isEqualTo("my-repo");

		assertThat(fileSystem.getRootDirectories()).hasSize(1);
		final Path root = fileSystem.getRootDirectories().iterator().next();
		assertThat(root.toString()).isEqualTo("/");

		assertThat(root.getRoot().toString()).isEqualTo("/");
	}

	@Test
	public void testRemoteRoot() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final File tempDir = util.createTempDirectory();
		final Git git = new GitImpl(
				GitImpl._cloneRepository().setNoCheckout(false).setBare(true).setCloneAllBranches(true)
						.setURI(setupGit(util.createTempDirectory()).getRepository().getDirectory().toString())
						.setDirectory(tempDir).call());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		assertThat(fileSystem.isReadOnly()).isFalse();
		assertThat(fileSystem.getSeparator()).isEqualTo("/");
		assertThat(fileSystem.getName()).isEqualTo("my-repo");

		assertThat(fileSystem.getRootDirectories()).hasSize(1);
		final Path root = fileSystem.getRootDirectories().iterator().next();
		assertThat(root.toString()).isEqualTo("/");

		assertThat(root.getRoot().toString()).isEqualTo("/");
	}

	private JGitFileSystemLock createFSLock(Git git) {
		return new JGitFileSystemLock(git
	}

	@Test
	public void testProvider() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		assertThat(fileSystem.getName()).isEqualTo("my-repo");
		assertThat(fileSystem.isReadOnly()).isFalse();
		assertThat(fileSystem.getSeparator()).isEqualTo("/");

		assertThat(fileSystem.provider()).isEqualTo(fsProvider);
	}

	@Test(expected = IllegalStateException.class)
	public void testClose() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		assertThat(fileSystem.isReadOnly()).isFalse();
		assertThat(fileSystem.getSeparator()).isEqualTo("/");
		assertThat(fileSystem.getName()).isEqualTo("my-repo");

		assertThat(fileSystem.isOpen()).isTrue();
		assertThat(fileSystem.getFileStores()).isNotNull();
		fileSystem.close();
		assertThat(fileSystem.isOpen()).isFalse();
		assertThat(fileSystem.getFileStores()).isNotNull();
	}

	@Test
	public void testSupportedFileAttributeViews() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		assertThat(fileSystem.isReadOnly()).isFalse();
		assertThat(fileSystem.getSeparator()).isEqualTo("/");
		assertThat(fileSystem.getName()).isEqualTo("my-repo");

		assertThat(fileSystem.supportedFileAttributeViews()).isNotEmpty().hasSize(2).contains("basic"
	}

	@Test
	public void testPathNonBranchRooted() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		final Path path = fileSystem.getPath("/path/to/some/place.txt");

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("/");
	}

	@Test
	public void testPathNonBranchNonRooted() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		final Path path = fileSystem.getPath("path/to/some/place.txt");

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isFalse();
		assertThat(path.toString()).isEqualTo("path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("");
	}

	@Test
	public void testPathBranchRooted() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		final Path path = fileSystem.getPath("test-branch"

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("/");
	}

	@Test
	public void testPathBranchNonRooted() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		final Path path = fileSystem.getPath("test-branch"

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isFalse();
		assertThat(path.toString()).isEqualTo("path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("");
	}

	@Test
	public void testPathBranchRooted2() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		final Path path = fileSystem.getPath("test-branch"

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("/");
	}

	@Test
	public void testPathBranchNonRooted2() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		final Path path = fileSystem.getPath("test-branch"

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isFalse();
		assertThat(path.toString()).isEqualTo("path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("");
	}

	@Test
	public void testFileStore() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final File tempDir = util.createTempDirectory();
		final Git git = setupGit(tempDir);

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		assertThat(fileSystem.getFileStores()).hasSize(1);
		final FileStore fileStore = fileSystem.getFileStores().iterator().next();
		assertThat(fileStore).isNotNull();

		assertThat(fileStore.getTotalSpace()).isEqualTo(tempDir.getTotalSpace());
		assertThat(fileStore.getUsableSpace()).isEqualTo(tempDir.getUsableSpace());
	}

	@Test
	public void testPathEqualsWithDifferentRepos() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git1 = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem1 = new JGitFileSystemImpl(fsProvider
				"my-repo1"

		final Git git2 = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem2 = new JGitFileSystemImpl(fsProvider
				"my-repo2"

		final Path path1 = fileSystem1.getPath("master"
		final Path path2 = fileSystem2.getPath("master"

		AssertionsForClassTypes.assertThat(path1).isNotEqualTo(path2);

		AssertionsForClassTypes.assertThat(path1).isEqualTo(fileSystem1.getPath("/path/to/some.txt"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetUserPrincipalLookupService() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"
		fileSystem.getUserPrincipalLookupService();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetPathMatcher() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"
		fileSystem.getPathMatcher("*");
	}

	@Test
	public void lockShouldSupportMultipleInnerLocksForTheSameThreadTest() throws IOException
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit(util.createTempDirectory());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider
				"my-repo"

		fileSystem.lock();
		fileSystem.lock();
		fileSystem.lock();
		fileSystem.unlock();
		fileSystem.unlock();
		fileSystem.unlock();
	}

	@Test
	public void lockTest() throws IOException

		final Git git = setupGit(util.createTempDirectory());
		JGitFileSystemLock lock = createFSLock(git);

		JGitFileSystemLock lockSpy = spy(lock);

		lockSpy.lock();
		lockSpy.lock();
		lockSpy.lock();
		verify(lockSpy

		lockSpy.unlock();
		lockSpy.unlock();
		lockSpy.unlock();
		verify(lockSpy
	}
}
