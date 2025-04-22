package org.eclipse.jgit.api;

import java.io.File;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;

public class InitCommandTest extends RepositoryTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testInitRepository() {
		String directory = new File(trash
		try {
			InitCommand command = new InitCommand();
			command.setDirectory(directory);
			Repository repository = command.call().getRepository();
			assertNotNull(repository);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testInitBareRepository() {
		String directory = new File(trash
		try {
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

}
