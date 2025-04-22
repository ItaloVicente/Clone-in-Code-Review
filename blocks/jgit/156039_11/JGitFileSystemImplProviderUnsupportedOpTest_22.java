package org.eclipse.jgit.niofs.internal;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_PORT;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.assertj.core.api.AssertionsForClassTypes;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.niofs.fs.AmbiguousFileSystemNameException;
import org.eclipse.jgit.niofs.fs.FileSystemState;
import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributeView;
import org.eclipse.jgit.niofs.fs.attribute.VersionRecord;
import org.eclipse.jgit.niofs.fs.options.CommentedOption;
import org.eclipse.jgit.niofs.fs.options.SquashOption;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider.RepositoryResolverImpl;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
import org.eclipse.jgit.niofs.internal.manager.JGitFileSystemsManager;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.GetRef;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
import org.eclipse.jgit.niofs.internal.op.model.PathType;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class JGitFileSystemImplProviderTest extends BaseTest {

	private int gitDaemonPort;

	@Override
	public Map<String
		Map<String
		gitPrefs.put(GIT_DAEMON_ENABLED
		gitDaemonPort = findFreePort();
		gitPrefs.put(GIT_DAEMON_PORT
		System.out.println(gitDaemonPort);
		return gitPrefs;
	}

	@Test
	public void testNewFileSystem() throws IOException {

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo)
		assertThat(stream).isNotNull().hasSize(0);

		try {
			provider.newFileSystem(newRepo
			failBecauseExceptionWasNotThrown(FileSystemAlreadyExistsException.class);
		} catch (final Exception ignored) {
		}

	}

	@Test
	public void testNewFileSystemInited() throws IOException {

		final Map<String
			{
				put("init"
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo)
		assertThat(stream).isNotNull().hasSize(1);
	}

	@Test
	public void testInvalidURINewFileSystem() throws IOException {

		try {
			provider.newFileSystem(newRepo
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (final IllegalArgumentException ex) {
			assertThat(ex.getMessage()).isEqualTo("Parameter named 'uri' is invalid
		}
	}

	@Test
	public void testNewFileSystemClone() throws IOException {


		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		assertThat(fs.getRootDirectories()).hasSize(1);

		assertThat(fs.getPath("file.txt").toFile()).isNotNull().exists();

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("fileXXXXX.txt"
					}
				}).execute();

		provider.getFileSystem(URI.create(

		assertThat(fs).isNotNull();

		assertThat(fs.getRootDirectories()).hasSize(1);

		for (final Path root : fs.getRootDirectories()) {
			if (root.toAbsolutePath().toUri().toString().contains("upstream")) {
				assertThat(provider.newDirectoryStream(root
			} else if (root.toAbsolutePath().toUri().toString().contains("origin")) {
				assertThat(provider.newDirectoryStream(root
			} else {
				assertThat(provider.newDirectoryStream(root
			}
		}

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("fileYYYY.txt"
					}
				}).execute();

		provider.getFileSystem(URI.create(

		assertThat(fs.getRootDirectories()).hasSize(1);

		assertThat(provider.newDirectoryStream(fs.getRootDirectories().iterator().next()
				.hasSize(3);
	}

	@Test
	public void testNewFileSystemCloneAndPush() throws IOException {


		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		assertThat(fs.getRootDirectories()).hasSize(1);

		assertThat(fs.getPath("file.txt").toFile()).isNotNull().exists();

		new Commit(((JGitFileSystem) fs).getGit()
				new HashMap<String
					{
						put("fileXXXXX.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
			}
		};

		final FileSystem fs2 = provider.newFileSystem(newRepo2

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file1UserBranch.txt"
					}
				}).execute();

		provider.getFileSystem(URI

		assertThat(fs2.getRootDirectories()).hasSize(2);

		final List<String> rootURIs1 = new ArrayList<String>() {
			{
			}
		};

		final List<String> rootURIs2 = new ArrayList<String>() {
			{
			}
		};

		final Set<String> rootURIs = new HashSet<String>();
		for (final Path root : fs2.getRootDirectories()) {
			rootURIs.add(root.toUri().toString());
		}

		rootURIs.removeAll(rootURIs1);

		assertThat(rootURIs).isEmpty();

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file2UserBranch.txt"
					}
				}).execute();

		provider.getFileSystem(URI

		assertThat(fs2.getRootDirectories()).hasSize(3);

		for (final Path root : fs2.getRootDirectories()) {
			rootURIs.add(root.toUri().toString());
		}

		rootURIs.removeAll(rootURIs2);

		assertThat(rootURIs).isEmpty();
	}

	@Test
	public void testNewFileSystemCloneAndRescan() throws IOException {


		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		assertThat(fs.getRootDirectories()).hasSize(1);

		final FileSystem fs2 = provider.getFileSystem(newRepo);

		assertThat(fs2).isNotNull();

		assertThat(fs2.getRootDirectories()).hasSize(1);
	}

	@Test
	public void testGetFileSystem() throws IOException {

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		assertThat(provider.getFileSystem(newRepo)).isEqualTo(fs);

	}

	@Test
	public void testInvalidURIGetFileSystem() {

		try {
			provider.getFileSystem(newRepo);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (final IllegalArgumentException ex) {
			assertThat(ex.getMessage()).isEqualTo("Parameter named 'uri' is invalid
		}
	}

	@Test
	public void testGetPath() throws IOException {

		provider.newFileSystem(newRepo


		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.getRoot().toString()).isEqualTo("/");
		Path root = path.getRoot();
		Path path1 = root.toRealPath();
		assertThat(path.toString()).isEqualTo("/home");

		AssertionsForClassTypes.assertThat(pathRelative).isNotNull();
		assertThat(pathRelative.getRoot().toString()).isEqualTo("");
		assertThat(pathRelative.toString()).isEqualTo("home");
	}

	@Test
	public void testInvalidURIGetPath() {

		try {
			provider.getPath(uri);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (final IllegalArgumentException ex) {
			assertThat(ex.getMessage()).isEqualTo("Parameter named 'uri' is invalid
		}
	}

	@Test
	public void testGetComplexPath() throws IOException {

		provider.newFileSystem(newRepo


		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.getRoot().toString()).isEqualTo("/");
		assertThat(path.toString()).isEqualTo("/home");

		AssertionsForClassTypes.assertThat(pathRelative).isNotNull();
		assertThat(pathRelative.getRoot().toString()).isEqualTo("");
		assertThat(pathRelative.toString()).isEqualTo("home");
	}

	@Test
	public void testGetComplexPathComposed() throws IOException {

		provider.newFileSystem(newRepo


		AssertionsForClassTypes.assertThat(path1).isNotNull();
		assertThat(path1.getRoot().toString()).isEqualTo("/");
		assertThat(path1.toString()).isEqualTo("/home");


		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.getRoot().toString()).isEqualTo("/");
		assertThat(path.toString()).isEqualTo("/home");

		final Path pathRelative = provider
		AssertionsForClassTypes.assertThat(pathRelative).isNotNull();
		assertThat(pathRelative.getRoot().toString()).isEqualTo("");
		assertThat(pathRelative.toString()).isEqualTo("home");
	}

	@Test
	public void testInputStream() throws IOException {
		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("myfile.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();


		final String content = extractContent(path);

		assertThat(content).isNotNull().isEqualTo("temp\n.origin\n.content");
	}

	@Test
	public void testInputStream2() throws IOException {

		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("path/to/file/myfile.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();


		final String content = extractContent(path);

		assertThat(content).isNotNull().isEqualTo("temp\n.origin\n.content");
	}

	@Test(expected = NoSuchFileException.class)
	public void testInputStream3() throws IOException {

		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("path/to/file/myfile.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();


		provider.newInputStream(path);
	}

	@Test(expected = NoSuchFileException.class)
	public void testInputStreamNoSuchFile() throws IOException {

		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("file.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();


		provider.newInputStream(path);
	}

	@Test
	public void testNewOutputStream() throws Exception {
		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("myfile.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/to/some/file/myfile.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();


		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		final InputStream inStream = provider.newInputStream(path);

		final String content = new Scanner(inStream).useDelimiter("\\A").next();

		inStream.close();

		assertThat(content).isNotNull().isEqualTo("my cool content");

		try {
			failBecauseExceptionWasNotThrown(IOException.class);
		} catch (Exception ignored) {
		}
	}

	@Test
	public void testNewOutputStreamWithJGitOp() throws Exception {
		final File parentFolder = util.createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin
				new HashMap<String
					{
						put("myfile.txt"
					}
				}).execute();
		new Commit(origin
				new HashMap<String
					{
						put("path/to/some/file/myfile.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		final CommentedOption op = new CommentedOption("User Tester"
				formatter.parse("31/12/2012"));

		final Path path = provider

		final OutputStream outStream = provider.newOutputStream(path
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		final InputStream inStream = provider.newInputStream(path);

		final String content = new Scanner(inStream).useDelimiter("\\A").next();

		inStream.close();

		assertThat(content).isNotNull().isEqualTo("my cool content");
	}

	@Test(expected = FileSystemNotFoundException.class)
	public void testGetPathFileSystemNotExisting() {
	}

	@Test(expected = FileSystemNotFoundException.class)
	public void testGetFileSystemNotExisting() {

		provider.getFileSystem(newRepo);
	}

	@Test
	public void testDeleteShouldRemoveEmptyParentDir() throws IOException {

		FileSystem doraFS = provider.newFileSystem(doraRepo

		final File doraRepoDir = ((JGitFileSystemProxy) doraFS).getGit().getRepository().getDirectory();

		final File parentDir = doraRepoDir.getParentFile();
		final File gitProviderDir = provider.getGitRepoContainerDir();

		FileSystem doraFS1 = provider.newFileSystem(doraRepo1
		final File dora1RepoDir = ((JGitFileSystemProxy) doraFS1).getGit().getRepository().getDirectory();

		final File parentDir1 = doraRepoDir.getParentFile();

		assertEquals(parentDir

		provider.delete(doraFS.getPath(null));
		assertFalse(doraRepoDir.exists());
		assertTrue(parentDir.exists());
		assertTrue(gitProviderDir.exists());

		provider.delete(doraFS1.getPath(null));
		assertFalse(dora1RepoDir.exists());
		assertTrue(parentDir1.exists());
		assertTrue(gitProviderDir.exists());
	}

	@Test
	public void testDelete() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		provider.newInputStream(path).close();

		try {
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}

		try {
			failBecauseExceptionWasNotThrown(DirectoryNotEmptyException.class);
		} catch (DirectoryNotEmptyException ignored) {
		}

		provider.delete(path);

		try {
			provider.newFileSystem(newRepo
			failBecauseExceptionWasNotThrown(FileSystemAlreadyExistsException.class);
		} catch (FileSystemAlreadyExistsException ignored) {
		}

		final Path fsPath = path.getFileSystem().getPath(null);
		provider.delete(fsPath);
		assertThat(fsPath.getFileSystem().isOpen()).isEqualTo(false);

		provider.newFileSystem(newRepo2
	}

	@Test
	public void testDeleteBranch() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		provider.newInputStream(path).close();


		try {
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}

		try {
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}
	}

	@Test
	public void testDeleteIfExists() throws IOException {
		provider.newFileSystem(newRepo

		final Path path = provider

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		provider.newInputStream(path).close();

		assertThat(provider.deleteIfExists(
						.isFalse();

		try {
			provider.deleteIfExists(
			failBecauseExceptionWasNotThrown(DirectoryNotEmptyException.class);
		} catch (DirectoryNotEmptyException ignored) {
		}

		assertThat(provider.deleteIfExists(path)).isTrue();
	}

	@Test
	public void testDeleteBranchIfExists() throws IOException {
		provider.newFileSystem(newRepo

		final Path path = provider

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		provider.newInputStream(path).close();

		assertThat(provider
						.isTrue();

		assertThat(provider
						.isFalse();

		assertThat(provider
						.isFalse();
	}

	@Test
	public void testIsHidden() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();


		final OutputStream outStream2 = provider.newOutputStream(path2);
		assertThat(outStream2).isNotNull();
		outStream2.write("my cool content".getBytes());
		outStream2.close();

		assertThat(provider
						.isTrue();

		assertThat(provider
						.isFalse();

		assertThat(provider.isHidden(
						.isTrue();

		assertThat(provider.isHidden(
						.isFalse();


				.isFalse();
	}

	@Test
	public void testIsSameFile() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();


		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		assertThat(provider.isSameFile(path

		assertThat(provider.isSameFile(path
	}

	@Test
	public void testCreateDirectory() throws IOException {
		provider.newFileSystem(newRepo

		final JGitPathImpl path = (JGitPathImpl) provider

		final PathInfo result = path.getFileSystem().getGit().getPathInfo(path.getRefTree()
		assertThat(result.getPathType()).isEqualTo(PathType.NOT_FOUND);

		provider.createDirectory(path);

		final PathInfo resultAfter = path.getFileSystem().getGit().getPathInfo(path.getRefTree()
		assertThat(resultAfter.getPathType()).isEqualTo(PathType.DIRECTORY);

		final Path gitkeepPath = path.resolve(".gitkeep");
		assertThat(provider.exists(gitkeepPath)).isEqualTo(true);

		try {
			provider.createDirectory(path);
			failBecauseExceptionWasNotThrown(FileAlreadyExistsException.class);
		} catch (FileAlreadyExistsException ignored) {
		}
	}

	@Test
	public void testCheckAccess() throws Exception {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		provider.checkAccess(path);


		provider.checkAccess(path_to_dir);

		final Path path_not_exists = provider

		try {
			provider.checkAccess(path_not_exists);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}
	}

	@Test
	public void testGetFileStore() throws Exception {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final FileStore fileStore = provider.getFileStore(path);

		assertThat(fileStore).isNotNull();

		assertThat(fileStore.getAttribute("readOnly")).isEqualTo(Boolean.FALSE);
	}

	@Test
	public void testNewDirectoryStream() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();


		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		final DirectoryStream<Path> stream1 = provider

		assertThat(stream1).isNotNull().hasSize(2).contains(path3

		final DirectoryStream<Path> stream2 = provider

		assertThat(stream2).isNotNull().hasSize(1)

		final DirectoryStream<Path> stream3 = provider.newDirectoryStream(

		assertThat(stream3).isNotNull().hasSize(1).contains(path2);

		final DirectoryStream<Path> stream4 = provider

		assertThat(stream4).isNotNull().hasSize(1).contains(path);

		try {
			provider.newDirectoryStream(path
			failBecauseExceptionWasNotThrown(NotDirectoryException.class);
		} catch (NotDirectoryException ignored) {
		}
		try {
			provider.newDirectoryStream(crazyPath
			failBecauseExceptionWasNotThrown(NotDirectoryException.class);
		} catch (NotDirectoryException ignored) {
		}

		provider.createDirectory(crazyPath);

		assertThat(provider.newDirectoryStream(crazyPath
	}

	@Test
	public void testFilteredNewDirectoryStream() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();


		final OutputStream outStream4 = provider.newOutputStream(path4);
		outStream4.write("my cool content".getBytes());
		outStream4.close();

		final DirectoryStream<Path> stream1 = provider.newDirectoryStream(
				entry -> entry.toString().endsWith(".xxx"));

		assertThat(stream1).isNotNull().hasSize(1).contains(path4);

		final DirectoryStream<Path> stream2 = provider.newDirectoryStream(

		assertThat(stream2).isNotNull().hasSize(0);
	}

	@Test
	public void testGetFileAttributeView() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path3
				JGitVersionAttributeViewImpl.class);

		assertThat(attrs.readAttributes().history().records().size()).isEqualTo(1);
		assertThat(attrs.readAttributes().history().records().get(0).uri()).isNotNull();

		assertThat(attrs.readAttributes().isDirectory()).isFalse();
		assertThat(attrs.readAttributes().isRegularFile()).isTrue();
		assertThat(attrs.readAttributes().creationTime()).isNotNull();
		assertThat(attrs.readAttributes().lastModifiedTime()).isNotNull();
		assertThat(attrs.readAttributes().size()).isEqualTo(15L);

		try {
			provider.getFileAttributeView(
					BasicFileAttributeView.class);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (Exception ignored) {
		}

		assertThat(provider.getFileAttributeView(path3


		final BasicFileAttributeView attrsRoot = provider.getFileAttributeView(rootPath

		assertThat(attrsRoot.readAttributes().isDirectory()).isTrue();
		assertThat(attrsRoot.readAttributes().isRegularFile()).isFalse();
		assertThat(attrsRoot.readAttributes().creationTime()).isNotNull();
		assertThat(attrsRoot.readAttributes().lastModifiedTime()).isNotNull();
		assertThat(attrsRoot.readAttributes().size()).isEqualTo(-1L);

		final Path prRootPath = provider

		final HiddenAttributeView extendedAttrs = provider.getFileAttributeView(prRootPath

		assertThat(extendedAttrs.readAttributes().isDirectory()).isTrue();
		assertThat(extendedAttrs.readAttributes().isRegularFile()).isFalse();
		assertThat(extendedAttrs.readAttributes().isHidden()).isEqualTo(true);
		assertThat(extendedAttrs.readAttributes().size()).isEqualTo(-1L);
		assertThat(extendedAttrs.readAttributes().creationTime()).isNotNull();
		assertThat(extendedAttrs.readAttributes().lastModifiedTime()).isNotNull();
	}

	@Test
	public void testReadAttributes() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();


		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		final BasicFileAttributes attrs = provider.readAttributes(path3

		assertThat(attrs.isDirectory()).isFalse();
		assertThat(attrs.isRegularFile()).isTrue();
		assertThat(attrs.creationTime()).isNotNull();
		assertThat(attrs.lastModifiedTime()).isNotNull();
		assertThat(attrs.size()).isEqualTo(15L);

		try {
			provider.readAttributes(
					BasicFileAttributes.class);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}

		assertThat(provider.readAttributes(path3


		final BasicFileAttributes attrsRoot = provider.readAttributes(rootPath

		assertThat(attrsRoot.isDirectory()).isTrue();
		assertThat(attrsRoot.isRegularFile()).isFalse();
		assertThat(attrsRoot.creationTime()).isNotNull();
		assertThat(attrsRoot.lastModifiedTime()).isNotNull();
		assertThat(attrsRoot.size()).isEqualTo(-1L);
	}

	@Test
	public void testReadAttributesMap() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path

		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path
		assertThat(provider.readAttributes(path

		try {
			provider.readAttributes(path
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignored) {
		}

		try {
			provider.readAttributes(path
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}


		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
				.hasSize(2);
		assertThat(provider.readAttributes(rootPath

		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath
		assertThat(provider.readAttributes(rootPath

		try {
			provider.readAttributes(rootPath
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignored) {
		}

		try {
			provider.readAttributes(rootPath
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.readAttributes(
					BasicFileAttributes.class);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}
	}

	@Test
	public void testSetAttribute() throws IOException {
		provider.newFileSystem(newRepo


		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();


		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();


		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		try {
			provider.setAttribute(path3
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.setAttribute(path3
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.setAttribute(path3
			failBecauseExceptionWasNotThrown(IllegalStateException.class);
		} catch (IllegalStateException ignored) {
		}

		try {
			provider.setAttribute(path3
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.setAttribute(path3
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignored) {
		}
	}

	private static class MyInvalidFileAttributeView implements BasicFileAttributeView {

		@Override
		public BasicFileAttributes readAttributes() throws IOException {
			return null;
		}

		@Override
		public void setTimes(FileTime lastModifiedTime
				throws IOException {

		}

		@Override
		public String name() {
			return null;
		}
	}

	@Test
	public void checkProperAmend() throws Exception {


		final FileSystem fs = provider.newFileSystem(newRepo
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT
			}
		});

		assertThat(fs).isNotNull();

		for (int z = 0; z < 5; z++) {
			final Path _path = provider
			provider.setAttribute(_path
			{
				final Path path = provider
				final OutputStream outStream = provider.newOutputStream(path);
				assertThat(outStream).isNotNull();
				outStream.write(("my cool content" + z).getBytes());
				outStream.close();
			}
			{
				final Path path2 = provider
				final OutputStream outStream2 = provider.newOutputStream(path2);
				assertThat(outStream2).isNotNull();
				outStream2.write(("bad content" + z).getBytes());
				outStream2.close();
			}

			provider.setAttribute(_path
		}

		final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path.getRoot()
				JGitVersionAttributeViewImpl.class);

		assertThat(attrs.readAttributes().history().records().size()).isEqualTo(5);
	}

	@Test
	public void accessOldVersions() throws Exception {


		final FileSystem fs = provider.newFileSystem(newRepo
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT
			}
		});

		assertThat(fs).isNotNull();

		for (int i = 0; i < 5; i++) {
			final OutputStream outStream = provider.newOutputStream(path);
			assertThat(outStream).isNotNull();
			outStream.write(("my cool content" + i).getBytes());
			outStream.close();
		}

		final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path
				JGitVersionAttributeViewImpl.class);

		assertThat(attrs.readAttributes().history().records().size()).isEqualTo(5);

		for (int i = 0; i < 5; i++) {
			final Path oldPath = provider
							+ "@old-versions-test-repo/some/path/myfile.txt"));
			final InputStream stream = provider.newInputStream(oldPath);
			assertNotNull(stream);
			final String content = new Scanner(stream).useDelimiter("\\A").next();
			assertEquals("my cool content" + i
		}
	}

	@Test
	public void checkProperSquash() throws IOException

		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo


		final OutputStream aStream = provider.newOutputStream(path);
		aStream.write("my cool content".getBytes());
		aStream.close();
		final RevCommit commit = ((GitImpl) fs.getGit())._log().add(fs.getGit().getRef("master").getObjectId())
				.setMaxCount(1).call().iterator().next();

		final OutputStream bStream = provider.newOutputStream(path2);
		bStream.write("my cool content".getBytes());
		bStream.close();
		final OutputStream cStream = provider.newOutputStream(path3);
		cStream.write("my cool content".getBytes());
		cStream.close();

		final VersionRecord record = makeVersionRecord("aparedes"
				commit.getName());
		final SquashOption squashOption = new SquashOption(record);

		provider.setAttribute(generalPath

		int commitsCount = 0;
		for (RevCommit com : ((GitImpl) fs.getGit())._log().all().call()) {
			commitsCount++;
			System.out.println(com.getName() + " - " + com.getFullMessage());
		}
		assertThat(commitsCount).isEqualTo(2);
	}

	@Test(expected = GitException.class)
	public void testSquashFailBecauseCommitIsFromAnotherBranch() throws IOException

		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo


		final OutputStream aStream = provider.newOutputStream(path);
		aStream.write("my cool content".getBytes());
		aStream.close();

		final List<RevCommit> commits = getCommitsFromBranch((GitImpl) fs.getGit()

		final OutputStream bStream = provider.newOutputStream(path2);
		bStream.write("my cool content".getBytes());
		bStream.close();
		final OutputStream cStream = provider.newOutputStream(path3);
		cStream.write("my cool content".getBytes());
		cStream.close();

		final VersionRecord record = makeVersionRecord("aparedes"
				commits.get(0).getName());
		final SquashOption squashOption = new SquashOption(record);

		provider.setAttribute(generalPath
	}

	@Test
	public void checkBatchError() throws Exception {

		final FileSystem fs = provider.newFileSystem(newRepo
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT
			}
		});

		provider = spy(provider);

		doThrow(new RuntimeException()).when(provider).notifyDiffs(any(JGitFileSystemImpl.class)
				any(String.class)

		assertThat(fs).isNotNull();

		provider.setAttribute(path
		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write(("my cool content").getBytes());
		outStream.close();

		try {
			provider.setAttribute(path
		} catch (Exception ex) {
			fail("Batch can't fail!"
		}
	}

	@Test
	public void resolveFSName() {

		String fsName = "dora-repo";
		assertEquals(fsName
		assertEquals(fsName

		assertEquals(fsName
		assertEquals(fsName

		fsName = "dora-repo/subdir";
		assertEquals(fsName
		assertEquals("dora-repo/subdir"

		assertEquals("dora-repo/subdir"
		assertEquals("dora-repo/subdir"

		fsName = "dora-repo/subdir/subdir";
		assertEquals(fsName
		assertEquals(fsName

		assertEquals(fsName
		assertEquals(fsName
	}

	@Test
	public void resolveSimpleFSNames() throws IOException {


		try {
			fail("should triggered FileSystemNotFoundException");
		} catch (FileSystemNotFoundException e) {
		}

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();


		assertEquals(fs
		assertEquals(path.getFileSystem()
	}

	@Test
	public void resolveComposedFSNames() throws IOException {


		final FileSystem fsSimpleName = provider.newFileSystem(simpleName

		assertThat(fsSimpleName).isNotNull();


		final FileSystem fsComposedName = provider.newFileSystem(composedName

		assertThat(fsComposedName).isNotNull();

		assertNotSame(fsSimpleName

		assertEquals(fsSimpleName

		assertEquals(fsComposedName


		assertEquals(fsSimpleName


		assertEquals(fsComposedName
	}

	@Test
	public void validFSNameTest() throws IOException {




	}

	private void checkAmbiguousFS(String fsOriginalName
		provider.newFileSystem(URI.create(fsOriginalName)
		try {
			for (String fsName : ambiguousFsName) {
				provider.newFileSystem(URI.create(fsName)
			}
			fail("ambiguous fs");
		} catch (AmbiguousFileSystemNameException e) {
		}
	}

	@Test
	public void checkRootPath() throws IOException {


		FileSystem fsComposedName = provider.newFileSystem(composedName


		assertEquals(fsComposedName

		assertEquals(fsComposedName
	}

	@Test
	public void getPathForComposedFSNames() throws IOException {


		FileSystem fsComposedName = provider.newFileSystem(composedName

		Path path = provider.getPath(simpleFileName);

		assertEquals(fsComposedName
		assertEquals("/file.txt"


		FileSystem fsSimpleName = provider.newFileSystem(simpleName


		path = provider.getPath(composedFileName);

		assertEquals(fsSimpleName
		assertEquals("/subdir1/file.txt"


		path = provider.getPath(composedFileName);

		assertEquals(fsSimpleName
		assertEquals("/subdir1/subdir2/file.txt"


		path = provider.getPath(composedFileName);

		assertEquals(fsSimpleName
		assertEquals("/subdir1/subdir2/subdir3"
	}

	@Test
	public void getPathForComposedFSNames2() throws IOException {

		FileSystem fsComposedName1 = provider.newFileSystem(composedName


		FileSystem fsComposedName2 = provider.newFileSystem(composedName2


		Path path1 = provider.getPath(composedFileName1);


		Path path2 = provider.getPath(composedFileName2);

		assertNotEquals(fsComposedName1
		assertNotEquals(path1.getFileSystem()

		assertEquals(path2.toString()
	}

	@Test
	public void extractPathTest() throws IOException {


		FileSystem fsComposedName1 = provider.newFileSystem(composedName


		Path path1 = provider.getPath(composedFileName1);

		assertEquals(path1.toString()
	}

	@Test
	public void resolveByRepositoryTest() throws IOException {

				EMPTY_ENV)).getRealJGitFileSystem();

		JGitFileSystemProvider.RepositoryResolverImpl<Object> objectRepositoryResolver = provider.new RepositoryResolverImpl<>();

		assertEquals(fsSimpleName

				EMPTY_ENV)).getRealJGitFileSystem();

		assertEquals(fsComposedName1
				objectRepositoryResolver.resolveFileSystem(fsComposedName1.getGit().getRepository()));
	}

	@Test
	public void extractFSHooksTest() {
		Map<String

		Object hook = (FileSystemHooks.FileSystemHook) context -> {
		};

		env.put("dora"
		env.put(FileSystemHooks.ExternalUpdate.name()

		Map<FileSystemHooks

		assertEquals(1
		assertTrue(fileSystemHooksMap.keySet().contains(FileSystemHooks.ExternalUpdate));
		assertEquals(hook
	}

	@Test
	public void extractCheckBranchAccessHookTest() {
		Map<String

		Object hook = (FileSystemHooks.FileSystemHook) context -> {
		};

		env.put("dora"
		env.put(FileSystemHooks.BranchAccessCheck.name()

		Map<FileSystemHooks

		assertEquals(1
		assertTrue(fileSystemHooksMap.keySet().contains(FileSystemHooks.BranchAccessCheck));
		assertEquals(hook
	}

	@Test
	public void testCloseFileSystem() throws IOException {

		JGitFileSystemProvider fsProvider = spy(new JGitFileSystemProvider(getGitPreferences()) {

			@Override
			protected void setupFileSystemsManager() {
				fsManager = mock(JGitFileSystemsManager.class);
				when(fsManager.allTheFSAreClosed()).thenReturn(true);
			}
		});

		fsProvider.onCloseFileSystem(mock(JGitFileSystem.class));

		verify(fsProvider
	}

	@Test
	public void moveBranchesTest() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby another-branch".getBytes());
			outStream.close();
		}


		provider.move(source

		Throwable extractContentCall = catchThrowable(

		assertThat(extractContentCall).isInstanceOf(NoSuchFileException.class);

		final String contentMoved = extractContent(

		assertThat(contentMoved).isNotNull().isEqualTo("little baby another-branch");
	}

	@Test
	public void moveBranchesNotAtTheSameFSShouldNotBeAllowedTest() throws IOException {
		provider.newFileSystem(newRepo

		provider.newFileSystem(anotherRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby another-branch".getBytes());
			outStream.close();
		}


		assertThatThrownBy(() -> provider.move(source
	}

	@Test
	public void copyBranchesNotAtTheSameFSShouldNotBeAllowedTest() throws IOException {
		provider.newFileSystem(newRepo

		provider.newFileSystem(anotherRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby another-branch".getBytes());
			outStream.close();
		}


		assertThatThrownBy(() -> provider.copy(source
	}

	@Test
	public void copyBranchesTest() throws IOException {
		provider.newFileSystem(newRepo

		{

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby another-branch".getBytes());
			outStream.close();
		}


		provider.copy(source

		final String originalContent = extractContent(

		assertThat(originalContent).isNotNull().isEqualTo("little baby another-branch");

		final String contentMoved = extractContent(

		assertThat(contentMoved).isNotNull().isEqualTo("little baby another-branch");
	}

	private String extractContent(Path path) throws IOException {
		final InputStream inputStream = provider.newInputStream(path);
		assertThat(inputStream).isNotNull();

		final String content = new Scanner(inputStream).useDelimiter("\\A").next();

		inputStream.close();

		return content;
	}

	private interface MyAttrs extends BasicFileAttributes {

	}

	private VersionRecord makeVersionRecord(final String author
			final Date date
		return new VersionRecord() {
			@Override
			public String id() {
				return commit;
			}

			@Override
			public String author() {
				return author;
			}

			@Override
			public String email() {
				return email;
			}

			@Override
			public String comment() {
				return comment;
			}

			@Override
			public Date date() {
				return date;
			}

			@Override
			public String uri() {
				return null;
			}
		};
	}

	private List<RevCommit> getCommitsFromBranch(final GitImpl origin
			throws GitAPIException
		List<RevCommit> commits = new ArrayList<>();
		final ObjectId id = new GetRef(origin.getRepository()
		for (RevCommit commit : origin._log().add(id).call()) {
			commits.add(commit);
		}
		return commits;
	}
}
