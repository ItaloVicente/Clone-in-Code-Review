package org.eclipse.jgit.internal.transport.sshd;

import static java.text.MessageFormat.format;
import static org.eclipse.jgit.transport.SshConstants.PUBKEY_ACCEPTED_ALGORITHMS;

import java.util.List;

import org.apache.sshd.client.auth.pubkey.UserAuthPublicKey;
import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.signature.Signature;
import org.eclipse.jgit.util.StringUtils;

public class JGitPublicKeyAuthentication extends UserAuthPublicKey {

	JGitPublicKeyAuthentication(List<NamedFactory<Signature>> factories) {
		super(factories);
	}

	@Override
	public void init(ClientSession rawSession
			throws Exception {
		if (!(rawSession instanceof JGitClientSession)) {
					+ rawSession.getClass().getCanonicalName());
		}
		JGitClientSession session = ((JGitClientSession) rawSession);
		HostConfigEntry hostConfig = session.getHostConfigEntry();
		String pubkeyAlgos = hostConfig.getProperty(PUBKEY_ACCEPTED_ALGORITHMS);
		if (!StringUtils.isEmptyOrNull(pubkeyAlgos)) {
			List<String> signatures = session.getSignatureFactoriesNames();
			signatures = session.modifyAlgorithmList(signatures
					session.getAllAvailableSignatureAlgorithms()
					PUBKEY_ACCEPTED_ALGORITHMS);
			if (!signatures.isEmpty()) {
				if (log.isDebugEnabled()) {
					log.debug(PUBKEY_ACCEPTED_ALGORITHMS + ' ' + signatures);
				}
				setSignatureFactoriesNames(signatures);
			} else {
				log.warn(format(SshdText.get().configNoKnownAlgorithms
						PUBKEY_ACCEPTED_ALGORITHMS
			}
		}
		super.init(session
	}
}
