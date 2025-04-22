
package org.eclipse.jgit.http.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.http.server.resolver.FileResolver;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;

public class FileResolverTest extends LocalDiskRepositoryTestCase {
	public void testUnreasonableNames() throws ServiceNotEnabledException {
		assertUnreasonable("");
		assertUnreasonable("a\\b");
		assertUnreasonable("../b");
		assertUnreasonable("a/../b");
		assertUnreasonable("a/./b");

		if (new File("/foo").isAbsolute())
			assertUnreasonable("/foo");


		if (new File("C:/windows").isAbsolute())
			assertUnreasonable("C:/windows");
	}

	private void assertUnreasonable(String name)
			throws ServiceNotEnabledException {
		FileResolver r = new FileResolver(new File(".")
		try {
			r.open(null
			fail("Opened unreasonable name \"" + name + "\"");
		} catch (RepositoryNotFoundException e) {
			assertEquals("repository not found: " + name
			assertNull("has no cause"
		}
	}

	public void testExportOk() throws IOException {
		final Repository a = createBareRepository();
		final String name = a.getDirectory().getName();
		final File base = a.getDirectory().getParentFile();
		final File export = new File(a.getDirectory()
		FileResolver resolver;

		assertFalse("no git-daemon-export-ok"
		resolver = new FileResolver(base
		try {
			resolver.open(null
			fail("opened non-exported repository");
		} catch (ServiceNotEnabledException e) {
			assertEquals("Service not enabled"
		}

		resolver = new FileResolver(base
		try {
			resolver.open(null
		} catch (ServiceNotEnabledException e) {
			fail("did not honor export-all flag");
		}

		export.createNewFile();
		assertTrue("has git-daemon-export-ok"
		resolver = new FileResolver(base
		try {
			resolver.open(null
		} catch (ServiceNotEnabledException e) {
			fail("did not honor git-daemon-export-ok");
		}
	}

	public void testNotAGitRepository() throws IOException
			ServiceNotEnabledException {
		final Repository a = createBareRepository();
		final String name = a.getDirectory().getName() + "-not-a-git";
		final File base = a.getDirectory().getParentFile();
		FileResolver resolver = new FileResolver(base

		try {
			resolver.open(null
		} catch (RepositoryNotFoundException e) {
			assertEquals("repository not found: " + name

			Throwable why = e.getCause();
			assertNotNull("has cause"
			assertEquals("repository not found: "
					+ new File(base
		}
	}
}
