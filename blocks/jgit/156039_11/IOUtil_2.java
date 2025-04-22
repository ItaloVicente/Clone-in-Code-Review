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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
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
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.toMap;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_NIO_DIR;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_ENABLED;

public abstract class BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	protected static final Map<String

	public IOUtil util;
	public JGitFileSystemProvider provider;

	static class TestFile {

		final String path;
		final String content;

		TestFile(final String path
			this.path = path;
			this.content = content;
		}
	}

	@After
	@Before
	public void clean() {
		if (util != null) {
			cleanup();
		}
		util = new IOUtil();
		try {
			provider = new JGitFileSystemProvider(getGitPreferences());
		} catch (IOException e) {
		}
	}

	private void cleanup() {
		if (provider == null) {
			return;
		}

		provider.shutdown();

		if (provider.getGitRepoContainerDir() != null && provider.getGitRepoContainerDir().exists()) {
			try {
				FileUtils.delete(provider.getGitRepoContainerDir()
								 FileUtils.RECURSIVE | FileUtils.SKIP_MISSING | FileUtils.IGNORE_ERRORS);
			} catch (IOException e) {
			}
		}
		util.cleanup();
	}

	public Map<String
		Map<String
		gitPrefs.put(GIT_DAEMON_ENABLED
		gitPrefs.put(GIT_SSH_ENABLED
		final String file;
		try {
			file = util.createTempDirectory().getAbsolutePath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		gitPrefs.put(GIT_NIO_DIR
		return gitPrefs;
	}

	static String multiline(String prefix
		return Arrays.stream(lines).map(s -> prefix + s).reduce((s1
	}

	static TestFile content(final String path
		return new TestFile(path
	}

	static void commit(final Git origin
			throws IOException {
		final Map<String
		new Commit(origin
	}

	static File tmpFile(final String content) {
		try {
			return tempFile(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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

	static File tempFile(final byte[] content) throws IOException {
		final File file = File.createTempFile("bar"
		final FileOutputStream out = new FileOutputStream(file);

		if (content != null && content.length > 0) {
			out.write(content);
			out.flush();
		}

		out.close();
		return file;
	}

	public static Git setupGit(final File tempDir) throws IOException

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

	static Ref branch(Git origin
		final Repository repo = origin.getRepository();
		return org.eclipse.jgit.api.Git.wrap(repo).branchCreate().setName(target).setStartPoint(source).call();
	}

	static List<Ref> listRefs(final Git cloned) {
		return new ListRefs(cloned.getRepository()).execute();
	}

	static void writeMockHook(final File hooksDirectory
			throws FileNotFoundException
		final PrintWriter writer = new PrintWriter(new File(hooksDirectory
		writer.println("# something");
		writer.close();
	}

	static int findFreePort() {
		int port = 0;
		try {
			ServerSocket server = new ServerSocket(0);
			port = server.getLocalPort();
			server.close();
		} catch (IOException e) {
			throw new RuntimeException("Can't find free port!");
		}
		logger.debug("Found free port " + port);
		return port;
	}

	static void testHook(final JGitFileSystemProvider provider

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

		Assertions.assertThat(fs).isNotNull();


		final OutputStream outStream = provider.newOutputStream(path);
		Assertions.assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		final InputStream inStream = provider.newInputStream(path);

		final String content = new Scanner(inStream).useDelimiter("\\A").next();

		inStream.close();

		Assertions.assertThat(content).isNotNull().isEqualTo("my cool content");

		if (wasExecuted) {
			Assertions.assertThat(hookExecuted.get()).isTrue();
		} else {
			Assertions.assertThat(hookExecuted.get()).isFalse();
		}
	}

	public PersonIdent getAuthor() {
		return new PersonIdent("user"
	}

	public static byte[] loadImage(final String path) throws IOException {
		final InputStream stream = BaseTest.class.getClassLoader().getResourceAsStream(path);
		StringWriter writer = new StringWriter();
		IOUtils.copy(stream
		return writer.toString().getBytes();
	}
}
