
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.PubSubConfig;
import org.eclipse.jgit.transport.PubSubConfig.Publisher;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

import org.kohsuke.args4j.Argument;

@Command(common = false
class Unsubscribe extends TextBuiltin {
	@Argument(index = 0
	private String remote = Constants.DEFAULT_REMOTE_NAME;

	@Override
	protected void run() throws Exception {
		StoredConfig dbconfig = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(dbconfig
		PubSubConfig pubsubConfig = SubscribeDaemon.getConfig();
		List<URIish> uris = remoteConfig.getURIs();
		if (uris.isEmpty()) {
			out.println(MessageFormat.format(
					CLIText.get().noRemoteUriUnsubscribe
			return;
		}
		String uriRoot = PubSubConfig.getUriRoot(uris.get(0));
		String dir = db.getDirectory().getAbsolutePath();

		Publisher p = pubsubConfig.getPublisher(uriRoot);
		if (p == null || !p.removeSubscriber(remote
			out.println(MessageFormat.format(
					CLIText.get().subscriptionDoesNotExist
			return;
		}

		if (p.getSubscribers().size() == 0)
			pubsubConfig.removePublisher(p);

		try {
			SubscribeDaemon.updateConfig(pubsubConfig);
		} catch (IOException e) {
			out.println(MessageFormat.format(CLIText.get().cannotWrite
					SubscribeDaemon.getConfigFile()));
			return;
		}

		out.println(MessageFormat.format(
				CLIText.get().didUnsubscribe
	}
}
