package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class AbstractRemoteCommandTest extends RepositoryTestCase {

	protected static final String REMOTE_NAME = "test";

	protected RemoteConfig setupRemote()
			throws IOException
		Repository remoteRepository = createWorkRepository();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config

		RefSpec refSpec = new RefSpec();
		refSpec = refSpec.setForceUpdate(true);
		refSpec = refSpec.setSourceDestination(Constants.R_HEADS + "*"
		remoteConfig.addFetchRefSpec(refSpec);

		URIish uri = new URIish(
				remoteRepository.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);

		remoteConfig.update(config);
		config.save();

		return remoteConfig;
	}

	protected void assertRemoteConfigEquals(RemoteConfig expected
			RemoteConfig actual) {
		assertEquals(expected.getName()
		assertEquals(expected.getURIs()
		assertEquals(expected.getPushURIs()
		assertEquals(expected.getFetchRefSpecs()
		assertEquals(expected.getPushRefSpecs()
	}

}
