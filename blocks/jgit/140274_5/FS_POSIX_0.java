
package org.eclipse.jgit.util;

import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FS_POSIXTest {
	private SystemReader originalSystemReaderInstance;
    private FileBasedConfig mockSystemConfig;
    private FileBasedConfig mockUserConfig;

	@Before
	public void setUp() throws Exception {
        SystemReader systemReader = Mockito.mock(SystemReader.class);

        originalSystemReaderInstance = SystemReader.getInstance();
        SystemReader.setInstance(systemReader);

        mockSystemConfig = mock(FileBasedConfig.class);
        mockUserConfig = mock(FileBasedConfig.class);
		when(systemReader.openSystemConfig(any()
		when(systemReader.openUserConfig(any()

        when(mockSystemConfig.getString(ConfigConstants.CONFIG_CORE_SECTION
    }

    @After
    public void tearDown() {
        SystemReader.setInstance(originalSystemReaderInstance);
    }

	@Test
	public void supportsAtomicCreateNewFile_shouldReturnSupportedAsDefault() {
        assertTrue(new FS_POSIX().supportsAtomicCreateNewFile());
	}

    @Test
    public void supportsAtomicCreateNewFile_shouldReturnTrueIfFlagIsSetInUserConfig() {
        setAtomicCreateCreationFlag(mockUserConfig
        assertTrue(new FS_POSIX().supportsAtomicCreateNewFile());
    }

    @Test
    public void supportsAtomicCreateNewFile_shouldReturnTrueIfFlagIsSetInSystemConfig() {
        setAtomicCreateCreationFlag(mockSystemConfig
        assertTrue(new FS_POSIX().supportsAtomicCreateNewFile());
    }

    @Test
    public void supportsAtomicCreateNewFile_shouldReturnFalseIfFlagUnsetInUserConfig() {
        setAtomicCreateCreationFlag(mockUserConfig
        assertFalse(new FS_POSIX().supportsAtomicCreateNewFile());
    }

    @Test
    public void supportsAtomicCreateNewFile_shouldReturnFalseIfFlagUnsetInSystemConfig() {
       setAtomicCreateCreationFlag(mockSystemConfig
       assertFalse(new FS_POSIX().supportsAtomicCreateNewFile());
    }

    private void setAtomicCreateCreationFlag(FileBasedConfig config
        when(config.getString(ConfigConstants.CONFIG_CORE_SECTION
                        ConfigConstants.CONFIG_KEY_SUPPORTSATOMICFILECREATION)).thenReturn(value);
    }
}
