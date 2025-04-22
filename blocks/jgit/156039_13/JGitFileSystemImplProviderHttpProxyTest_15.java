package org.eclipse.jgit.niofs.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.HOOK_DIR;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.util.Map;

import org.eclipse.jgit.hooks.PreCommitHook;
import org.junit.Test;

public class JGitFileSystemImplProviderHookTest extends BaseTest {

	@Override
	public Map<String
		Map<String
		try {
			final File hooksDir = util.createTempDirectory();
			gitPrefs.put(HOOK_DIR

			writeMockHook(hooksDir
			writeMockHook(hooksDir
		} catch (IOException e) {
			e.printStackTrace();
		}

		return gitPrefs;
	}

	@Test
	public void testInstalledHook() throws IOException {

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		if (fs instanceof JGitFileSystemImpl) {
			File[] hooks = new File(((JGitFileSystemImpl) fs).getGit().getRepository().getDirectory()
					.listFiles();
			assertThat(hooks).isNotEmpty().isNotNull();
			assertThat(hooks.length).isEqualTo(2);

			boolean foundPreCommitHook = false;
			boolean foundPostCommitHook = false;
			for (File hook : hooks) {
				if (hook.getName().equals("pre-commit")) {
					foundPreCommitHook = hook.canExecute();
				} else if (hook.getName().equals("post-commit")) {
					foundPostCommitHook = hook.canExecute();
				}
			}
			assertThat(foundPreCommitHook).isTrue();
			assertThat(foundPostCommitHook).isTrue();
		}
	}

	@Test
	public void testExecutedPostCommitHook() throws IOException {
		testHook(provider
	}

	@Test
	public void testNotSupportedPreCommitHook() throws IOException {
		testHook(provider
	}
}
