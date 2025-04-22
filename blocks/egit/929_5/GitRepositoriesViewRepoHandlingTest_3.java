package org.eclipse.egit.ui.view.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.repository.tree.ErrorNode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryConfig;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class GitRepositoriesViewRemoteHandlingTest extends
		GitRepositoriesViewTestBase {

	private static File repositoryFile;

	private static File remoteRepositoryFile;

	@BeforeClass
	public static void beforeClass() throws Exception {
		repositoryFile = createProjectAndCommitToRepository();
		remoteRepositoryFile = createRemoteRepository(repositoryFile);
		Activator.getDefault().getRepositoryUtil().addConfiguredRepository(
				repositoryFile);
	}

	@Test
	@Ignore
	public void testExpandRemotes() throws Exception {
		removeRemotesConfig(repositoryFile);
		refreshAndWait();
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem remotesItem = getRemotesItem(tree, repositoryFile)
				.expand();
		assertEquals("Wrong number of remotes", 0, remotesItem.getNodes()
				.size());
		RepositoryConfig cfg = lookupRepository(repositoryFile).getConfig();
		String remoteUri = "file:///" + remoteRepositoryFile.getPath();

		cfg.setString("remote", "test", "url", remoteUri);
		cfg.setString("remote", "test", "fetch", "somejunk");
		cfg.setString("remote", "test2", "url", remoteUri);
		cfg.setString("remote", "test2", "fetch", "somejunk");
		cfg.setString("remote", "test2", "pushurl", remoteUri);
		cfg.setString("remote", "test2", "push", "somejunk");
		cfg.setString("remote", "test3", "pushurl", "somejunk");
		cfg.setString("remote", "test3", "push", "somejunk");
		cfg.save();
		cfg.load();
		refreshAndWait();
		remotesItem = getRemotesItem(tree, repositoryFile).expand();
		assertEquals("Wrong number of remotes", 3, remotesItem.getNodes()
				.size());

		remotesItem = getRemotesItem(tree, repositoryFile).expand();
		List<String> testnodes = remotesItem.getNode("test").expand()
				.getNodes();
		assertTrue(testnodes.size() == 1);
		List<String> test2nodes = remotesItem.getNode("test2").expand()
				.getNodes();
		assertTrue(test2nodes.size() == 2);
		remotesItem.getNode("test3").expand().getNodes();
		assertTrue(remotesItem.getNode("test3").expand().getNodes().size() == 1);
		final SWTBotTreeItem errorItem = remotesItem.getNode("test3")
				.getNode(0);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Object data = errorItem.widget.getData();
				assertTrue(data instanceof ErrorNode);
			}
		});

		removeRemotesConfig(repositoryFile);
		refreshAndWait();
		remotesItem = getRemotesItem(tree, repositoryFile).expand();
		assertEquals("Wrong number of remotes", 0, remotesItem.getNodes()
				.size());
	}

	@Test
	@Ignore // TODO does not compile currently
	public void testConfigureRemote() throws Exception {
	}

	private void removeRemotesConfig(File file) throws Exception {
		Repository repo = lookupRepository(file);
		RepositoryConfig config = repo.getConfig();
		for (String remote : config.getSubsections("remote"))
			config.unsetSection("remote", remote);
		config.save();
		waitInUI();
	}
}
