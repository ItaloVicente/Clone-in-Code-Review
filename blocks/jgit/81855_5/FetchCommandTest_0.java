package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.submodule.SubmoduleStatus;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.junit.Before;
import org.junit.Test;

public class FetchCommandRecurseSubmodulesTest extends RepositoryTestCase {
	private Git git;

	private Git remoteGit;
	private Repository submodule;

	@Before
	public void setupSubmoduleRepository() throws Exception {
		git = new Git(db);

		Repository remoteRepository = createWorkRepository();
		remoteGit = new Git(remoteRepository);

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(
				remoteRepository.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();

		String path = "modules/submodule";
		submodule = remoteGit.submoduleAdd().setPath(path).setURI(
				createWorkRepository().getDirectory().toURI().toString())
				.call();
		assertNotNull(submodule);
		RevCommit commit = remoteGit.commit().setMessage("add submodule")
				.call();

		Map<String
		assertTrue(submoduleStatus.isEmpty());
		RefSpec spec = new RefSpec("refs/heads/master");
		git.fetch().setRemote("test").setRefSpecs(spec).call();
		git.reset().setRef(Constants.FETCH_HEAD).call();
		assertEquals(commit.getId()
		submoduleStatus = git.submoduleStatus().call();
		assertTrue(submoduleStatus.containsKey(path));
	}

	@Test
	public void defaultShouldNotFetchSubmodule() throws Exception {
	}
}
