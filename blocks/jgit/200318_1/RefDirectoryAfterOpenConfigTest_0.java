
package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;

public class FileRepositoryBuilderAfterOpenConfigTest extends FileRepositoryBuilderTest {
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		StoredConfig userConfig = SystemReader.getInstance().getUserConfig();
		userConfig.setEnum(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_TRUST_PACKED_REFS_STAT
				CoreConfig.TrustPackedRefsStat.AFTER_OPEN);
		userConfig.save();
	}
}
