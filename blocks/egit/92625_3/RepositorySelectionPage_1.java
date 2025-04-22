package org.eclipse.egit.ui.internal.clone;

import java.net.URISyntaxException;


import org.eclipse.egit.ui.internal.KnownHosts;
import org.eclipse.egit.ui.internal.components.RepositorySelectionPage.Protocol;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.TransportProtocol;
import org.eclipse.jgit.transport.URIish;

public abstract class GitUrlChecker {

	private static final String GIT_CLONE_COMMAND_PREFIX = "git clone "; //$NON-NLS-1$

	public static boolean isValidGitUrl(String url) {
		try {
			if (url != null) {
				url = stripGitCloneCommand(url);
				url = url.split(
						"[ \\f\\n\\r\\x0B\\t\\xA0\\u1680\\u180e\\u2000-\\u200a\\u202f\\u205f\\u3000]", //$NON-NLS-1$
						2)[0];
				URIish u = new URIish(url);
				if (canHandleProtocol(u)) {
					if (Protocol.GIT.handles(u) || Protocol.SSH.handles(u)
							|| (Protocol.HTTP.handles(u)
									|| Protocol.HTTPS.handles(u))
									&& KnownHosts.isKnownHost(u.getHost())
							|| url.endsWith(Constants.DOT_GIT_EXT)) {
						return true;
					}
				}
			}
		} catch (URISyntaxException e) {

		}
		return false;
	}

	private static String stripGitCloneCommand(String input) {
		input = input.trim();
		if (input.startsWith(GIT_CLONE_COMMAND_PREFIX)) {
			return input.substring(GIT_CLONE_COMMAND_PREFIX.length()).trim();
		}
		return input;
	}

	private static boolean canHandleProtocol(URIish u) {
		for (TransportProtocol proto : Transport.getTransportProtocols())
			if (proto.canHandle(u))
				return true;
		return false;
	}

}
