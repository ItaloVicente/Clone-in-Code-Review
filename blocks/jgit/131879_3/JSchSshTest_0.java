package org.eclipse.jgit.transport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.sshd.common.config.keys.IdentityUtils;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.common.session.Session;
import org.apache.sshd.common.util.security.SecurityUtils;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.command.AbstractCommandSupport;
import org.apache.sshd.server.shell.UnknownCommand;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;

public class SshGitServer {

	@NonNull
	private String testUser;

	@NonNull
	private PublicKey testKey;

	@NonNull
	private Repository repository;

	private final ExecutorService executorService = Executors
			.newFixedThreadPool(2);

	private final SshServer server;

	public SshGitServer(@NonNull String testUser
			@NonNull Repository repository
			throws IOException
		this.testUser = testUser;
		this.testKey = IdentityUtils
				.loadIdentities(Collections.singletonMap("A"
				.get("A").getPublic();
		this.repository = repository;
		server = SshServer.setUpDefaultServer();
		server.setKeyPairProvider(new KeyPairProvider() {

			@Override
			public Iterable<KeyPair> loadKeys() {
				try (ByteArrayInputStream in = new ByteArrayInputStream(
						hostKey)) {
					return Collections.singletonList(
							SecurityUtils.loadKeyPairIdentity(""
				} catch (IOException | GeneralSecurityException e) {
					return null;
				}
			}

		});
		server.setFileSystemFactory(new VirtualFileSystemFactory() {

			@Override
			protected Path computeRootDir(Session session) throws IOException {
				return SshGitServer.this.repository.getDirectory()
						.getParentFile().getAbsoluteFile().toPath();
			}
		});
		server.setSubsystemFactories(Collections
				.singletonList((new SftpSubsystemFactory.Builder()).build()));
		server.setShellFactory(null);
		server.setPasswordAuthenticator(null);
		server.setKeyboardInteractiveAuthenticator(null);
		server.setGSSAuthenticator(null);
		server.setHostBasedAuthenticator(null);
		server.setPublickeyAuthenticator((userName
			return SshGitServer.this.testUser.equals(userName) && KeyUtils
					.compareKeys(SshGitServer.this.testKey
		});
		server.setCommandFactory(command -> {
			if (command.startsWith("git-upload-pack")
					|| command.startsWith("git upload-pack")) {
				return new GitUploadPackCommand(command
			}
			return new UnknownCommand(command);
		});
	}

	public int start() throws IOException {
		server.start();
		return server.getPort();
	}

	public void stop() throws IOException {
		executorService.shutdownNow();
		server.stop(true);
	}

	private class GitUploadPackCommand extends AbstractCommandSupport {

		protected GitUploadPackCommand(String command
				ExecutorService executorService) {
			super(command
		}

		@Override
		public void run() {
			UploadPack uploadPack = new UploadPack(repository);
			String gitProtocol = getEnvironment().getEnv().get("GIT_PROTOCOL");
			if (gitProtocol != null) {
				uploadPack
						.setExtraParameters(Collections.singleton(gitProtocol));
			}
			try {
				uploadPack.upload(getInputStream()
						getErrorStream());
				onExit(0);
			} catch (IOException e) {
				log.warn(
						MessageFormat.format("Could not run {0}"
						e);
				onExit(-1
			}
		}

	}
}
