package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_PORT;

public class JGitFileSystemImplProviderEncodingTest extends AbstractTestInfra {

	private int gitDaemonPort;

	@Override
	public Map<String
		Map<String
		gitPrefs.put(GIT_DAEMON_ENABLED
		gitDaemonPort = findFreePort();
		gitPrefs.put(GIT_DAEMON_PORT
		return gitPrefs;
	}

	@Test
	public void test() throws IOException {

		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file-name.txt"
					}
				}).execute();

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file+name.txt"
					}
				}).execute();

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file name.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		fs.getPath("file+name.txt").toUri();

		provider.getPath(fs.getPath("file+name.txt").toUri());

		URI uri = fs.getPath("file+name.txt").toUri();
		Path path = provider.getPath(uri);
		Path path1 = fs.getPath("file+name.txt");
		assertThat(path).isEqualTo(path1);

		assertThat(provider.getPath(fs.getPath("file name.txt").toUri())).isEqualTo(fs.getPath("file name.txt"));

		assertThat(fs.getPath("file.txt").toUri());

		assertThat(provider.getPath(fs.getPath("file.txt").toUri())).isEqualTo(fs.getPath("file.txt"));
	}
}
