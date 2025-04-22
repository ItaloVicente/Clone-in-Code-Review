package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitFileSystemImplProviderMigrationTest extends AbstractTestInfra {

	@Test
	public void testCreateANewDirectoryWithMigrationEnv() throws IOException {

		final Map<String
			{
				put("init"
				put("migrate-from"
			}
		};

		final URI newUri = URI.create(newPath);
		provider.newFileSystem(newUri

		provider.getFileSystem(newUri);
		assertThat(new File(provider.getGitRepoContainerDir()
		assertThat(provider.getFileSystem(newUri)).isNotNull();
	}
}
