package org.eclipse.jgit.niofs.internal.daemon.ssh;

import static org.apache.sshd.common.NamedFactory.setUpBuiltinFactories;
import static org.apache.sshd.server.ServerBuilder.builder;
import static org.eclipse.jgit.niofs.internal.daemon.common.PortUtil.validateOrGetNew;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.sshd.common.cipher.BuiltinCiphers;
import org.apache.sshd.common.mac.BuiltinMacs;
import org.apache.sshd.common.util.security.SecurityUtils;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.pubkey.CachingPublicKeyAuthenticator;
import org.apache.sshd.server.keyprovider.AbstractGeneratorHostKeyProvider;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.UnknownCommand;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
import org.eclipse.jgit.niofs.internal.security.AuthenticationService;
import org.eclipse.jgit.niofs.internal.security.PublicKeyAuthenticator;
import org.eclipse.jgit.niofs.internal.security.User;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitSSHService {

	private static final Logger LOG = LoggerFactory.getLogger(GitSSHService.class);

	private final List<BuiltinCiphers> managedCiphers = Collections.unmodifiableList(Arrays.asList(
			BuiltinCiphers.aes128ctr
			BuiltinCiphers.arcfour128

	private final List<BuiltinMacs> managedMACs = Collections
			.unmodifiableList(Arrays.asList(BuiltinMacs.hmacmd5
					BuiltinMacs.hmacsha512

	private SshServer sshd;
	private AuthenticationService authenticationService;
	private PublicKeyAuthenticator publicKeyAuthenticator;

	private SshServer buildSshServer(String ciphersConfigured

		return builder().cipherFactories(setUpBuiltinFactories(false
				.macFactories(setUpBuiltinFactories(false
	}

	public void setup(final File certDir
			final String algorithm
			final UploadPackFactory uploadPackFactory
			final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver
			final ExecutorService executorService) {
		setup(certDir
				repositoryResolver
	}

	public void setup(final File certDir
			final String algorithm
			final UploadPackFactory uploadPackFactory
			final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver
			final ExecutorService executorService
		checkNotNull("certDir"
		checkNotEmpty("sshIdleTimeout"
		checkNotEmpty("algorithm"
		checkNotNull("receivePackFactory"
		checkNotNull("uploadPackFactory"
		checkNotNull("repositoryResolver"

		buildSSHServer(gitSshCiphers

		sshd.getProperties().put(SshServer.IDLE_TIMEOUT

		if (inetSocketAddress != null) {
			sshd.setHost(inetSocketAddress.getHostName());
			sshd.setPort(validateOrGetNew(inetSocketAddress.getPort()));

			if (inetSocketAddress.getPort() != sshd.getPort()) {
				LOG.error("SSH for Git original port {} not available
						inetSocketAddress.getPort()
			}
		}

		if (!certDir.exists()) {
			certDir.mkdirs();
		}

		final AbstractGeneratorHostKeyProvider keyPairProvider = new SimpleGeneratorHostKeyProvider(
				new File(certDir

		try {
			SecurityUtils.getKeyPairGenerator(algorithm);
			keyPairProvider.setAlgorithm(algorithm);
		} catch (final Exception ignore) {
			throw new RuntimeException(String.format("Can't use '%s' algorithm for ssh key pair generator."
					ignore);
		}

		sshd.setKeyPairProvider(keyPairProvider);
		sshd.setCommandFactory(command -> {
			if (command.startsWith("git-upload-pack")) {
				return new GitUploadCommand(command
			} else if (command.startsWith("git-receive-pack")) {
				return new GitReceiveCommand(command
			} else {
				return new UnknownCommand(command);
			}
		});
		sshd.setPublickeyAuthenticator(new CachingPublicKeyAuthenticator((username

			final User user = getPublicKeyAuthenticator().authenticate(username

			if (user == null) {
				return false;
			}

			session.setAttribute(BaseGitCommand.SUBJECT_KEY

			return true;
		}));
		sshd.setPasswordAuthenticator((username

			final User user = getUserPassAuthenticator().login(username

			if (user == null) {
				return false;
			}

			session.setAttribute(BaseGitCommand.SUBJECT_KEY
			return true;
		});
	}

	private void buildSSHServer(String gitSshCiphers

		sshd = buildSshServer(gitSshCiphers
	}

	private List<BuiltinCiphers> checkAndSetGitCiphers(String gitSshCiphers) {
		if (gitSshCiphers == null || gitSshCiphers.isEmpty()) {
			return managedCiphers;
		} else {
			List<BuiltinCiphers> ciphersHandled = new ArrayList<>();
			List<String> ciphers = Arrays.asList(gitSshCiphers.split("
				} else {
					LOG.warn("Cipher {} not handled in git ssh configuration. "
				}
			}
			return ciphersHandled;
		}
	}

	private List<BuiltinMacs> checkAndSetGitMacs(String gitSshMacs) {

		if (gitSshMacs == null || gitSshMacs.isEmpty()) {
			return managedMACs;
		} else {

			List<BuiltinMacs> macs = new ArrayList<>();
			List<String> macsInput = Arrays.asList(gitSshMacs.split("
				} else {
					LOG.warn("MAC {} not handled in git ssh configuration. "
				}
			}
			return macs;
		}
	}

	public void stop() {
		try {
			sshd.stop(true);
		} catch (IOException ignored) {
		}
	}

	public void start() {
		try {
			sshd.start();
		} catch (IOException e) {
			throw new RuntimeException("Couldn't start SSH daemon at " + sshd.getHost() + ":" + sshd.getPort()
		}
	}

	public boolean isRunning() {
		return !(sshd.isClosed() || sshd.isClosing());
	}

	SshServer getSshServer() {
		return sshd;
	}

	public Map<String
		return Collections.unmodifiableMap(sshd.getProperties());
	}

	public AuthenticationService getUserPassAuthenticator() {
		return authenticationService;
	}

	public void setUserPassAuthenticator(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public PublicKeyAuthenticator getPublicKeyAuthenticator() {
		return publicKeyAuthenticator;
	}

	public void setPublicKeyAuthenticator(PublicKeyAuthenticator publicKeyAuthenticator) {
		this.publicKeyAuthenticator = publicKeyAuthenticator;
	}

	public List<BuiltinCiphers> getManagedCiphers() {
		return managedCiphers;
	}

	public List<BuiltinMacs> getManagedMACs() {
		return managedMACs;
	}
}
