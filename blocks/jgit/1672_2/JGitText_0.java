package org.eclipse.jgit.api;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class PushCommandTest extends RepositoryTestCase {

	public void testPush() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();

		final Config config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);

		Git git1 = new Git(db);
		RevCommit commit = git1.commit().setMessage("initial commit").call();
		RevTag tag = git1.tag().setName("tag").call();

		try {
			db2.resolve(commit.getId().getName() + "^{commit}");
			fail("id shouldn't exist yet");
		} catch (MissingObjectException e) {
		}

		RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
		git1.push().setRemote("test").setRefSpecs(spec)
				.call();

		assertEquals(commit.getId()
				db2.resolve(commit.getId().getName() + "^{commit}"));
		assertEquals(tag.getId()
	}

}
