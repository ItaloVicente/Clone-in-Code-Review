package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;

public class InitCommandTest extends RepositoryTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testInitRepository() {
		try {
			File directory = createTempDirectory("testInitRepository");
			InitCommand command = new InitCommand();
			command.setDirectory(directory);
			Repository repository = command.call().getRepository();
			assertNotNull(repository);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testInitBareRepository() {
		try {
			File directory = createTempDirectory("testInitBareRepository");
			InitCommand command = new InitCommand();
			command.setDirectory(directory);
			command.setBare(true);
			Repository repository = command.call().getRepository();
			assertNotNull(repository);
			assertTrue(repository.isBare());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public static File createTempDirectory(String name) throws IOException {
		final File temp;
		temp = File.createTempFile(name

		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: "
					+ temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: "
					+ temp.getAbsolutePath());
		}
		return temp;
	}

}
