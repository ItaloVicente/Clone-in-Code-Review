package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.ListRefs;
import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
import org.eclipse.jgit.niofs.internal.op.model.DefaultCommitContent;
import org.eclipse.jgit.util.FS_POSIX;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.ProcessResult;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_ENABLED;

public abstract class AbstractTestInfra {

	static class TestFile {

		final String path;
		final String content;

		TestFile(final String path
			this.path = path;
			this.content = content;
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(AbstractTestInfra.class);

	protected static final Map<String

	protected static final List<File> tempFiles = new ArrayList<>();

	protected JGitFileSystemProvider provider;

	@Before
	public void createGitFsProvider() throws IOException {
		provider = new JGitFileSystemProvider(getGitPreferences());
	}

	public Map<String
		Map<String
		gitPrefs.put(GIT_DAEMON_ENABLED
		gitPrefs.put(GIT_SSH_ENABLED
		return gitPrefs;
	}

	@After
	public void destroyGitFsProvider() throws IOException {
		if (provider == null) {
			return;
		}

		provider.shutdown();

		if (provider.getGitRepoContainerDir() != null && provider.getGitRepoContainerDir().exists()) {
			FileUtils.delete(provider.getGitRepoContainerDir()
		}
	}

	@AfterClass
	@BeforeClass
	public static void cleanup() {
		for (final File tempFile : tempFiles) {
			try {
				FileUtils.delete(tempFile
			} catch (IOException e) {
			}
		}
	}

	protected Git setupGit() throws IOException
		return setupGit(createTempDirectory());
	}

	protected Git setupGit(final File tempDir) throws IOException

		final Git git = Git.createRepository(tempDir);

		new Commit(git
				new DefaultCommitContent(new HashMap<String
					{
						put("file1.txt"
						put("file2.txt"
					}
				})).execute();

		return git;
	}

	protected static File createTempDirectory() throws IOException {
		final File temp = File.createTempFile("temp"
		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}

		tempFiles.add(temp);

		return temp;
	}

	public static File tempFile(final String content) throws IOException {
		final File file = File.createTempFile("bar"
		final OutputStream out = new FileOutputStream(file);

		if (content != null && !content.isEmpty()) {
			out.write(content.getBytes());
			out.flush();
		}

		out.close();
		return file;
	}

	public File tempFile(final byte[] content) throws IOException {
		final File file = File.createTempFile("bar"
		final FileOutputStream out = new FileOutputStream(file);

		if (content != null && content.length > 0) {
			out.write(content);
			out.flush();
		}

		out.close();
		return file;
	}

	public PersonIdent getAuthor() {
		return new PersonIdent("user"
	}

	public static int findFreePort() {
		int port = 0;
		try {
			ServerSocket server = new ServerSocket(0);
			port = server.getLocalPort();
			server.close();
		} catch (IOException e) {
			Assert.fail("Can't find free port!");
		}
		logger.debug("Found free port " + port);
		return port;
	}

	protected byte[] loadImage(final String path) throws IOException {
		final InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path);
		StringWriter writer = new StringWriter();
		IOUtils.copy(stream
		return writer.toString().getBytes();
	}

	static void commit(final Git origin
			throws IOException {
		final Map<String
		new Commit(origin
	}

	public static File tmpFile(final String content) {
		try {
			return tempFile(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	static TestFile content(final String path
		return new TestFile(path
	}

	void writeMockHook(final File hooksDirectory
			throws FileNotFoundException
		final PrintWriter writer = new PrintWriter(new File(hooksDirectory
		writer.println("# something");
		writer.close();
	}

	void testHook(final String gitRepoName

		final AtomicBoolean hookExecuted = new AtomicBoolean(false);
		final FileSystem fs = provider.newFileSystem(newRepo

		provider.setDetectedFS(new FS_POSIX() {
			@Override
			public ProcessResult runHookIfPresent(Repository repox
					throws JGitInternalException {
				if (hookName.equals(testedHookName)) {
					hookExecuted.set(true);
				}
				return new ProcessResult(ProcessResult.Status.OK);
			}
		});

		assertThat(fs).isNotNull();


		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		final InputStream inStream = provider.newInputStream(path);

		final String content = new Scanner(inStream).useDelimiter("\\A").next();

		inStream.close();

		assertThat(content).isNotNull().isEqualTo("my cool content");

		if (wasExecuted) {
			assertThat(hookExecuted.get()).isTrue();
		} else {
			assertThat(hookExecuted.get()).isFalse();
		}
	}

	protected Ref branch(Git origin
		final Repository repo = origin.getRepository();
		return org.eclipse.jgit.api.Git.wrap(repo).branchCreate().setName(target).setStartPoint(source).call();
	}

	protected List<Ref> listRefs(final Git cloned) {
		return new ListRefs(cloned.getRepository()).execute();
	}

	protected static String multiline(String prefix
		return Arrays.stream(lines).map(s -> prefix + s).reduce((s1
	}
}
