package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.lang.reflect.ReflectPermission;
import java.nio.file.Files;
import java.security.Permission;
import java.security.SecurityPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyPermission;
import java.util.logging.LoggingPermission;

import javax.security.auth.AuthPermission;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.junit.SeparateClassloaderTestRunner;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.SystemReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SeparateClassloaderTestRunner.class)
public class SecurityManagerTest {
	private File root;

	private SecurityManager originalSecurityManager;

	private List<Permission> permissions = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		SystemReader.setInstance(new MockSystemReader());
		root = Files.createTempDirectory("jgit-security").toFile();

		permissions.add(new RuntimePermission("*"));
		permissions.add(new SecurityPermission("*"));
		permissions.add(new AuthPermission("*"));
		permissions.add(new ReflectPermission("*"));
		permissions.add(new PropertyPermission("*"
		permissions.add(new LoggingPermission("control"

		permissions.add(new FilePermission(
				System.getProperty("java.home") + "/-"

		String tempDir = System.getProperty("java.io.tmpdir");
		permissions.add(new FilePermission(tempDir
		permissions
				.add(new FilePermission(tempDir + "/-"

		String classPath = System.getProperty("java.class.path");
		if (classPath != null) {
			for (String path : classPath.split(File.pathSeparator)) {
				permissions.add(new FilePermission(path
			}
		}
		String jgitSourcesRoot = new File(System.getProperty("user.dir"))
				.getParent();
		permissions.add(new FilePermission(jgitSourcesRoot + "/-"

		permissions.add(new FilePermission(root.getPath() + "/-"
				"read

		originalSecurityManager = System.getSecurityManager();
		System.setSecurityManager(new SecurityManager() {

			@Override
			public void checkPermission(Permission requested) {
				for (Permission permission : permissions) {
					if (permission.implies(requested)) {
						return;
					}
				}

				super.checkPermission(requested);
			}
		});
	}

	@After
	public void tearDown() throws Exception {
		System.setSecurityManager(originalSecurityManager);

		FileUtils.delete(root
	}

	@Test
	public void testInitAndClone() throws IOException
		File remote = new File(root
		File local = new File(root

		try (Git git = Git.init().setDirectory(remote).call()) {
			JGitTestUtil.write(new File(remote
			git.add().addFilepattern(".").call();
			git.commit().setMessage("Initial commit").call();
		}

		try (Git git = Git.cloneRepository().setURI(remote.toURI().toString())
				.setDirectory(local).call()) {
			assertTrue(new File(local

			JGitTestUtil.write(new File(local
			git.add().addFilepattern(".").call();
			RevCommit commit1 = git.commit().setMessage("Commit on local repo")
					.call();
			assertEquals("Commit on local repo"
			assertNotNull(TreeWalk.forPath(git.getRepository()
					commit1.getTree()));
		}

	}

}
