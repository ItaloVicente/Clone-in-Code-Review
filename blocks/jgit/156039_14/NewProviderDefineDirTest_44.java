package org.eclipse.jgit.niofs.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_NIO_DIR;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_NIO_DIR_NAME;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.REPOSITORIES_CONTAINER_DIR;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class NewProviderDefineDirTest extends BaseTest {

	private String dirPathName;
	private File tempDir;

	public NewProviderDefineDirTest(final String dirPathName) {
		this.dirPathName = dirPathName;
	}

	@Parameters(name = "{index}: dir name: {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { REPOSITORIES_CONTAINER_DIR }
	}

	@Override
	public Map<String
		try {
			tempDir = util.createTempDirectory();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		Map<String
		gitPrefs.put(GIT_NIO_DIR
		if (!REPOSITORIES_CONTAINER_DIR.equals(dirPathName)) {
			gitPrefs.put(GIT_NIO_DIR_NAME
		}
		return gitPrefs;
	}

	@Test
	public void testUsingProvidedPath() throws IOException {

		JGitFileSystemProxy fileSystem = (JGitFileSystemProxy) provider.newFileSystem(newRepo

		String[] names = tempDir.list();

		assertThat(names).isEmpty();

		String[] repos = new File(tempDir

		assertThat(repos).isNull();

		fileSystem.getRealJGitFileSystem();

		names = tempDir.list();

		assertThat(names).contains(dirPathName);

		repos = new File(tempDir

		assertThat(repos).contains("repo-name.git");
	}
}
