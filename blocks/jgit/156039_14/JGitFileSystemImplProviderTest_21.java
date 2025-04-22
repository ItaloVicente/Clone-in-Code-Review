package org.eclipse.jgit.niofs.internal;

import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_IDLE_TIMEOUT;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_PORT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.sshd.server.SshServer;
import org.assertj.core.api.Assertions;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHookExecutionContext;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.security.AuthenticationService;
import org.eclipse.jgit.niofs.internal.security.User;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Assume;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class JGitFileSystemImplProviderSSHTest extends BaseTest {

	private int gitSSHPort;

	@Override
	public Map<String
		Map<String

		gitPrefs.put(GIT_SSH_ENABLED
		gitSSHPort = findFreePort();
		gitPrefs.put(GIT_SSH_PORT
		gitPrefs.put(GIT_SSH_IDLE_TIMEOUT

		return gitPrefs;
	}

	@Test
	public void testSSHPostReceiveHook() throws IOException {
		FileSystemHooks.FileSystemHook hook = spy(new FileSystemHooks.FileSystemHook() {
			@Override
			public void execute(FileSystemHookExecutionContext context) {
				assertEquals("repo"
			}
		});

		Assume.assumeFalse("UF-511"
		provider.setJAASAuthenticator(new AuthenticationService() {
			private User user;

			@Override
			public User login(String s
				user = new User() {
					@Override
					public String getIdentifier() {
						return s;
					}
				};
				return user;
			}

			@Override
			public boolean isLoggedIn() {
				return user != null;
			}

			@Override
			public void logout() {
				user = null;
			}

			@Override
			public User getUser() {
				return user;
			}
		});
		provider.setAuthorizer((fs

		CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("admin"
		assertEquals("10001"

		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo
				new HashMap<String
					{
						put(FileSystemHooks.ExternalUpdate.name()
					}
				});

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file-name.txt"
					}
				}).execute();

		JGitFileSystem clone;
			{
				put("init"
				put("origin"
			}
		});

		assertNotNull(clone);


		ArgumentCaptor<FileSystemHookExecutionContext> captor = ArgumentCaptor
				.forClass(FileSystemHookExecutionContext.class);

		verify(hook).execute(captor.capture());

		Assertions.assertThat(captor.getValue()).isNotNull().hasFieldOrPropertyWithValue("fsName"
	}
}
