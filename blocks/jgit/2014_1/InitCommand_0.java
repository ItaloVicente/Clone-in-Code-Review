package org.eclipse.jgit.api;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;

public class InitCommandTest extends RepositoryTestCase {

	public void testInitRepository() {
		try {
			InitCommand command = new InitCommand();
			Repository repository = command.call();
			assertNotNull(repository);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testInitBareRepository() {
		try {
			InitCommand command = new InitCommand();
			command.setBare(true);
			Repository repository = command.call();
			assertNotNull(repository);
			assertTrue(repository.isBare());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
