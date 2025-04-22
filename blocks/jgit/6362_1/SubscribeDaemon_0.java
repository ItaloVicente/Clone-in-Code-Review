
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.errors.NoWorkTreeException;
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
		URIish uriish = remoteConfig.getURIs().get(0);
		String uriRoot = PubSubConfig.getUriRoot(uriish);
		String dir = db.getDirectory().getAbsolutePath();

		Publisher p = pubsubConfig.getPublisher(uriRoot);
		if (p == null) {
			p = new Publisher(uriRoot);
			pubsubConfig.addPublisher(p);
		}

		Subscriber s = p.getSubscriber(remote

		if (s == null)
			p.addSubscriber(new Subscriber(p

		try {
			SubscribeDaemon.updateConfig(pubsubConfig);
		} catch (IOException e) {
			out.println(MessageFormat.format(CLIText.get().cannotWrite
					SubscribeDaemon.getConfigFile()));
			return;
		}
	}
}
