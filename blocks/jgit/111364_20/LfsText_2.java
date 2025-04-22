package org.eclipse.jgit.lfs.internal;

import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_TYPE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProxySelector;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jgit.lfs.LfsPointer;
import org.eclipse.jgit.lfs.Protocol;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
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

public class LfsConnectionHelper {

	public static HttpConnection getLfsConnection(Repository db
			String purpose)
			throws IOException {
		StoredConfig config = db.getConfig();
		String lfsEndpoint = config.getString("lfs"
		Map<String
		if (lfsEndpoint == null) {
			String remoteUrl = null;
			for (String remote : db.getRemoteNames()) {
				lfsEndpoint = config.getString("lfs"
				if (lfsEndpoint == null
						&& (remote.equals(Constants.DEFAULT_REMOTE_NAME))) {
					remoteUrl = config.getString("remote"
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
					}
				} catch (Exception e) {
				}
			} else {
			}
		}
		if (lfsEndpoint == null) {
			throw new IOException(
		}
		HttpConnection connection = HttpTransport.getConnectionFactory().create(
				url
		connection.setDoOutput(true);
				&& !config.getBoolean("http"
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

	private static String extractProjectName(URIish u) {
		String path = u.getPath().substring(1);
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
							Constants.CHARSET))) {
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
