
package org.eclipse.jgit.pgm;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.transport.PubSubConfig;
import org.eclipse.jgit.util.FS;

@Command(common = false
class SubscribeDaemon extends TextBuiltin {
	public static String GLOBAL_PUBSUB_FILE = ".gitpubsub";

	public static File getConfigFile() {
		return new File(FS.detect().userHome()
	}

	public static PubSubConfig getConfig()
			throws IOException
		File cfg = getConfigFile();
		FileBasedConfig c = new FileBasedConfig(cfg
		c.load();
		return new PubSubConfig(c);
	}

	public static void updateConfig(PubSubConfig pubsubConfig)
			throws IOException {
		File cfg = getConfigFile();
		FileBasedConfig c = new FileBasedConfig(cfg
		pubsubConfig.update(c);
		c.save();
	}

	@Override
	protected void run() throws Exception {
	}
}
