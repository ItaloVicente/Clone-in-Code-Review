
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.PubSubConfig;
import org.eclipse.jgit.transport.PubSubConfig.Publisher;
import org.eclipse.jgit.transport.PubSubConfig.Subscriber;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

import org.kohsuke.args4j.Argument;

@Command(common = false
class Subscribe extends TextBuiltin {

	@Argument(index = 0
	private String remote = Constants.DEFAULT_REMOTE_NAME;

	@Override
	protected void run() throws Exception {
		StoredConfig dbconfig = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(dbconfig
		PubSubConfig pubsubConfig = SubscribeDaemon.getConfig();
		List<URIish> uris = remoteConfig.getURIs();
		if (uris.isEmpty())
			throw die(MessageFormat.format(
					CLIText.get().noRemoteUriSubscribe
		URIish uri = uris.get(0);
		String uriRoot = PubSubConfig.getUriRoot(uri);
		String dir = db.getDirectory().getAbsolutePath();

		Publisher p = pubsubConfig.getPublisher(uriRoot);
		if (p == null) {
			p = new Publisher(uriRoot);
			pubsubConfig.addPublisher(p);
		}

		Subscriber s = p.getSubscriber(remote

		if (s == null) {
			Subscriber newSubscriber = new Subscriber(p
			for (Subscriber oldSubscriber : p.getSubscribers()) {
				if (newSubscriber.getName() == oldSubscriber.getName())
					throw die(MessageFormat.format(
							CLIText.get().cannotSubscribeTwice
							oldSubscriber.getDirectory()));
			}
			p.addSubscriber(newSubscriber);
		}

		try {
			SubscribeDaemon.updateConfig(pubsubConfig);
		} catch (IOException e) {
			throw die(MessageFormat.format(CLIText.get().cannotWrite
					SubscribeDaemon.getConfigFile()));
		}

		out.println(MessageFormat.format(
				CLIText.get().didSubscribe
	}
}
