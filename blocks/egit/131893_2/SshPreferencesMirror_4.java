package org.eclipse.egit.core.internal;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.common.config.keys.FilePasswordProvider;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.securestorage.EGitSecureStore;
import org.eclipse.egit.core.securestorage.UserPasswordCredentials;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.sshd.IdentityPasswordProvider;
import org.eclipse.jgit.transport.sshd.ProxyData;
import org.eclipse.jgit.transport.sshd.ProxyDatabase;
import org.eclipse.jgit.transport.sshd.SshdSessionFactory;
import org.eclipse.jgit.transport.sshd.proxy.HttpClientConnector;
import org.eclipse.jgit.transport.sshd.proxy.Socks5ClientConnector;

public class EGitSshdSessionFactory extends SshdSessionFactory {

	public EGitSshdSessionFactory() {
		super(null, new EGitProxyDatabase());
	}

	@Override
	public File getSshDirectory() {
		File file = super.getSshDirectory();
		if (file != null) {
			return file;
		}
		return SshPreferencesMirror.INSTANCE.getSshDirectory();
	}

	@Override
	@NonNull
	protected List<Path> getDefaultIdentities(@NonNull File sshDir) {
		List<Path> defaultKeys = SshPreferencesMirror.INSTANCE
				.getDefaultIdentities(sshDir);
		if (defaultKeys == null) {
			return Collections.emptyList();
		} else if (defaultKeys.isEmpty()) {
			return super.getDefaultIdentities(sshDir);
		}
		return defaultKeys;
	}

	@Override
	protected String getDefaultPreferredAuthentications() {
		return SshPreferencesMirror.INSTANCE.getPreferredAuthentications();
	}

	@Override
	protected FilePasswordProvider createFilePasswordProvider(
			CredentialsProvider provider) {
		return new EGitFilePasswordProvider(provider,
				Activator.getDefault().getSecureStore());
	}

	private static class EGitProxyDatabase implements ProxyDatabase {

		@Override
		public ProxyData get(HostConfigEntry hostConfig,
				InetSocketAddress remoteAddress) {
			IProxyService service = Activator.getDefault().getProxyService();
			if (service == null || !service.isProxiesEnabled()) {
				return null;
			}
			try {
				IProxyData[] data = service
						.select(new URI(IProxyData.SOCKS_PROXY_TYPE,
						"//" + remoteAddress.getHostString(), null)); //$NON-NLS-1$
				if (data == null || data.length == 0) {
					data = service.select(new URI(IProxyData.HTTP_PROXY_TYPE,
							"//" + remoteAddress.getHostString(), null)); //$NON-NLS-1$
					if (data == null || data.length == 0) {
						return null;
					}
				}
				return newData(remoteAddress, data[0]);
			} catch (URISyntaxException e) {
				return null;
			}
		}

		private ProxyData newData(InetSocketAddress remoteAddress,
				IProxyData data) {
			if (data == null) {
				return null;
			}
			InetSocketAddress proxyAddress = new InetSocketAddress(
					data.getHost(), data.getPort());
			char[] password = data.getPassword() == null ? null
					: data.getPassword().toCharArray();
			switch (data.getType()) {
			case IProxyData.HTTP_PROXY_TYPE:
				return new ProxyData(proxyAddress,
						new HttpClientConnector(proxyAddress, remoteAddress,
								data.getUserId(), password));
			case IProxyData.HTTPS_PROXY_TYPE:
				return null;
			case IProxyData.SOCKS_PROXY_TYPE:
				return new ProxyData(proxyAddress,
						new Socks5ClientConnector(proxyAddress, remoteAddress,
								data.getUserId(), password));
			default:
				return null;
			}
		}
	}

	private static class EGitFilePasswordProvider
			extends IdentityPasswordProvider {

		private final EGitSecureStore store;

		public EGitFilePasswordProvider(CredentialsProvider provider,
				EGitSecureStore store) {
			super(provider);
			this.store = store;
		}

		@Override
		protected char[] getPassword(String resourceKey, State state)
				throws IOException {
			if (state.getCount() == 0) {
				try {
					UserPasswordCredentials credentials = store
							.getCredentials(toUri(resourceKey));
					if (credentials != null) {
						String password = credentials.getPassword();
						if (password != null) {
							char[] pass = password.toCharArray();
							state.setPassword(pass);
							return pass;
						}
					}
				} catch (StorageException | RuntimeException e) {
					Activator.logError(e.getMessage(), e);
				}
			}
			return super.getPassword(resourceKey, state);
		}

		@Override
		protected ResourceDecodeResult handleDecodeAttemptResult(
				String resourceKey, State state, char[] password, Exception err)
				throws IOException, GeneralSecurityException {
			if (state != null && password != null) {
				if (state.getCount() == 0) {
					if (err != null) {
						try {
							store.clearCredentials(toUri(resourceKey));
						} catch (IOException | RuntimeException e) {
							state.incCount();
							Activator.logError(e.getMessage(), e);
						}
						return ResourceDecodeResult.RETRY;
					}
				} else if (err == null) {
					UserPasswordCredentials credentials = new UserPasswordCredentials(
							"egit:ssh:resource", new String(password)); //$NON-NLS-1$
					try {
						store.putCredentials(toUri(resourceKey), credentials);
					} catch (StorageException | RuntimeException e) {
						Activator.logError(e.getMessage(), e);
					}
				}
			}
			return super.handleDecodeAttemptResult(resourceKey, state, password,
					err);
		}
	}
}
