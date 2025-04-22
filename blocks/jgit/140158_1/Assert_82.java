package org.eclipse.jgit.junit.ssh;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.SshConstants;
import org.apache.sshd.common.config.keys.AuthorizedKeyEntry;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.config.keys.PublicKeyEntryResolver;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.session.Session;
import org.apache.sshd.common.util.buffer.Buffer;
import org.apache.sshd.common.util.security.SecurityUtils;
import org.apache.sshd.server.ServerAuthenticationManager;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.UserAuth;
import org.apache.sshd.server.auth.gss.GSSAuthenticator;
import org.apache.sshd.server.auth.gss.UserAuthGSS;
import org.apache.sshd.server.auth.gss.UserAuthGSSFactory;
import org.apache.sshd.server.auth.keyboard.DefaultKeyboardInteractiveAuthenticator;
import org.apache.sshd.server.command.AbstractCommandSupport;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.UnknownCommand;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.UploadPack;

public class SshTestGitServer {

	@NonNull
	protected final String testUser;

	@NonNull
	protected final Repository repository;

	@NonNull
	protected final List<KeyPair> hostKeys = new ArrayList<>();

	protected final SshServer server;

	@NonNull
	protected PublicKey testKey;

	private final ExecutorService executorService = Executors
			.newFixedThreadPool(2);

	public SshTestGitServer(@NonNull String testUser
			@NonNull Repository repository
			throws IOException
		this.testUser = testUser;
		setTestUserPublicKey(testKey);
		this.repository = repository;
		server = SshServer.setUpDefaultServer();
		try (ByteArrayInputStream in = new ByteArrayInputStream(hostKey)) {
			hostKeys.add(SecurityUtils.loadKeyPairIdentity(""
		} catch (IOException | GeneralSecurityException e) {
		}
		server.setKeyPairProvider(() -> hostKeys);

		configureAuthentication();

		List<NamedFactory<Command>> subsystems = configureSubsystems();
		if (!subsystems.isEmpty()) {
			server.setSubsystemFactories(subsystems);
		}

		configureShell();

		server.setCommandFactory(command -> {
			if (command.startsWith(RemoteConfig.DEFAULT_UPLOAD_PACK)) {
				return new GitUploadPackCommand(command
			} else if (command.startsWith(RemoteConfig.DEFAULT_RECEIVE_PACK)) {
				return new GitReceivePackCommand(command
			}
			return new UnknownCommand(command);
		});
	}

	private static class FakeUserAuthGSS extends UserAuthGSS {
		@Override
		protected Boolean doAuth(Buffer buffer
				throws Exception {
			if (initial) {
				ServerSession session = getServerSession();
				Buffer b = session.createBuffer(
						SshConstants.SSH_MSG_USERAUTH_INFO_REQUEST);
				b.putBytes(KRB5_MECH.getDER());
				session.writePacket(b);
				return null;
			}
			return Boolean.FALSE;
		}
	}

	private List<NamedFactory<UserAuth>> getAuthFactories() {
		List<NamedFactory<UserAuth>> authentications = new ArrayList<>();
		authentications.add(new UserAuthGSSFactory() {
			@Override
			public UserAuth create() {
				return new FakeUserAuthGSS();
			}
		});
		authentications.add(
				ServerAuthenticationManager.DEFAULT_USER_AUTH_PUBLIC_KEY_FACTORY);
		authentications.add(
				ServerAuthenticationManager.DEFAULT_USER_AUTH_KB_INTERACTIVE_FACTORY);
		authentications.add(
				ServerAuthenticationManager.DEFAULT_USER_AUTH_PASSWORD_FACTORY);
		return authentications;
	}

	protected void configureAuthentication() {
		server.setUserAuthFactories(getAuthFactories());
		server.setPasswordAuthenticator(null);
		server.setKeyboardInteractiveAuthenticator(null);
		server.setHostBasedAuthenticator(null);
		server.setGSSAuthenticator(new GSSAuthenticator() {
			@Override
			public boolean validateInitialUser(ServerSession session
					String user) {
				return false;
			}
		});
		server.setPublickeyAuthenticator((userName
			return SshTestGitServer.this.testUser.equals(userName) && KeyUtils
					.compareKeys(SshTestGitServer.this.testKey
		});
	}

	@NonNull
	protected List<NamedFactory<Command>> configureSubsystems() {
		server.setFileSystemFactory(new VirtualFileSystemFactory() {

			@Override
			protected Path computeRootDir(Session session) throws IOException {
				return SshTestGitServer.this.repository.getDirectory()
						.getParentFile().getAbsoluteFile().toPath();
			}
		});
		return Collections
				.singletonList((new SftpSubsystemFactory.Builder()).build());
	}

	protected void configureShell() {
		server.setShellFactory(null);
	}

	public void addHostKey(@NonNull Path key
			throws IOException
		try (InputStream in = Files.newInputStream(key)) {
			KeyPair pair = SecurityUtils.loadKeyPairIdentity(key.toString()
					null);
			if (inFront) {
				hostKeys.add(0
			} else {
				hostKeys.add(pair);
			}
		}
	}

	public void enablePasswordAuthentication() {
		server.setPasswordAuthenticator((user
			return testUser.equals(user)
					&& testUser.toUpperCase(Locale.ROOT).equals(pwd);
		});
	}

	public void enableKeyboardInteractiveAuthentication() {
		server.setPasswordAuthenticator((user
			return testUser.equals(user)
					&& testUser.toUpperCase(Locale.ROOT).equals(pwd);
		});
		server.setKeyboardInteractiveAuthenticator(
				DefaultKeyboardInteractiveAuthenticator.INSTANCE);
	}

	public int start() throws IOException {
		server.start();
		return server.getPort();
	}

	public void stop() throws IOException {
		executorService.shutdownNow();
		server.stop(true);
	}

	public void setTestUserPublicKey(Path key)
			throws IOException
		this.testKey = AuthorizedKeyEntry.readAuthorizedKeys(key).get(0)
				.resolvePublicKey(PublicKeyEntryResolver.IGNORING);
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

	private class GitReceivePackCommand extends AbstractCommandSupport {

		protected GitReceivePackCommand(String command
				ExecutorService executorService) {
			super(command
		}

		@Override
		public void run() {
			try {
				new ReceivePack(repository).receive(getInputStream()
						getOutputStream()
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
