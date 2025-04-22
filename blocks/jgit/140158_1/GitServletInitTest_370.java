
package org.eclipse.jgit.http.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.FileResolver;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class FileResolverTest extends LocalDiskRepositoryTestCase {
	@Test
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

	private static void assertUnreasonable(String name)
			throws ServiceNotEnabledException {
		FileResolver<RepositoryResolver> r = new FileResolver<>(
				new File(".")
		try {
			r.open(null
			fail("Opened unreasonable name \"" + name + "\"");
		} catch (RepositoryNotFoundException e) {
			assertEquals("repository not found: " + name
			assertNull("has no cause"
		}
	}

	@Test
	public void testExportOk() throws IOException {
		final Repository a = createBareRepository();
		final String name = a.getDirectory().getName();
		final File base = a.getDirectory().getParentFile();
		final File export = new File(a.getDirectory()
		FileResolver<RepositoryResolver> resolver;

		assertFalse("no git-daemon-export-ok"
		resolver = new FileResolver<>(base
		try {
			resolver.open(null
			fail("opened non-exported repository");
		} catch (ServiceNotEnabledException e) {
			assertEquals("Service not enabled"
		}

		resolver = new FileResolver<>(base
		try {
			resolver.open(null
		} catch (ServiceNotEnabledException e) {
			fail("did not honor export-all flag");
		}

		FileUtils.createNewFile(export);
		resolver = new FileResolver<>(base
		try {
			resolver.open(null
		} catch (ServiceNotEnabledException e) {
			fail("did not honor git-daemon-export-ok");
		}
	}

	@Test
	public void testNotAGitRepository() throws IOException
			ServiceNotEnabledException {
		final Repository a = createBareRepository();
		final String name = a.getDirectory().getName() + "-not-a-git";
		final File base = a.getDirectory().getParentFile();
		FileResolver<RepositoryResolver> resolver = new FileResolver<>(
				base

		try {
			resolver.open(null
			fail("opened non-git repository");
		} catch (RepositoryNotFoundException e) {
			assertEquals("repository not found: " + name

			Throwable why = e.getCause();
			assertNotNull("has cause"
			assertEquals("repository not found: "
					+ new File(base
		}
	}
}
