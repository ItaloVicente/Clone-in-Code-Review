package org.eclipse.jgit.api;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class FetchCommandTest extends RepositoryTestCase {

	public void testFetch() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();
		Git git2 = new Git(db2);

		final Config config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);

		RevCommit commit = git2.commit().setMessage("initial commit").call();
		RevTag tag = git2.tag().setName("tag").call();

		Git git1 = new Git(db);

		RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
		git1.fetch().setRemote("test").setRefSpecs(spec)
				.call();

		assertEquals(commit.getId()
				db.resolve(commit.getId().getName() + "^{commit}"));
		assertEquals(tag.getId()

	}

}
