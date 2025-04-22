package org.eclipse.jgit.internal.transport.sshd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.sshd.common.AttributeRepository.AttributeKey;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.kex.KexProposalOption;
import org.apache.sshd.common.kex.extension.KexExtensionHandler;
import org.apache.sshd.common.kex.extension.KexExtensions;
import org.apache.sshd.common.kex.extension.parser.ServerSignatureAlgorithms;
import org.apache.sshd.common.session.Session;
import org.apache.sshd.common.signature.Signature;
import org.apache.sshd.common.util.logging.AbstractLoggingBean;
import org.eclipse.jgit.util.StringUtils;

public class JGitKexExtensionHandler extends AbstractLoggingBean
		implements KexExtensionHandler {

	public static final JGitKexExtensionHandler INSTANCE = new JGitKexExtensionHandler();

	private static final AttributeKey<Boolean> CLIENT_PROPOSAL_MADE = new AttributeKey<>();

	public static final AttributeKey<Set<String>> SERVER_ALGORITHMS = new AttributeKey<>();

	private JGitKexExtensionHandler() {
	}

	@Override
	public boolean isKexExtensionsAvailable(Session session
			AvailabilityPhase phase) throws IOException {
		return !AvailabilityPhase.PREKEX.equals(phase);
	}

	@Override
	public void handleKexInitProposal(Session session
			Map<KexProposalOption
		if (session == null || session.isServerSession() || !initiator) {
			return;
		}
		if (session.getAttribute(CLIENT_PROPOSAL_MADE) != null) {
			return;
		}
		String kexAlgorithms = proposal.get(KexProposalOption.SERVERKEYS);
		if (StringUtils.isEmptyOrNull(kexAlgorithms)) {
			return;
		}
		List<String> algorithms = new ArrayList<>();
		for (String algo : kexAlgorithms.split("
					session
		}
		proposal.put(KexProposalOption.SERVERKEYS
				String.join("
					serverAlgorithms);
		}
		if (serverAlgorithms != null && !serverAlgorithms.isEmpty()) {
			List<NamedFactory<Signature>> clientAlgorithms = new ArrayList<>(
					session.getSignatureFactories());
			if (log.isDebugEnabled()) {
				log.debug(
						"handleServerSignatureAlgorithms({}): PubkeyAcceptedAlgorithms before: {}"
						session
			}
			List<NamedFactory<Signature>> unknown = new ArrayList<>();
			Set<String> known = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
			known.addAll(serverAlgorithms);
			for (Iterator<NamedFactory<Signature>> iter = clientAlgorithms
					.iterator(); iter.hasNext();) {
				NamedFactory<Signature> algo = iter.next();
				if (!known.contains(algo.getName())) {
					unknown.add(algo);
					iter.remove();
				}
			}
			clientAlgorithms.addAll(unknown);
			if (log.isDebugEnabled()) {
				log.debug(
						"handleServerSignatureAlgorithms({}): PubkeyAcceptedAlgorithms after: {}"
						session
			}
			session.setAttribute(SERVER_ALGORITHMS
			session.setSignatureFactories(clientAlgorithms);
		}
	}
}
