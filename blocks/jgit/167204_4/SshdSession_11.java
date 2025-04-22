package org.eclipse.jgit.transport.sshd;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;

public class ApacheSshTestProtocol2 extends ApacheSshTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		StoredConfig config = ((Repository) db).getConfig();
		config.setInt("protocol"
		config.save();
	}
}
