package org.eclipse.jgit.transport.ssh;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.ssh.SshTestGitServer;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FS;
import org.junit.After;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.KeyPair;

public abstract class SshTestHarness extends RepositoryTestCase {

	protected static final String TEST_USER = "testuser";

	protected File sshDir;

	protected File privateKey1;

	protected File privateKey2;

	protected File publicKey1;

	protected SshTestGitServer server;

	private SshSessionFactory factory;

	protected int testPort;

	protected File knownHosts;

	private File homeDir;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		writeTrashFile("file.txt"
		try (Git git = new Git(db)) {
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("Initial commit").call();
		}
		mockSystemReader.setProperty("user.home"
				getTemporaryDirectory().getAbsolutePath());
		mockSystemReader.setProperty("HOME"
				getTemporaryDirectory().getAbsolutePath());
		homeDir = FS.DETECTED.userHome();
		FS.DETECTED.setUserHome(getTemporaryDirectory().getAbsoluteFile());
		sshDir = new File(getTemporaryDirectory()
		assertTrue(sshDir.mkdir());
		File serverDir = new File(getTemporaryDirectory()
		assertTrue(serverDir.mkdir());
		privateKey1 = new File(sshDir
		privateKey2 = new File(sshDir
		publicKey1 = createKeyPair(privateKey1);
		createKeyPair(privateKey2);
		ByteArrayOutputStream publicHostKey = new ByteArrayOutputStream();
		server = new SshTestGitServer(TEST_USER
				createHostKey(publicHostKey));
		testPort = server.start();
		assertTrue(testPort > 0);
		knownHosts = new File(sshDir
		Files.write(knownHosts.toPath()
				+ testPort + ' '
				+ publicHostKey.toString(US_ASCII.name())));
		factory = createSessionFactory();
		SshSessionFactory.setInstance(factory);
	}

	private static File createKeyPair(File privateKeyFile) throws Exception {
		JSch jsch = new JSch();
		KeyPair pair = KeyPair.genKeyPair(jsch
		try (OutputStream out = new FileOutputStream(privateKeyFile)) {
			pair.writePrivateKey(out);
		}
		File publicKeyFile = new File(privateKeyFile.getParentFile()
				privateKeyFile.getName() + ".pub");
		try (OutputStream out = new FileOutputStream(publicKeyFile)) {
			pair.writePublicKey(out
		}
		return publicKeyFile;
	}

	private static byte[] createHostKey(OutputStream publicKey)
			throws Exception {
		JSch jsch = new JSch();
		KeyPair pair = KeyPair.genKeyPair(jsch
		pair.writePublicKey(publicKey
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			pair.writePrivateKey(out);
			out.flush();
			return out.toByteArray();
		}
	}

	protected static String createKnownHostsFile(File file
			int port
		List<String> lines = Files.readAllLines(publicKey.toPath()
		assertEquals("Public key has too many lines"
		String pubKey = lines.get(0);
		String[] parts = pubKey.split("\\s+");
		assertTrue("Unexpected key content"
				parts.length == 2 || parts.length == 3);
		String keyPart = parts[0] + ' ' + parts[1];
		String line = '[' + host + "]:" + port + ' ' + keyPart;
		Files.write(file.toPath()
		return keyPart;
	}

	protected boolean hasHostKey(String host
			List<String> lines) {
		String h = '[' + host + "]:" + port;
		return lines.stream()
				.anyMatch(l -> l.contains(h) && l.contains(keyPart));
	}

	@After
	public void shutdownServer() throws Exception {
		if (server != null) {
			server.stop();
			server = null;
		}
		FS.DETECTED.setUserHome(homeDir);
		SshSessionFactory.setInstance(null);
		factory = null;
	}

	protected abstract SshSessionFactory createSessionFactory();

	protected SshSessionFactory getSessionFactory() {
		return factory;
	}

	protected abstract void installConfig(String... config);

	protected void copyTestResource(String resourceName
			throws IOException {
		copyTestResource(SshTestHarness.class
	}

	protected void copyTestResource(Class<?> loader
			File to) throws IOException {
		try (InputStream in = loader.getResourceAsStream(resourceName)) {
			Files.copy(in
		}
	}

	protected File cloneWith(String uri
			String... config) throws Exception {
		installConfig(config);
		CloneCommand clone = Git.cloneRepository().setCloneAllBranches(true)
				.setDirectory(to).setURI(uri);
		if (provider != null) {
			clone.setCredentialsProvider(provider);
		}
		try (Git git = clone.call()) {
			Repository repo = git.getRepository();
			assertNotNull(repo.resolve("master"));
			assertNotEquals(db.getWorkTree()
					git.getRepository().getWorkTree());
			assertTrue(new File(git.getRepository().getWorkTree()
					.exists());
			return repo.getWorkTree();
		}
	}

	protected void pushTo(File localClone) throws Exception {
		pushTo(null
	}

	protected void pushTo(CredentialsProvider provider
			throws Exception {
		RevCommit commit;
		File newFile = null;
		try (Git git = Git.open(localClone)) {
			Repository local = git.getRepository();
			newFile = File.createTempFile("new"
					local.getWorkTree());
			write(newFile
			File existingFile = new File(local.getWorkTree()
			write(existingFile
			git.add().addFilepattern("file.txt")
					.addFilepattern(newFile.getName())
					.call();
			commit = git.commit().setMessage("Local commit").call();
			PushCommand push = git.push().setPushAll();
			if (provider != null) {
				push.setCredentialsProvider(provider);
			}
			Iterable<PushResult> results = push.call();
			for (PushResult result : results) {
				for (RemoteRefUpdate u : result.getRemoteUpdates()) {
					assertEquals(
							"Could not update " + u.getRemoteName() + ' '
									+ u.getMessage()
							RemoteRefUpdate.Status.OK
				}
			}
		}
		assertEquals("Unexpected remote commit"
		assertEquals("Unexpected remote commit"
				db.resolve(Constants.HEAD));
		File remoteFile = new File(db.getWorkTree()
		assertFalse("File should not exist on remote"
		try (Git git = new Git(db)) {
			git.reset().setMode(ResetType.HARD).setRef(Constants.HEAD).call();
		}
		assertTrue("File does not exist on remote"
		checkFile(remoteFile
	}

	protected static class TestCredentialsProvider extends CredentialsProvider {

		private final List<String> stringStore;

		private final Iterator<String> strings;

		public TestCredentialsProvider(String... strings) {
			if (strings == null || strings.length == 0) {
				stringStore = Collections.emptyList();
			} else {
				stringStore = Arrays.asList(strings);
			}
			this.strings = stringStore.iterator();
		}

		@Override
		public boolean isInteractive() {
			return true;
		}

		@Override
		public boolean supports(CredentialItem... items) {
			return true;
		}

		@Override
		public boolean get(URIish uri
				throws UnsupportedCredentialItem {
			System.out.println("URI: " + uri);
			for (CredentialItem item : items) {
				System.out.println(item.getClass().getSimpleName() + ' '
						+ item.getPromptText());
			}
			logItems(uri
			for (CredentialItem item : items) {
				if (item instanceof CredentialItem.InformationalMessage) {
					continue;
				}
				if (item instanceof CredentialItem.YesNoType) {
					((CredentialItem.YesNoType) item).setValue(true);
				} else if (item instanceof CredentialItem.CharArrayType) {
					if (strings.hasNext()) {
						((CredentialItem.CharArrayType) item)
								.setValue(strings.next().toCharArray());
					} else {
						return false;
					}
				} else if (item instanceof CredentialItem.StringType) {
					if (strings.hasNext()) {
						((CredentialItem.StringType) item)
								.setValue(strings.next());
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
			return true;
		}

		private List<LogEntry> log = new ArrayList<>();

		private void logItems(URIish uri
			log.add(new LogEntry(uri
		}

		public List<LogEntry> getLog() {
			return log;
		}
	}

	protected static class LogEntry {

		private URIish uri;

		private List<CredentialItem> items;

		public LogEntry(URIish uri
			this.uri = uri;
			this.items = items;
		}

		public URIish getURIish() {
			return uri;
		}

		public List<CredentialItem> getItems() {
			return items;
		}
	}
}
