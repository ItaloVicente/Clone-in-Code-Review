package org.eclipse.jgit.transport.ssh.jsch;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jgit.transport.SshSessionFactory;
import org.junit.Test;

public class ServiceLoaderTest {

	@Test
	public void testDefaultFactoryFound() {
		SshSessionFactory defaultFactory = SshSessionFactory.getInstance();
		assertNotNull(defaultFactory);
	}
}
