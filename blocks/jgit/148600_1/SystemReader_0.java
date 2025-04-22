package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SystemReaderTest {
	private Path trash;

	private Path mockSystemConfig;

	private Path mockUserConfig;

	@Mock
	private FS fs;

	@Before
	public void setup() throws Exception {
		trash = Files.createTempDirectory("jgit_test");
		mockSystemConfig = trash.resolve("systemgitconfig");
		Files.write(mockSystemConfig
				.getBytes(StandardCharsets.UTF_8));
		mockUserConfig = trash.resolve(".gitconfig");
		Files.write(mockUserConfig
				"[core]\n  bare = false\n".getBytes(StandardCharsets.UTF_8));
		when(fs.getGitSystemConfig()).thenReturn(mockSystemConfig.toFile());
		when(fs.userHome()).thenReturn(trash.toFile());
		SystemReader.setInstance(null);
	}

	@After
	public void teardown() throws Exception {
		FileUtils.delete(trash.toFile()
	}

	@Test
	public void openSystemConfigReturnsDifferentInstances() throws Exception {
		FileBasedConfig system1 = SystemReader.getInstance()
				.openSystemConfig(null
		system1.load();
		assertEquals("false"
				system1.getString("core"

		FileBasedConfig system2 = SystemReader.getInstance()
				.openSystemConfig(null
		system2.load();
		assertEquals("false"
				system2.getString("core"

		system1.setString("core"
		assertEquals("true"
				system1.getString("core"
		assertEquals("false"
				system2.getString("core"
	}

	@Test
	public void openUserConfigReturnsDifferentInstances() throws Exception {
		FileBasedConfig user1 = SystemReader.getInstance().openUserConfig(null
				fs);
		user1.load();
		assertEquals("false"

		FileBasedConfig user2 = SystemReader.getInstance().openUserConfig(null
				fs);
		user2.load();
		assertEquals("false"

		user1.setString("core"
		assertEquals("true"
		assertEquals("false"
	}
}
