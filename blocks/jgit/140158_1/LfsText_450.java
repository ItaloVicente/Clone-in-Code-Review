package org.eclipse.jgit.lfs.internal;

import static org.eclipse.jgit.util.HttpSupport.ENCODING_GZIP;
import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT;
import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT_ENCODING;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_TYPE;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.errors.CommandFailedException;
import org.eclipse.jgit.lfs.LfsPointer;
import org.eclipse.jgit.lfs.Protocol;
import org.eclipse.jgit.lfs.errors.LfsConfigInvalidException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.HttpConfig;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.HttpSupport;
import org.eclipse.jgit.util.SshSupport;

public class LfsConnectionFactory {

	private static final int SSH_AUTH_TIMEOUT_SECONDS = 30;
	private static final Map<String

	public static HttpConnection getLfsConnection(Repository db
			String purpose) throws IOException {
		StoredConfig config = db.getConfig();
		Map<String
		String lfsUrl = getLfsUrl(db
		URL url = new URL(lfsUrl + Protocol.OBJECTS_LFS_ENDPOINT);
		HttpConnection connection = HttpTransport.getConnectionFactory().create(
				url
		connection.setDoOutput(true);
		if (url.getProtocol().equals(SCHEME_HTTPS)
				&& !config.getBoolean(HttpConfig.HTTP
						HttpConfig.SSL_VERIFY_KEY
			HttpSupport.disableSslVerify(connection);
		}
		connection.setRequestMethod(method);
		connection.setRequestProperty(HDR_ACCEPT
				Protocol.CONTENTTYPE_VND_GIT_LFS_JSON);
		connection.setRequestProperty(HDR_CONTENT_TYPE
				Protocol.CONTENTTYPE_VND_GIT_LFS_JSON);
		additionalHeaders
				.forEach((k
		return connection;
	}

	private static String getLfsUrl(Repository db
			Map<String
			throws LfsConfigInvalidException {
		StoredConfig config = db.getConfig();
		String lfsUrl = config.getString(ConfigConstants.CONFIG_SECTION_LFS
				null
				ConfigConstants.CONFIG_KEY_URL);
		Exception ex = null;
		if (lfsUrl == null) {
			String remoteUrl = null;
			for (String remote : db.getRemoteNames()) {
				lfsUrl = config.getString(ConfigConstants.CONFIG_SECTION_LFS
						remote
						ConfigConstants.CONFIG_KEY_URL);
				if (lfsUrl == null && (remote.equals(
						org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME))) {
					remoteUrl = config.getString(
							ConfigConstants.CONFIG_KEY_REMOTE
							ConfigConstants.CONFIG_KEY_URL);
				}
				break;
			}
			if (lfsUrl == null && remoteUrl != null) {
				try {
					lfsUrl = discoverLfsUrl(db
							remoteUrl);
				} catch (URISyntaxException | IOException
						| CommandFailedException e) {
					ex = e;
				}
			} else {
				lfsUrl = lfsUrl + Protocol.INFO_LFS_ENDPOINT;
			}
		}
		if (lfsUrl == null) {
			if (ex != null) {
				throw new LfsConfigInvalidException(
						LfsText.get().lfsNoDownloadUrl
			}
			throw new LfsConfigInvalidException(LfsText.get().lfsNoDownloadUrl);
		}
		return lfsUrl;
	}

	private static String discoverLfsUrl(Repository db
			Map<String
			throws URISyntaxException
		URIish u = new URIish(remoteUrl);
		if (u.getScheme() == null || SCHEME_SSH.equals(u.getScheme())) {
			Protocol.ExpiringAction action = getSshAuthentication(db
					remoteUrl
			additionalHeaders.putAll(action.header);
			return action.href;
		} else {
			return remoteUrl + Protocol.INFO_LFS_ENDPOINT;
		}
	}

	private static Protocol.ExpiringAction getSshAuthentication(
			Repository db
			throws IOException
		AuthCache cached = sshAuthCache.get(remoteUrl);
		Protocol.ExpiringAction action = null;
		if (cached != null && cached.validUntil > System.currentTimeMillis()) {
			action = cached.cachedAction;
		}

		if (action == null) {
			String json = SshSupport.runSshCommand(u.setPath("")
					null
							+ purpose
					SSH_AUTH_TIMEOUT_SECONDS);

			action = Protocol.gson().fromJson(json
					Protocol.ExpiringAction.class);

			AuthCache c = new AuthCache(action);
			sshAuthCache.put(remoteUrl
		}
		return action;
	}

	@NonNull
	public static HttpConnection getLfsContentConnection(
			Repository repo
			throws IOException {
		URL contentUrl = new URL(action.href);
		HttpConnection contentServerConn = HttpTransport.getConnectionFactory()
				.create(contentUrl
						.proxyFor(ProxySelector.getDefault()
		contentServerConn.setRequestMethod(method);
		if (action.header != null) {
			action.header.forEach(
					(k
		}
		if (contentUrl.getProtocol().equals(SCHEME_HTTPS)
				&& !repo.getConfig().getBoolean(HttpConfig.HTTP
						HttpConfig.SSL_VERIFY_KEY
			HttpSupport.disableSslVerify(contentServerConn);
		}

		contentServerConn.setRequestProperty(HDR_ACCEPT_ENCODING
				ENCODING_GZIP);

		return contentServerConn;
	}

	private static String extractProjectName(URIish u) {
		String path = u.getPath();

			path = path.substring(1);
		}

		if (path.endsWith(org.eclipse.jgit.lib.Constants.DOT_GIT)) {
			return path.substring(0
		} else {
			return path;
		}
	}

	public static Protocol.Request toRequest(String operation
			LfsPointer... resources) {
		Protocol.Request req = new Protocol.Request();
		req.operation = operation;
		if (resources != null) {
			req.objects = new LinkedList<>();
			for (LfsPointer res : resources) {
				Protocol.ObjectSpec o = new Protocol.ObjectSpec();
				o.oid = res.getOid().getName();
				o.size = res.getSize();
				req.objects.add(o);
			}
		}
		return req;
	}

	private static final class AuthCache {
		private static final long AUTH_CACHE_EAGER_TIMEOUT = 500;

		private static final SimpleDateFormat ISO_FORMAT = new SimpleDateFormat(

		public AuthCache(Protocol.ExpiringAction action) {
			this.cachedAction = action;
			try {
				if (action.expiresIn != null && !action.expiresIn.isEmpty()) {
					this.validUntil = (System.currentTimeMillis()
							+ Long.parseLong(action.expiresIn))
							- AUTH_CACHE_EAGER_TIMEOUT;
				} else if (action.expiresAt != null
						&& !action.expiresAt.isEmpty()) {
					this.validUntil = ISO_FORMAT.parse(action.expiresAt)
							.getTime() - AUTH_CACHE_EAGER_TIMEOUT;
				} else {
					this.validUntil = System.currentTimeMillis();
				}
			} catch (Exception e) {
				this.validUntil = System.currentTimeMillis();
			}
		}

		long validUntil;

		Protocol.ExpiringAction cachedAction;
	}

}
