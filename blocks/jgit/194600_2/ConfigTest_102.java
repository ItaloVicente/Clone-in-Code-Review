
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CommitTemplateConfigTest {

	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();

	@Test
	public void testCommitTemplatePathInHomeDirecory()
			throws ConfigInvalidException
		Config config = new Config(null);
		File tempFile = tmp.newFile("testCommitTemplate-");
		File workTree = tmp.newFolder("dummy-worktree");
		Repository repo = FileRepositoryBuilder.create(workTree);
		String templateContent = "content of the template";
		JGitTestUtil.write(tempFile
		String homeDir = System.getProperty("user.home");
		File tempFileInHomeDirectory = File.createTempFile("fileInHomeFolder"
				".tmp"
		tempFileInHomeDirectory.deleteOnExit();
		JGitTestUtil.write(tempFileInHomeDirectory
		String expectedTemplatePath = "~/" + tempFileInHomeDirectory.getName();
		config = ConfigTest
				.parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		String templatePath = config.get(CommitConfig.KEY)
				.getCommitTemplatePath();
		assertEquals(expectedTemplatePath
		assertEquals(templateContent
				config.get(CommitConfig.KEY).getCommitTemplateContent(repo));
	}
}
