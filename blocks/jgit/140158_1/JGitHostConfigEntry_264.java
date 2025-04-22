package org.eclipse.jgit.internal.transport.sshd;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.net.SocketAddress;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.sshd.client.ClientFactoryManager;
import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.keyverifier.KnownHostsServerKeyVerifier.HostEntryPair;
import org.apache.sshd.client.keyverifier.ServerKeyVerifier;
import org.apache.sshd.client.session.ClientSessionImpl;
import org.apache.sshd.common.FactoryManager;
import org.apache.sshd.common.SshException;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.io.IoSession;
import org.apache.sshd.common.io.IoWriteFuture;
import org.apache.sshd.common.util.Readable;
import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.fnmatch.FileNameMatcher;
import org.eclipse.jgit.internal.transport.sshd.proxy.StatefulProxyConnector;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.SshConstants;

public class JGitClientSession extends ClientSessionImpl {

	private HostConfigEntry hostConfig;

	private CredentialsProvider credentialsProvider;

	private StatefulProxyConnector proxyHandler;

	public JGitClientSession(ClientFactoryManager manager
			throws Exception {
		super(manager
	}

	public HostConfigEntry getHostConfigEntry() {
		return hostConfig;
	}

	public void setHostConfigEntry(HostConfigEntry hostConfig) {
		this.hostConfig = hostConfig;
	}

	public void setCredentialsProvider(CredentialsProvider provider) {
		credentialsProvider = provider;
	}

	public CredentialsProvider getCredentialsProvider() {
		return credentialsProvider;
	}

	public void setProxyHandler(StatefulProxyConnector handler) {
		proxyHandler = handler;
	}

	@Override
	protected IoWriteFuture sendIdentification(String ident)
			throws IOException {
		return null;
	}

	@Override
	protected byte[] sendKexInit() throws IOException {
		StatefulProxyConnector proxy = proxyHandler;
		if (proxy != null) {
			try {
				proxy.runWhenDone(() -> {
					sendStartSsh();
					return null;
				});
				return null;
			} catch (IOException e) {
				throw e;
			} catch (Exception other) {
				throw new IOException(other.getLocalizedMessage()
			}
		} else {
			return sendStartSsh();
		}
	}

	private byte[] sendStartSsh() throws IOException {
		super.sendIdentification(clientVersion);
		return super.sendKexInit();
	}

	@Override
	public void messageReceived(Readable buffer) throws Exception {
		StatefulProxyConnector proxy = proxyHandler;
		if (proxy != null) {
			proxy.messageReceived(getIoSession()
		} else {
			super.messageReceived(buffer);
		}
	}

	@Override
	protected void checkKeys() throws SshException {
		ServerKeyVerifier serverKeyVerifier = getServerKeyVerifier();
		SocketAddress remoteAddress = getConnectAddress();
		PublicKey serverKey = getKex().getServerKey();
		if (!serverKeyVerifier.verifyServerKey(this
				serverKey)) {
			throw new SshException(
					org.apache.sshd.common.SshConstants.SSH2_DISCONNECT_HOST_KEY_NOT_VERIFIABLE
					SshdText.get().kexServerKeyInvalid);
		}
	}

	@Override
	protected String resolveAvailableSignaturesProposal(
			FactoryManager manager) {
		Set<String> defaultSignatures = new LinkedHashSet<>();
		defaultSignatures.addAll(getSignatureFactoriesNames());
		HostConfigEntry config = resolveAttribute(
				JGitSshClient.HOST_CONFIG_ENTRY);
		String hostKeyAlgorithms = config
				.getProperty(SshConstants.HOST_KEY_ALGORITHMS);
		if (hostKeyAlgorithms != null && !hostKeyAlgorithms.isEmpty()) {
			char first = hostKeyAlgorithms.charAt(0);
			if (first == '+') {
				return String.join("
			if (toRemove.indexOf('*') < 0 && toRemove.indexOf('?') < 0) {
				current.remove(toRemove);
				continue;
			}
			try {
				FileNameMatcher matcher = new FileNameMatcher(toRemove
				for (Iterator<String> i = current.iterator(); i.hasNext();) {
					matcher.reset();
					matcher.append(i.next());
					if (matcher.isMatch()) {
						i.remove();
					}
				}
			} catch (InvalidPatternException e) {
				log.warn(format(SshdText.get().configInvalidPattern
						toRemove));
			}
		}
	}

	private List<String> filteredList(Set<String> known
		List<String> newNames = new ArrayList<>();
		for (String newValue : values.split("\\s*
			if (known.contains(newValue)) {
				newNames.add(newValue);
			}
		}
		return newNames;
	}

}
