
package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Constants;

@SuppressWarnings("nls")
public final class SshConstants {

	private SshConstants() {
	}

	public static final int SSH_DEFAULT_PORT = 22;

	public static final String SSH_SCHEME = "ssh";

	public static final String SFTP_SCHEME = "sftp";

	public static final String SSH_DIR = ".ssh";

	public static final String CONFIG = Constants.CONFIG;

	public static final String KNOWN_HOSTS = "known_hosts";


	public static final String BATCH_MODE = "BatchMode";

	public static final String CANONICAL_DOMAINS = "CanonicalDomains";

	public static final String CERTIFICATE_FILE = "CertificateFile";

	public static final String CIPHERS = "Ciphers";

	public static final String COMPRESSION = "Compression";

	public static final String CONNECTION_ATTEMPTS = "ConnectionAttempts";

	public static final String CONTROL_PATH = "ControlPath";

	public static final String GLOBAL_KNOWN_HOSTS_FILE = "GlobalKnownHostsFile";

	public static final String HOST = "Host";

	public static final String HOST_KEY_ALGORITHMS = "HostKeyAlgorithms";

	public static final String HOST_NAME = "HostName";

	public static final String IDENTITIES_ONLY = "IdentitiesOnly";

	public static final String IDENTITY_AGENT = "IdentityAgent";

	public static final String IDENTITY_FILE = "IdentityFile";

	public static final String KEX_ALGORITHMS = "KexAlgorithms";

	public static final String LOCAL_COMMAND = "LocalCommand";

	public static final String LOCAL_FORWARD = "LocalForward";

	public static final String MACS = "MACs";

	public static final String NUMBER_OF_PASSWORD_PROMPTS = "NumberOfPasswordPrompts";

	public static final String PORT = "Port";

	public static final String PREFERRED_AUTHENTICATIONS = "PreferredAuthentications";

	public static final String PROXY_COMMAND = "ProxyCommand";

	public static final String REMOTE_COMMAND = "RemoteCommand";

	public static final String REMOTE_FORWARD = "RemoteForward";

	public static final String SEND_ENV = "SendEnv";

	public static final String STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";

	public static final String USER = "User";

	public static final String USER_KNOWN_HOSTS_FILE = "UserKnownHostsFile";


	public static final String YES = "yes";

	public static final String ON = "on";

	public static final String TRUE = "true";

	public static final String NO = "no";

	public static final String OFF = "off";

	public static final String FALSE = "false";


	public static final String ID_RSA = "id_rsa";

	public static final String ID_DSA = "id_dsa";

	public static final String ID_ECDSA = "id_ecdsa";

	public static final String ID_ED25519 = "id_ed25519";

			ID_RSA
	};
}
