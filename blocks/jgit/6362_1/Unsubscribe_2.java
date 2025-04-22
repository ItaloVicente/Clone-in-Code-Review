
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.NoWorkTreeException;
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
		URIish uriish = remoteConfig.getURIs().get(0);
		String uriRoot = PubSubConfig.getUriRoot(uriish);
		String dir = db.getDirectory().getAbsolutePath();

		if (uriish != null) {
			Publisher p = pubsubConfig.getPublisher(uriRoot);
			if (p == null || !p.removeSubscriber(remote
				out.println(MessageFormat.format(
						CLIText.get().doesNotExist
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
		}
	}
}
