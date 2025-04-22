package org.eclipse.jgit.internal.transport.sshd;

import java.io.IOException;
import java.nio.channels.Channel;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.sshd.agent.SshAgent;
import org.apache.sshd.agent.SshAgentFactory;
import org.apache.sshd.client.auth.pubkey.AbstractKeyPairIterator;
import org.apache.sshd.client.auth.pubkey.KeyAgentIdentity;
import org.apache.sshd.client.auth.pubkey.KeyPairIdentity;
import org.apache.sshd.client.auth.pubkey.PublicKeyIdentity;
import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.FactoryManager;
import org.apache.sshd.common.keyprovider.KeyIdentityProvider;
import org.apache.sshd.common.signature.SignatureFactoriesManager;

public class JGitPublicKeyIterator
		extends AbstractKeyPairIterator<PublicKeyIdentity> implements Channel {


	private final AtomicBoolean open = new AtomicBoolean(true);

	private SshAgent agent;

	private final List<Iterator<PublicKeyIdentity>> keys = new ArrayList<>(3);

	private final Iterator<Iterator<PublicKeyIdentity>> keyIter;

	private Iterator<PublicKeyIdentity> current;

	private Boolean hasElement;

	public JGitPublicKeyIterator(ClientSession session
			SignatureFactoriesManager signatureFactories) throws Exception {
		super(session);
		boolean useAgent = true;
		if (session instanceof JGitClientSession) {
			HostConfigEntry config = ((JGitClientSession) session)
					.getHostConfigEntry();
			useAgent = !config.isIdentitiesOnly();
		}
		if (useAgent) {
			FactoryManager manager = session.getFactoryManager();
			SshAgentFactory factory = manager == null ? null
					: manager.getAgentFactory();
			if (factory != null) {
				try {
					agent = factory.createClient(manager);
					keys.add(new AgentIdentityIterator(agent));
				} catch (IOException e) {
					try {
						closeAgent();
					} catch (IOException err) {
						e.addSuppressed(err);
					}
					throw e;
				}
			}
		}
		keys.add(
				new KeyPairIdentityIterator(session.getRegisteredIdentities()
						session
		keys.add(new KeyPairIdentityIterator(session.getKeyPairProvider()
				session
		keyIter = keys.iterator();
	}

	@Override
	public boolean isOpen() {
		return open.get();
	}

	@Override
	public void close() throws IOException {
		if (open.getAndSet(false)) {
			closeAgent();
		}
	}

	@Override
	public boolean hasNext() {
		if (!isOpen()) {
			return false;
		}
		if (hasElement != null) {
			return hasElement.booleanValue();
		}
		while (current == null || !current.hasNext()) {
			if (keyIter.hasNext()) {
				current = keyIter.next();
			} else {
				current = null;
				hasElement = Boolean.FALSE;
				return false;
			}
		}
		hasElement = Boolean.TRUE;
		return true;
	}

	@Override
	public PublicKeyIdentity next() {
		if (!isOpen() || hasElement == null && !hasNext()
				|| !hasElement.booleanValue()) {
			throw new NoSuchElementException();
		}
		hasElement = null;
		PublicKeyIdentity result;
		try {
			result = current.next();
		} catch (NoSuchElementException e) {
			result = null;
		}
		return result;
	}

	private void closeAgent() throws IOException {
		if (agent == null) {
			return;
		}
		try {
			agent.close();
		} finally {
			agent = null;
		}
	}

	private static class AgentIdentityIterator
			implements Iterator<PublicKeyIdentity> {

		private final SshAgent agent;

		private final Iterator<? extends Map.Entry<PublicKey

		public AgentIdentityIterator(SshAgent agent) throws IOException {
			this.agent = agent;
			iter = agent == null ? null : agent.getIdentities().iterator();
		}

		@Override
		public boolean hasNext() {
			return iter != null && iter.hasNext();
		}

		@Override
		public PublicKeyIdentity next() {
			if (iter == null) {
				throw new NoSuchElementException();
			}
			Map.Entry<PublicKey
			return new KeyAgentIdentity(agent
					entry.getValue());
		}
	}

	private static class KeyPairIdentityIterator
			implements Iterator<PublicKeyIdentity> {

		private final Iterator<KeyPair> keyPairs;

		private final ClientSession session;

		private final SignatureFactoriesManager signatureFactories;

		public KeyPairIdentityIterator(KeyIdentityProvider provider
				ClientSession session
				SignatureFactoriesManager signatureFactories) {
			this.session = session;
			this.signatureFactories = signatureFactories;
			keyPairs = provider == null ? null : provider.loadKeys().iterator();
		}

		@Override
		public boolean hasNext() {
			return keyPairs != null && keyPairs.hasNext();
		}

		@Override
		public PublicKeyIdentity next() {
			if (keyPairs == null) {
				throw new NoSuchElementException();
			}
			KeyPair key = keyPairs.next();
			return new KeyPairIdentity(signatureFactories
		}
	}
}
