package org.eclipse.jgit.gitrepo;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.junit.Test;

public class RepoCommandTest extends RepositoryTestCase {

	private Git remoteGit;

	public void setUp() throws Exception {
		super.setUp();

		Repository remoteDb = createWorkRepository();
		remoteGit = new Git(remoteDb);
		JGitTestUtil.writeTrashFile(remoteDb
		remoteGit.add().addFilepattern("hello.txt").call();
		remoteGit.commit().setMessage("Initial commit").call();
	}

	@Test
	public void testAddRepoManifest() throws Exception {
		StringBuilder xmlContent = new StringBuilder();
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
			.append("<manifest>")
			.append("<remote name=\"remote1\" fetch=\".\" />")
			.append("<default revision=\"master\" remote=\"remote1\" />")
			.append("<project path=\"foo\" name=\".\" />")
			.append("</manifest>");
		writeTrashFile("manifest.xml"
		RepoCommand command = new RepoCommand(db);
		command.setPath(db.getWorkTree() + "/manifest.xml")
			.setURI(remoteGit.getRepository().getDirectory().toURI().toString())
			.call();
		File hello = new File(db.getWorkTree() + "/foo/hello.txt");
		assertTrue(hello.exists());
		BufferedReader reader = new BufferedReader(new FileReader(hello));
		String content = reader.readLine();
		assertTrue(content.startsWith("world"));
	}
}
