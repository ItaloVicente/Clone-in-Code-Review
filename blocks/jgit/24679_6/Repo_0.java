package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;

public class RepoTest extends CLIRepositoryTestCase {
	private Repository remoteDb;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		remoteDb = createWorkRepository();
		Git git = new Git(remoteDb);
		JGitTestUtil.writeTrashFile(remoteDb
		git.add().addFilepattern("hello.txt").call();
		git.commit().setMessage("Initial commit").call();
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
		StringBuilder cmd = new StringBuilder("git repo --base-uri=\"")
			.append(remoteDb.getDirectory().toURI().toString())
			.append("\" \"")
			.append(db.getWorkTree().getAbsolutePath())
			.append("/manifest.xml\"");
		execute(cmd.toString());
		File hello = new File(db.getWorkTree()
		assertTrue("submodule was checked out."
		BufferedReader reader = new BufferedReader(new FileReader(hello));
		String content = reader.readLine();
		reader.close();
		assertEquals("submodule content is as expected."
	}
}
