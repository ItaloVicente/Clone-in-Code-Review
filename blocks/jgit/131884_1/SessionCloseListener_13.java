package org.eclipse.jgit.transport.sshd;

import static org.eclipse.jgit.transport.OpenSshConfigFile.flag;
import static org.eclipse.jgit.transport.OpenSshConfigFile.positive;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.config.hosts.HostConfigEntryResolver;
import org.apache.sshd.common.util.net.SshdSocketAddress;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.transport.OpenSshConfigFile;
import org.eclipse.jgit.transport.OpenSshConfigFile.HostEntry;
import org.eclipse.jgit.transport.SshConstants;

public class JGitSshConfig implements HostConfigEntryResolver {

	private OpenSshConfigFile configFile;

	public JGitSshConfig(@NonNull File home
			@NonNull String localUserName) {
		configFile = new OpenSshConfigFile(home
	}

	@Override
	public HostConfigEntry resolveEffectiveHost(String host
			String username) throws IOException {
		HostEntry entry = configFile.lookup(host
		JGitHostConfigEntry config = new JGitHostConfigEntry();
		String hostName = entry.getValue(SshConstants.HOST_NAME);
		if (hostName == null || hostName.isEmpty()) {
			hostName = host;
		}
		config.setHostName(hostName);
		String user = username != null && !username.isEmpty() ? username
				: entry.getValue(SshConstants.USER);
		if (user == null || user.isEmpty()) {
			user = configFile.getLocalUserName();
		}
		config.setUsername(user);
		int p = port >= 0 ? port : positive(entry.getValue(SshConstants.PORT));
		config.setPort(p >= 0 ? p : SshConstants.SSH_DEFAULT_PORT);
		config.setIdentities(entry.getValues(SshConstants.IDENTITY_FILE));
		config.setIdentitiesOnly(
				flag(entry.getValue(SshConstants.IDENTITIES_ONLY)));
		Map<String
				String.CASE_INSENSITIVE_ORDER);
		allOptions.putAll(entry.getOptions());
		entry.getMultiValuedOptions().entrySet().stream()
				.forEach(e -> allOptions.put(e.getKey()
						String.join("
package org.eclipse.jgit.transport.sshd;

import java.nio.file.Path;
import java.security.KeyPair;
import java.util.function.Function;

public interface KeyCache {

	KeyPair get(Path path

	public void close();
}
