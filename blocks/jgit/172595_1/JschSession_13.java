
package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;

public class JSchSshProtocol2Test extends JSchSshTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		StoredConfig config = ((Repository) db).getConfig();
		config.setInt("protocol"
		config.save();
	}
}
