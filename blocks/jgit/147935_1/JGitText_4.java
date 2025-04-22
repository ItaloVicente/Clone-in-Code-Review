
package org.eclipse.jgit.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FS_POSIXTest {
	private SystemReader originalSystemReaderInstance;

	private FileBasedConfig systemConfig;

	private FileBasedConfig userConfig;

	private Path tmp;

	@Before
	public void setUp() throws Exception {
		tmp = Files.createTempDirectory("jgit_test_");
		MockSystemReader mockSystemReader = new MockSystemReader();
		SystemReader.setInstance(mockSystemReader);

		FS.getFileStoreAttributes(tmp.getParent());
		systemConfig = new FileBasedConfig(
				new File(tmp.toFile()
		userConfig = new FileBasedConfig(systemConfig
				new File(tmp.toFile()
		userConfig.setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTODETACH
		userConfig.save();
		mockSystemReader.setSystemGitConfig(systemConfig);
		mockSystemReader.setUserGitConfig(userConfig);

		originalSystemReaderInstance = SystemReader.getInstance();
		SystemReader.setInstance(mockSystemReader);
	}

	@After
	public void tearDown() throws IOException {
		SystemReader.setInstance(originalSystemReaderInstance);
		FileUtils.delete(tmp.toFile()
	}

	@Test
	public void supportsAtomicCreateNewFile_shouldReturnSupportedAsDefault() {
		assertTrue(new FS_POSIX().supportsAtomicCreateNewFile());
	}

	@Test
	public void supportsAtomicCreateNewFile_shouldReturnTrueIfFlagIsSetInUserConfig() {
		setAtomicCreateCreationFlag(userConfig
		assertTrue(new FS_POSIX().supportsAtomicCreateNewFile());
	}

	@Test
	public void supportsAtomicCreateNewFile_shouldReturnTrueIfFlagIsSetInSystemConfig() {
		setAtomicCreateCreationFlag(systemConfig
		assertTrue(new FS_POSIX().supportsAtomicCreateNewFile());
	}

	@Test
	public void supportsAtomicCreateNewFile_shouldReturnFalseIfFlagUnsetInUserConfig() {
		setAtomicCreateCreationFlag(userConfig
		assertFalse(new FS_POSIX().supportsAtomicCreateNewFile());
	}

	@Test
	public void supportsAtomicCreateNewFile_shouldReturnFalseIfFlagUnsetInSystemConfig() {
		setAtomicCreateCreationFlag(systemConfig
		assertFalse(new FS_POSIX().supportsAtomicCreateNewFile());
	}

	private void setAtomicCreateCreationFlag(FileBasedConfig config
			String value) {
		config.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SUPPORTSATOMICFILECREATION
	}
}
