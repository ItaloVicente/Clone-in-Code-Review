package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Policy;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SecurityManagerMissingPermissionsTest extends RepositoryTestCase {

	private final StringWriter errorOutputWriter = new StringWriter();

	private WriterAppender appender;

	private SecurityManager originalSecurityManager;

	@Override
	@Before
	public void setUp() throws Exception {
		originalSecurityManager = System.getSecurityManager();

		appender = new WriterAppender(
				new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)
				errorOutputWriter);

		Logger.getRootLogger().addAppender(appender);

		refreshPolicyAllPermission(Policy.getPolicy());
		System.setSecurityManager(new SecurityManager());
		super.setUp();
	}

	@Test
	public void testCreateNewRepos_MissingPermissions() throws Exception {
		File wcTree = new File(getTemporaryDirectory()
				"CreateNewRepositoryTest_testCreateNewRepos");

		File marker = new File(getTemporaryDirectory()
		Files.write(marker.toPath()
		assertTrue("Can write in test directory"
		FileUtils.delete(marker);
		assertFalse("Can delete in test direcory"

		Git git = Git.init().setBare(false)
				.setDirectory(new File(wcTree.getAbsolutePath())).call();

		addRepoToClose(git.getRepository());

		assertEquals(""
	}

	@Override
	@After
	public void tearDown() throws Exception {
		System.setSecurityManager(originalSecurityManager);
		Logger.getRootLogger().removeAppender(appender);
		super.tearDown();
	}

	private static void refreshPolicyAllPermission(Policy policy)
			throws IOException {
		String policyString = "grant { permission java.security.AllPermission; };";

		Path policyFile = Files.createTempFile("testpolicy"

		try {
			Files.write(policyFile
			System.setProperty("java.security.policy"
					policyFile.toUri().toURL().toString());
			policy.refresh();
		} finally {
			try {
				Files.delete(policyFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
