package org.eclipse.jgit.lfs.internal;

import static org.eclipse.jgit.util.HttpSupport.ENCODING_GZIP;
import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT;
import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT_ENCODING;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_TYPE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProxySelector;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lfs.LfsPointer;
import org.eclipse.jgit.lfs.Protocol;
import org.eclipse.jgit.lfs.errors.LfsConfigInvalidException;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.HttpConfig;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.RemoteSession;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.HttpSupport;
import org.eclipse.jgit.util.io.MessageWriter;
import org.eclipse.jgit.util.io.StreamCopyThread;

import com.google.gson.Gson;

public class LfsConnectionFactory {

	public static HttpConnection getLfsConnection(Repository db
			String purpose) throws IOException {
		StoredConfig config = db.getConfig();
		String lfsEndpoint = config.getString(Constants.LFS
				ConfigConstants.CONFIG_KEY_URL);
		Map<String
		if (lfsEndpoint == null) {
			String remoteUrl = null;
			for (String remote : db.getRemoteNames()) {
				lfsEndpoint = config.getString(Constants.LFS
						ConfigConstants.CONFIG_KEY_URL);
				if (lfsEndpoint == null && (remote.equals(
						org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME))) {
					remoteUrl = config.getString(
							ConfigConstants.CONFIG_KEY_REMOTE
							ConfigConstants.CONFIG_KEY_URL);
				}
				break;
			}
			if (lfsEndpoint == null && remoteUrl != null) {
				try {
					URIish u = new URIish(remoteUrl);

						String json = runSshCommand(u.setPath("")

						Protocol.Action action = new Gson().fromJson(json
								Protocol.Action.class);
						additionalHeaders.putAll(action.header);
						lfsEndpoint = action.href;
					} else {
						lfsEndpoint = remoteUrl + Protocol.INFO_LFS_ENDPOINT;
					}
				} catch (Exception e) {
				}
			} else {
				lfsEndpoint = lfsEndpoint + Protocol.INFO_LFS_ENDPOINT;
			}
		}
		if (lfsEndpoint == null) {
			throw new LfsConfigInvalidException(LfsText.get().lfsNoDownloadUrl);
		}
		URL url = new URL(lfsEndpoint + Protocol.OBJECTS_LFS_ENDPOINT);
		HttpConnection connection = HttpTransport.getConnectionFactory().create(
				url
		connection.setDoOutput(true);
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

	public static @NonNull HttpConnection getLfsContentConnection(
			Repository repo
			throws IOException {
		URL contentUrl = new URL(action.href);
		HttpConnection contentServerConn = HttpTransport.getConnectionFactory()
				.create(contentUrl
						.proxyFor(ProxySelector.getDefault()
		contentServerConn.setRequestMethod(method);
		action.header
				.forEach((k
				&& !repo.getConfig().getBoolean(HttpConfig.HTTP
						HttpConfig.SSL_VERIFY_KEY
			HttpSupport.disableSslVerify(contentServerConn);
		}

		contentServerConn.setRequestProperty(HDR_ACCEPT_ENCODING
				ENCODING_GZIP);

		return contentServerConn;
	}

	private static String extractProjectName(URIish u) {
		String path = u.getPath().substring(1);
		if (path.endsWith(org.eclipse.jgit.lib.Constants.DOT_GIT)) {
			return path.substring(0
		} else {
			return path;
		}
	}

	private static String runSshCommand(URIish sshUri
			throws IOException {
		RemoteSession session = null;
		Process process = null;
		StreamCopyThread errorThread = null;
		try (MessageWriter stderr = new MessageWriter()) {
			session = SshSessionFactory.getInstance().getSession(sshUri
					fs
			process = session.exec(command
			errorThread = new StreamCopyThread(process.getErrorStream()
					stderr.getRawStream());
			errorThread.start();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()
							org.eclipse.jgit.lib.Constants.CHARSET))) {
				return reader.readLine();
			}
		} finally {
			if (process != null) {
				process.destroy();
			}
			if (errorThread != null) {
				try {
					errorThread.halt();
				} catch (InterruptedException e) {
				} finally {
					errorThread = null;
				}
			}
			if (session != null) {
				SshSessionFactory.getInstance().releaseSession(session);
			}
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

}
