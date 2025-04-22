
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile.positive;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile;
import org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile.HostEntry;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.ConfigRepository;

public class OpenSshConfig implements ConfigRepository {

	public static OpenSshConfig get(FS fs) {
		File home = fs.userHome();
		if (home == null)

		final File config = new File(new File(home
				SshConstants.CONFIG);
		return new OpenSshConfig(home
	}

	private OpenSshConfigFile configFile;

	OpenSshConfig(File h
		configFile = new OpenSshConfigFile(h
				SshSessionFactory.getLocalUserName());
	}

	public Host lookup(String hostName) {
		HostEntry entry = configFile.lookup(hostName
		return new Host(entry
	}

	public static class Host {
		String hostName;

		int port;

		File identityFile;

		String user;

		String preferredAuthentications;

		Boolean batchMode;

		String strictHostKeyChecking;

		int connectionAttempts;

		private HostEntry entry;

		private Config config;

		private static final Map<String
				String.CASE_INSENSITIVE_ORDER);

		static {
			KEY_MAP.put("kex"
			KEY_MAP.put("server_host_key"
			KEY_MAP.put("cipher.c2s"
			KEY_MAP.put("cipher.s2c"
			KEY_MAP.put("mac.c2s"
			KEY_MAP.put("mac.s2c"
			KEY_MAP.put("compression.s2c"
			KEY_MAP.put("compression.c2s"
			KEY_MAP.put("compression_level"
			KEY_MAP.put("MaxAuthTries"
					SshConstants.NUMBER_OF_PASSWORD_PROMPTS);
		}

		private static String mapKey(String key) {
			String k = KEY_MAP.get(key);
			return k != null ? k : key;
		}

		public Host() {
		}

		Host(HostEntry entry
			this.entry = entry;
			complete(hostName
		}

		public String getStrictHostKeyChecking() {
			return strictHostKeyChecking;
		}

		public String getHostName() {
			return hostName;
		}

		public int getPort() {
			return port;
		}

		public File getIdentityFile() {
			return identityFile;
		}

		public String getUser() {
			return user;
		}

		public String getPreferredAuthentications() {
			return preferredAuthentications;
		}

		public boolean isBatchMode() {
			return batchMode != null && batchMode.booleanValue();
		}

		public int getConnectionAttempts() {
			return connectionAttempts;
		}


		private void complete(String initialHostName
			hostName = entry.getValue(SshConstants.HOST_NAME);
			user = entry.getValue(SshConstants.USER);
			port = positive(entry.getValue(SshConstants.PORT));
			connectionAttempts = positive(
					entry.getValue(SshConstants.CONNECTION_ATTEMPTS));
			strictHostKeyChecking = entry
					.getValue(SshConstants.STRICT_HOST_KEY_CHECKING);
			batchMode = Boolean.valueOf(OpenSshConfigFile
					.flag(entry.getValue(SshConstants.BATCH_MODE)));
			preferredAuthentications = entry
					.getValue(SshConstants.PREFERRED_AUTHENTICATIONS);
			if (hostName == null || hostName.isEmpty()) {
				hostName = initialHostName;
			}
			if (user == null || user.isEmpty()) {
				user = localUserName;
			}
			if (port <= 0) {
				port = SshConstants.SSH_DEFAULT_PORT;
			}
			if (connectionAttempts <= 0) {
				connectionAttempts = 1;
			}
			List<String> identityFiles = entry
					.getValues(SshConstants.IDENTITY_FILE);
			if (identityFiles != null && !identityFiles.isEmpty()) {
				identityFile = new File(identityFiles.get(0));
			}
		}

		Config getConfig() {
			if (config == null) {
				config = new Config() {

					@Override
					public String getHostname() {
						return Host.this.getHostName();
					}

					@Override
					public String getUser() {
						return Host.this.getUser();
					}

					@Override
					public int getPort() {
						return Host.this.getPort();
					}

					@Override
					public String getValue(String key) {
							if (!OpenSshConfigFile.flag(
									Host.this.entry.getValue(mapKey(key)))) {
								return "none
							}
							return "zlib@openssh.com
						}
						return Host.this.entry.getValue(mapKey(key));
					}

					@Override
					public String[] getValues(String key) {
						List<String> values = Host.this.entry
								.getValues(mapKey(key));
						if (values == null) {
							return new String[0];
						}
						return values.toArray(new String[0]);
					}
				};
			}
			return config;
		}

		@Override
		@SuppressWarnings("nls")
		public String toString() {
			return "Host [hostName=" + hostName + "
					+ "
					+ "
					+ "
					+ strictHostKeyChecking + "
					+ connectionAttempts + "
		}
	}

	@Override
	public Config getConfig(String hostName) {
		Host host = lookup(hostName);
		return host.getConfig();
	}

	@Override
	public String toString() {
	}
}
